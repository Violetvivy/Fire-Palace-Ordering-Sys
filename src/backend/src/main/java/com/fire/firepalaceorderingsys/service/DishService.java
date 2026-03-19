package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.dto.DishDTO;
import com.fire.firepalaceorderingsys.pojo.Dish;

import java.util.List;

/**
 * 菜品服务接口
 */
public interface DishService {

    /**
     * 根据ID查询菜品
     * @param id 菜品ID
     * @return 菜品信息
     */
    Dish getById(Long id);

    /**
     * 查询所有菜品
     * @return 菜品列表
     */
    List<Dish> getAll();

    /**
     * 根据状态查询菜品
     * @param status 状态：1上架，0下架
     * @return 菜品列表
     */
    List<Dish> getByStatus(Integer status);

    /**
     * 根据分类ID查询菜品
     * @param categoryId 分类ID
     * @return 菜品列表
     */
    List<Dish> getByCategory(Long categoryId);

    /**
     * 根据名称模糊查询菜品
     * @param name 菜品名称
     * @return 菜品列表
     */
    List<Dish> searchByName(String name);

    /**
     * 新增菜品
     * @param dishDTO 菜品信息
     * @return 新增的菜品
     */
    Dish add(DishDTO dishDTO);

    /**
     * 更新菜品信息
     * @param dishDTO 菜品信息
     * @return 更新后的菜品
     */
    Dish update(DishDTO dishDTO);

    /**
     * 删除菜品（软删除）
     * @param id 菜品ID
     */
    void delete(Long id);

    /**
     * 更新菜品上架/下架状态
     * @param id 菜品ID
     * @param status 状态：1上架，0下架
     */
    void updateStatus(Long id, Integer status);
}
