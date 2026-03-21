package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.pojo.Category;
import java.util.List;

/**
 * 分类业务接口
 * 定义分类相关的增删改查业务方法
 */
public interface CategoryService {

    /**
     * 新增分类
     * @param category 要新增的分类对象
     * @return 新增成功返回true，失败返回false
     */
    boolean addCategory(Category category);

    /**
     * 修改分类
     * @param category 要修改的分类对象（必须带id）
     * @return 修改成功返回true，失败返回false
     */
    boolean updateCategory(Category category);

    /**
     * 根据id删除分类
     * @param id 要删除的分类id
     * @return 删除成功返回true，失败返回false
     */
    boolean deleteCategoryById(Long id);

    /**
     * 根据id查询分类详情
     * @param id 要查询的分类id
     * @return 分类对象
     */
    Category getCategoryById(Long id);

    /**
     * 查询所有分类列表
     * @return 分类列表
     */
    List<Category> getAllCategoryList();

}