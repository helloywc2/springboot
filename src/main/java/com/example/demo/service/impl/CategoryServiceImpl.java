package com.example.demo.service.impl;

import com.example.demo.dataobj.ProductCategory;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 * Created by 廖师兄
 * 2017-05-09 10:16
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
