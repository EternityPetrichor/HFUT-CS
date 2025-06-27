package com.base.manager.service;

import com.base.manager.dto.ProductDTO;
import com.base.manager.pojo.BaseAdminRole;
import com.base.manager.pojo.BaseAdminUser;
import com.base.manager.pojo.Product;
import com.base.manager.response.PageDataResult;

import java.util.Map;

public interface ProductService {

    PageDataResult getProductList(ProductDTO productSearch, Integer pageNum, Integer pageSize);

    Map<String,Object> addProduct(Product product);

    Map<String,Object> updateProduct(Product product);

    Map<String,Object> delProduct(Integer id);
}
