package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.WaiterDTO;
import com.fire.firepalaceorderingsys.pojo.Waiter;
import com.fire.firepalaceorderingsys.service.WaiterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务员控制器
 */
@RestController
@RequestMapping("/waiter")
public class WaiterController {

    @Autowired
    private WaiterService waiterService;

    /**
     * 根据ID查询服务员
     */
    @GetMapping("/select/{id}")
    public Result getById(@PathVariable Long id) {
        Waiter waiter = waiterService.getById(id);
        return Result.success(waiter);
    }

    /**
     * 根据工号查询服务员
     */
    @GetMapping("/workNo/{workNo}")
    public Result getByWorkNo(@PathVariable String workNo) {
        Waiter waiter = waiterService.getByWorkNo(workNo);
        return Result.success(waiter);
    }

    /**
     * 查询所有服务员
     */
    @GetMapping("/list")
    public Result getAll() {
        List<Waiter> waiters = waiterService.getAll();
        return Result.success(waiters);
    }

    /**
     * 根据姓名模糊查询服务员
     */
    @GetMapping("/search")
    public Result searchByName(@RequestParam String name) {
        List<Waiter> waiters = waiterService.getByName(name);
        return Result.success(waiters);
    }

    /**
     * 新增服务员
     */
    @PostMapping("/add")
    public Result add(@Valid @RequestBody WaiterDTO waiterDTO) {
        Waiter waiter = waiterService.add(waiterDTO);
        return Result.success(waiter);
    }

    /**
     * 更新服务员信息
     */
    @PutMapping("update")
    public Result update(@Valid @RequestBody WaiterDTO waiterDTO) {
        Waiter waiter = waiterService.update(waiterDTO);
        return Result.success(waiter);
    }

    /**
     * 删除服务员
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        waiterService.delete(id);
        return Result.success();
    }
}
