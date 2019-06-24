package com.example.demo.controller;

import com.example.demo.VO.ProductInfoVO;
import com.example.demo.VO.ProductVO;
import com.example.demo.VO.ResultVO;
import com.example.demo.dataobj.ProductCategory;
import com.example.demo.dataobj.ProductInfo;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){

//        // 1. 查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        System.out.println(productInfoList);


//        List<ProductCategory> productCategoryList =  categoryService.findAll();
//        List<ProductCategory> productCategoryList =  categoryService.findByCategoryTypeIn(Arrays.asList(3));
//        System.out.println(productCategoryList);

//        List<ProductCategory> categoryServiceList = categoryService.findAll();
//        System.out.println(categoryServiceList);

        // 2. 查询类目
        List<Integer> categoryTypeList = new ArrayList<>();

        for(ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }

        List<ProductCategory> productCategoryList =  categoryService.findByCategoryTypeIn(categoryTypeList);
//
//        // 3. 数据拼装
//
//
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory: productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType((productCategory.getCategoryType()));
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }




//        ResultVO resultVO = new ResultVO();
//        resultVO.setData(Arrays.asList(productVOList));
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//
//        return resultVO;

        return ResultVOUtil.success(productVOList);
    }

}
