package com.fire.firepalaceorderingsys.controller;

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

    @PostMapping("/add")
    public String addCategory(@RequestBody Category category) {
        boolean isSuccess = categoryService.addCategory(category);
        return isSuccess ? "新增分类成功" : "新增分类失败";
    }

    @PutMapping("/update")
    public String updateCategory(@RequestBody Category category) {
        boolean isSuccess = categoryService.updateCategory(category);
        return isSuccess ? "修改分类成功" : "修改分类失败";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        boolean isSuccess = categoryService.deleteCategoryById(id);
        return isSuccess ? "删除分类成功" : "删除分类失败";
    }

    @GetMapping("/get/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/list")
    public List<Category> getAllCategoryList() {
        return categoryService.getAllCategoryList();
    }
}