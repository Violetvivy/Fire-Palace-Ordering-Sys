package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.RoomDTO;
import com.fire.firepalaceorderingsys.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 包厢控制器
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * 添加包厢
     */
    @PostMapping("/add")
    public Result addRoom(@Valid @RequestBody RoomDTO dto) {
        return roomService.addRoom(dto);
    }

    /**
     * 删除包厢
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteRoom(@PathVariable Long id) {
        return roomService.deleteRoom(id);
    }

    /**
     * 更新包厢信息
     */
    @PutMapping("/update")
    public Result updateRoom(@Valid @RequestBody RoomDTO dto) {
        return roomService.updateRoom(dto);
    }

    /**
     * 根据ID查询包厢
     */
    @GetMapping("/{id}")
    public Result getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    /**
     * 查询所有包厢
     */
    @GetMapping("/all")
    public Result getAllRooms() {
        return roomService.getAllRooms();
    }

    /**
     * 根据状态查询包厢
     */
    @GetMapping("/status/{status}")
    public Result getRoomsByStatus(@PathVariable Integer status) {
        return roomService.getRoomsByStatus(status);
    }

    /**
     * 绑定包厢（将状态从0改为1）
     */
    @PostMapping("/binding")
    public Result bindRoom(@RequestParam String roomName) {
        return roomService.bindRoom(roomName);
    }

    /**
     * 解除绑定包厢（将状态从1改为0）
     */
    @PostMapping("/unbinding")
    public Result unbindRoom(@RequestParam String roomName) {
        return roomService.unbindRoom(roomName);
    }
}
