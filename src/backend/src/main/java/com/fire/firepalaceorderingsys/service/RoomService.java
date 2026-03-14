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
}
