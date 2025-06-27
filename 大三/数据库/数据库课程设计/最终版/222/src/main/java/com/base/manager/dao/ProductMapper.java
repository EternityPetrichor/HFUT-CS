package com.base.manager.dao;

import com.base.manager.dto.ProductDTO;
import java.util.List;

import com.base.manager.pojo.Product;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

@Repository
public interface ProductMapper extends MyMapper<Product> {

    List<ProductDTO> getProductList(ProductDTO productDTO);

}