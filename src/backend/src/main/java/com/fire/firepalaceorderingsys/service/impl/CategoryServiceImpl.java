package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.mapper.CategoryMapper;
import com.fire.firepalaceorderingsys.pojo.Category;
import com.fire.firepalaceorderingsys.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 分类业务实现类
 * 实现CategoryService接口里的所有方法，调用Mapper完成数据库操作
 */
// 这个注解必须加！告诉Spring这是业务实现类，会自动管理这个对象
@Service
public class CategoryServiceImpl implements CategoryService {

    // 注入我们刚才写的CategoryMapper，用来调用数据库操作
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public boolean addCategory(Category category) {
        // 调用Mapper的新增方法，返回受影响的行数，大于0就是成功
        return categoryMapper.insert(category) > 0;
    }

    @Override
    public boolean updateCategory(Category category) {
        // 调用Mapper的修改方法
        return categoryMapper.update(category) > 0;
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        // 调用Mapper的删除方法
        return categoryMapper.deleteById(id) > 0;
    }

    @Override
    public Category getCategoryById(Long id) {
        // 调用Mapper的根据id查询方法
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> getAllCategoryList() {
        // 调用Mapper的查询所有列表方法
        return categoryMapper.selectAll();
    }
}