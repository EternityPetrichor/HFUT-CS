package com.base.manager.service.impl;

import com.base.manager.common.utils.DateUtils;
import com.base.manager.dao.ProductMapper;
import com.base.manager.dto.ProductDTO;
import com.base.manager.pojo.Product;
import com.base.manager.response.PageDataResult;
import com.base.manager.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductMapper productMapper;
    @Override
    public PageDataResult getProductList(ProductDTO productSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();

        // Use PageHelper to start paging
        PageHelper.startPage(pageNum, pageSize);

        List<ProductDTO> product = productMapper.getProductList(productSearch);

        if (!product.isEmpty()) {
            PageInfo<ProductDTO> pageInfo = new PageInfo<>(product);
            pageDataResult.setList(product);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }


    @Override
    public Map<String, Object> addProduct(Product product) {
        logger.info("设置产品[新增或更新]！permission:" + product);
        product.setCreateTime(DateUtils.getCurrentDateToDate());
        Map<String,Object> data = new HashMap();
        int result=productMapper.insert(product);
        if(result == 0){
            data.put("code",0);
            data.put("msg","新增产品失败");
            logger.error("新增产品失败");
            return data;
        }
        data.put("code",1);
        data.put("msg","新增产品成功");
        return data;
    }

    @Override
    public Map<String, Object> updateProduct(Product product) {
        Map<String,Object> data = new HashMap();
        try{
            int result = productMapper.updateByPrimaryKey(product);
            if(result == 0){
                data.put("code",0);
                data.put("msg","更新失败！");
                logger.error("产品[更新]，结果=更新失败！");
                return data;
            }
            data.put("code",1);
            data.put("msg","更新成功！");
            logger.info("产品[更新]，结果=更新成功！");
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("产品[更新]异常！", e);
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> delProduct(Integer id) {
        Map<String, Object> data = new HashMap<>();
        try {
            int result = productMapper.deleteByPrimaryKey(id);
            if(result == 0){
                data.put("code",0);
                data.put("msg","删除产品失败");
            }
            data.put("code",1);
            data.put("msg","删除产品成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除产品异常！", e);
        }
        return data;
    }
}
