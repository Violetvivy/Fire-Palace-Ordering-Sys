package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.RoomDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.RoomMapper;
import com.fire.firepalaceorderingsys.pojo.Room;
import com.fire.firepalaceorderingsys.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    /**
     * 添加包厢
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addRoom(RoomDTO dto) {
        // 检查包厢名称是否已存在
        if (roomMapper.checkRoomNameExists(dto.getRoomName()) >= 1) {
            return Result.error("包厢名称已存在！");
        }

        Room room = new Room();
        BeanUtils.copyProperties(dto, room);
        room.setCreatedAt(LocalDateTime.now());
        room.setDeletedAt(null);

        roomMapper.insert(room);
        log.info("包厢添加成功: {}", dto.getRoomName());

        return Result.success("添加成功");
    }

    /**
     * 删除包厢
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteRoom(Long id) {
        Room room = roomMapper.selectById(id);
        if (room == null) {
            return Result.error("包厢不存在或已被删除");
        }

        int result = roomMapper.deleteById(id);
        if (result > 0) {
            log.info("包厢删除成功: id={}, roomName={}", id, room.getRoomName());
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 更新包厢信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateRoom(RoomDTO dto) {
        if (dto.getId() == null) {
            return Result.error("包厢ID不能为空");
        }

        Room existingRoom = roomMapper.selectById(dto.getId());
        if (existingRoom == null) {
            return Result.error("包厢不存在或已被删除");
        }

        // 如果修改了包厢名称，检查新名称是否已存在（排除自身）
        if (!existingRoom.getRoomName().equals(dto.getRoomName())) {
            if (roomMapper.checkRoomNameExists(dto.getRoomName()) >= 1) {
                return Result.error("包厢名称已存在！");
            }
        }

        Room room = new Room();
        BeanUtils.copyProperties(dto, room);
        room.setCreatedAt(existingRoom.getCreatedAt()); // 保持原创建时间

        int result = roomMapper.update(room);
        if (result > 0) {
            log.info("包厢更新成功: id={}, roomName={}", dto.getId(), dto.getRoomName());
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 根据ID查询包厢
     */
    @Override
    public Result getRoomById(Long id) {
        Room room = roomMapper.selectById(id);
        if (room == null) {
            return Result.error("包厢不存在或已被删除");
        }

        RoomDTO roomDTO = new RoomDTO();
        BeanUtils.copyProperties(room, roomDTO);
        return Result.success(roomDTO);
    }

    /**
     * 查询所有包厢
     */
    @Override
    public Result getAllRooms() {
        List<Room> rooms = roomMapper.selectAll();
        List<RoomDTO> roomDTOs = rooms.stream().map(room -> {
            RoomDTO dto = new RoomDTO();
            BeanUtils.copyProperties(room, dto);
            return dto;
        }).collect(Collectors.toList());

        return Result.success(roomDTOs);
    }

    /**
     * 根据状态查询包厢
     */
    @Override
    public Result getRoomsByStatus(Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            return Result.error("状态参数错误，只能为0(空闲)或1(使用中)");
        }

        List<Room> rooms = roomMapper.selectByStatus(status);
        List<RoomDTO> roomDTOs = rooms.stream().map(room -> {
            RoomDTO dto = new RoomDTO();
            BeanUtils.copyProperties(room, dto);
            return dto;
        }).collect(Collectors.toList());

        return Result.success(roomDTOs);
    }

    /**
     * 绑定包厢（将状态从0改为1）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result bindRoom(String roomName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            return Result.error("包厢名称不能为空");
        }

        Room room = roomMapper.selectByRoomName(roomName);
        if (room == null) {
            return Result.error("包厢不存在");
        }

        if (room.getStatus() != 0) {
            return Result.error("包厢当前状态不是空闲，无法绑定");
        }

        int result = roomMapper.updateStatus(room.getId(), 1);
        if (result > 0) {
            log.info("包厢绑定成功: roomName={}, id={}", roomName, room.getId());
            return Result.success("绑定成功");
        } else {
            return Result.error("绑定失败");
        }
    }

    /**
     * 解除绑定包厢（将状态从1改为0）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result unbindRoom(String roomName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            return Result.error("包厢名称不能为空");
        }

        Room room = roomMapper.selectByRoomName(roomName);
        if (room == null) {
            return Result.error("包厢不存在");
        }

        if (room.getStatus() != 1) {
            return Result.error("包厢当前状态不是使用中，无法解绑");
        }

        int result = roomMapper.updateStatus(room.getId(), 0);
        if (result > 0) {
            log.info("包厢解绑成功: roomName={}, id={}", roomName, room.getId());
            return Result.success("解绑成功");
        } else {
            return Result.error("解绑失败");
        }
    }
}
