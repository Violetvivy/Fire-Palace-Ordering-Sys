package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.pojo.Category;
import com.fire.firepalaceorderingsys.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类接口
     */
    @PostMapping("/add")
    public Result addCategory(@RequestBody Category category) {
        boolean isSuccess = categoryService.addCategory(category);
        if (isSuccess) {
            return Result.success("新增分类成功");
        } else {
            return Result.error("新增分类失败");
        }
    }

    /**
     * 修改分类接口
     */
    @PutMapping("/update")
    public Result updateCategory(@RequestBody Category category) {
        boolean isSuccess = categoryService.updateCategory(category);
        if (isSuccess) {
            return Result.success("修改分类成功");
        } else {
            return Result.error("修改分类失败");
        }
    }

    /**
     * 根据id删除分类接口
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteCategory(@PathVariable Long id) {
        boolean isSuccess = categoryService.deleteCategoryById(id);
        if (isSuccess) {
            return Result.success("删除分类成功");
        } else {
            return Result.error("删除分类失败");
        }
    }

    /**
     * 根据id查询分类详情接口
     */
    @GetMapping("/get/{id}")
    public Result getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return Result.success(category);
    }

    /**
     * 查询全部分类列表接口
     */
    @GetMapping("/list")
    public Result getAllCategoryList() {
        List<Category> categoryList = categoryService.getAllCategoryList();
        return Result.success(categoryList);
    }

}