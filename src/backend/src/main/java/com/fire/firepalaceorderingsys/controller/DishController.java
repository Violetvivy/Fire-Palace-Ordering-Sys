package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.DishDTO;
import com.fire.firepalaceorderingsys.pojo.Dish;
import com.fire.firepalaceorderingsys.service.DishService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品控制器
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 根据ID查询菜品
     */
    @GetMapping("/select/{id}")
    public Result getById(@PathVariable Long id) {
        Dish dish = dishService.getById(id);
        return Result.success(dish);
    }

    /**
     * 查询菜品列表（可选按状态过滤）
     */
    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) Integer status) {
        List<Dish> dishes;
        if (status != null) {
            dishes = dishService.getByStatus(status);
        } else {
            dishes = dishService.getAll();
        }
        return Result.success(dishes);
    }

    /**
     * 根据分类ID查询菜品
     */
    @GetMapping("/category/{categoryId}")
    public Result getByCategory(@PathVariable Long categoryId) {
        List<Dish> dishes = dishService.getByCategory(categoryId);
        return Result.success(dishes);
    }

    /**
     * 根据名称模糊搜索菜品
     */
    @GetMapping("/search")
    public Result searchByName(@RequestParam String name) {
        List<Dish> dishes = dishService.searchByName(name);
        return Result.success(dishes);
    }

    /**
     * 新增菜品
     */
    @PostMapping("/add")
    public Result add(@Valid @RequestBody DishDTO dishDTO) {
        Dish dish = dishService.add(dishDTO);
        return Result.success(dish);
    }

    /**
     * 更新菜品信息
     */
    @PutMapping("/update")
    public Result update(@Valid @RequestBody DishDTO dishDTO) {
        Dish dish = dishService.update(dishDTO);
        return Result.success(dish);
    }

    /**
     * 删除菜品（软删除）
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        dishService.delete(id);
        return Result.success();
    }

    /**
     * 更新菜品上架/下架状态
     */
    @PutMapping("/status/{id}")
    public Result updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        dishService.updateStatus(id, status);
        return Result.success();
    }
}
