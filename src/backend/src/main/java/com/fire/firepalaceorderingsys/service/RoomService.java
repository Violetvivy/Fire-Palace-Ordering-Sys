package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    /**
     * 添加包厢
     */
    Result addRoom(RoomDTO dto);

    /**
     * 删除包厢
     */
    Result deleteRoom(Long id);

    /**
     * 更新包厢信息
     */
    Result updateRoom(RoomDTO dto);

    /**
     * 根据ID查询包厢
     */
    Result getRoomById(Long id);

    /**
     * 查询所有包厢
     */
    Result getAllRooms();

    /**
     * 根据状态查询包厢
     */
    Result getRoomsByStatus(Integer status);

    /**
     * 绑定包厢（将状态从0改为1）
     */
    Result bindRoom(String roomName);

    /**
     * 解除绑定包厢（将状态从1改为0）
     */
    Result unbindRoom(String roomName);
}
