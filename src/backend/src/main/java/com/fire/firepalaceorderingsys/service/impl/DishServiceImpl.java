package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.dto.DishDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.CategoryMapper;
import com.fire.firepalaceorderingsys.mapper.DishMapper;
import com.fire.firepalaceorderingsys.pojo.Category;
import com.fire.firepalaceorderingsys.pojo.Dish;
import com.fire.firepalaceorderingsys.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜品服务实现类
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据ID查询菜品
     */
    @Override
    public Dish getById(Long id) {
        Dish dish = dishMapper.selectById(id);
        if (dish == null) {
            throw new BusinessException("菜品不存在");
        }
        return dish;
    }

    /**
     * 查询所有菜品
     */
    @Override
    public List<Dish> getAll() {
        return dishMapper.selectAll();
    }

    /**
     * 根据状态查询菜品
     */
    @Override
    public List<Dish> getByStatus(Integer status) {
        return dishMapper.selectByStatus(status);
    }

    /**
     * 根据分类ID查询菜品
     */
    @Override
    public List<Dish> getByCategory(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        return dishMapper.selectByCategory(categoryId);
    }

    /**
     * 根据名称模糊查询菜品
     */
    @Override
    public List<Dish> searchByName(String name) {
        return dishMapper.selectByName(name);
    }

    /**
     * 新增菜品
     */
    @Override
    @Transactional
    public Dish add(DishDTO dishDTO) {
        // 校验分类是否存在
        Category category = categoryMapper.selectById(dishDTO.getCategoryId());
        if (category == null) {
            throw new BusinessException("分类不存在");
        }

        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setCategoryId(dishDTO.getCategoryId());
        dish.setPrice(dishDTO.getPrice());
        dish.setCostPrice(dishDTO.getCostPrice());
        dish.setSpicyLevel(dishDTO.getSpicyLevel() != null ? dishDTO.getSpicyLevel() : 0);
        dish.setIsSignature(dishDTO.getIsSignature() != null ? dishDTO.getIsSignature() : 0);
        dish.setImageUrl(dishDTO.getImageUrl());
        dish.setVideoUrl(dishDTO.getVideoUrl());
        dish.setDescription(dishDTO.getDescription());
        dish.setCulturalStory(dishDTO.getCulturalStory());
        dish.setStatus(dishDTO.getStatus() != null ? dishDTO.getStatus() : 1);

        dishMapper.insert(dish);
        log.info("新增菜品成功: id={}, name={}", dish.getId(), dish.getName());
        return dish;
    }

    /**
     * 更新菜品信息
     */
    @Override
    @Transactional
    public Dish update(DishDTO dishDTO) {
        if (dishDTO.getId() == null) {
            throw new BusinessException("菜品ID不能为空");
        }

        Dish existing = dishMapper.selectById(dishDTO.getId());
        if (existing == null) {
            throw new BusinessException("菜品不存在");
        }

        // 若更新了分类，校验新分类是否存在
        if (dishDTO.getCategoryId() != null) {
            Category category = categoryMapper.selectById(dishDTO.getCategoryId());
            if (category == null) {
                throw new BusinessException("分类不存在");
            }
        }

        Dish dish = new Dish();
        dish.setId(dishDTO.getId());
        dish.setName(dishDTO.getName());
        dish.setCategoryId(dishDTO.getCategoryId());
        dish.setPrice(dishDTO.getPrice());
        dish.setCostPrice(dishDTO.getCostPrice());
        dish.setSpicyLevel(dishDTO.getSpicyLevel());
        dish.setIsSignature(dishDTO.getIsSignature());
        dish.setImageUrl(dishDTO.getImageUrl());
        dish.setVideoUrl(dishDTO.getVideoUrl());
        dish.setDescription(dishDTO.getDescription());
        dish.setCulturalStory(dishDTO.getCulturalStory());
        dish.setStatus(dishDTO.getStatus());

        dishMapper.update(dish);
        log.info("更新菜品成功: id={}, name={}", dish.getId(), dish.getName());
        return dishMapper.selectById(dish.getId());
    }

    /**
     * 删除菜品（软删除）
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Dish dish = dishMapper.selectById(id);
        if (dish == null) {
            throw new BusinessException("菜品不存在");
        }
        dishMapper.deleteById(id);
        log.info("删除菜品成功: id={}, name={}", id, dish.getName());
    }

    /**
     * 更新菜品上架/下架状态
     */
    @Override
    @Transactional
    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态值非法，0表示下架，1表示上架");
        }
        Dish dish = dishMapper.selectById(id);
        if (dish == null) {
            throw new BusinessException("菜品不存在");
        }
        dishMapper.updateStatus(id, status);
        log.info("更新菜品状态成功: id={}, status={}", id, status);
    }
}
