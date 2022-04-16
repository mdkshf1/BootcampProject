package com.bootcampproject.controllers;

import com.bootcampproject.entities.Category;
import com.bootcampproject.entities.CategoryMetadataField;
import com.bootcampproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addMetadata")
    public ResponseEntity<?> addMetadata(@RequestHeader(name = "name")String name)
    {
        return new ResponseEntity<String>("Category Metadata saved and id is"+categoryService.addMetadata(name), HttpStatus.OK);
    }

    @GetMapping("metadata")
    public ResponseEntity<?> getAllMetadata(@RequestHeader(name = "offset",required = false)Integer offset,@RequestHeader(name = "limit",required = false)Integer limit)
    {
        return new ResponseEntity<List<CategoryMetadataField>>(categoryService.getAllMetadata(PageRequest.of(offset,limit)),HttpStatus.OK);
    }

    @PostMapping("addCategory")
    public ResponseEntity<?> addCategory(@RequestHeader(name = "categoryName")String categoryName,@RequestHeader(name = "parentId",required = false)Long id)
    {
        return new ResponseEntity<String>("Category saved with id is"+categoryService.addCategory(categoryName,id),HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id")Long id)
    {
        return new ResponseEntity<Category>(categoryService.getCategory(id),HttpStatus.OK);
    }
    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(@RequestHeader(name = "offset",required = false)Integer offset,@RequestHeader(name = "limit",required = false)Integer limit)
    {
        return new ResponseEntity<List<Category>>(categoryService.getAllCategory(PageRequest.of(offset,limit,Sort.by("id"))),HttpStatus.OK);
    }

    @PutMapping("updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")Long id,@RequestBody String name)
    {
        return new ResponseEntity<Category>(categoryService.updateCategory(id,name),HttpStatus.OK);
    }

}
