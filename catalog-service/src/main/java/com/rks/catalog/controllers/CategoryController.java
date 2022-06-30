package com.rks.catalog.controllers;import com.rks.catalog.domain.category.Category;import com.rks.catalog.service.impl.CategoryServiceImpl;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.web.bind.annotation.*;import java.util.List;@RestController@RequestMapping("/api/v1")public class CategoryController {    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);    private CategoryServiceImpl categoryService;    @Autowired    public CategoryController(CategoryServiceImpl categoryService) {        this.categoryService = categoryService;    }    @GetMapping("/categories")    public @ResponseBody List<Category> getAll() {        return categoryService.getAllCategories();    }    @PostMapping("/categories")    public @ResponseBody Category add(@RequestBody Category c) {        return categoryService.save(c);    }    @GetMapping("/categories/{id}")    public @ResponseBody Category getById(@PathVariable("id") String categoryId) {        return categoryService.getById(categoryId);    }    @GetMapping("/categories/search")    public @ResponseBody List<Category> search(@RequestParam("criteria") String criteria) {        log.info("Searching categories for search criteria: {}", criteria);        return categoryService.getCategoriesByNameRegex(criteria);    }}