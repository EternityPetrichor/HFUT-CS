package com.base.manager.controller;

import com.base.manager.common.utils.DateUtils;
import com.base.manager.dto.ProductDTO;
import com.base.manager.pojo.BaseAdminRole;
import com.base.manager.pojo.BaseAdminUser;
import com.base.manager.pojo.Product;
import com.base.manager.response.PageDataResult;
import com.base.manager.service.ProductService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("product")
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductService productService;


    @RequestMapping("/productManage")
    public String userManage() {
        Object principal = SecurityUtils.getSubject().getPrincipal();

        if (principal instanceof BaseAdminUser) {
            BaseAdminUser user = (BaseAdminUser) principal;
            if (user.getRoleId() == 4) {
                return "/pfs/productManage";
            } else {
                return "/product/productManage";
            }
        }
        return "/product/productManage";
    }

    @PostMapping("getProductList")
    @ResponseBody
    public PageDataResult getUserList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ ProductDTO productDTO) {
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取用户列表
            pdr = productService.getProductList(productDTO, pageNum ,pageSize);
            logger.info("产品列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("产品列表查询异常！", e);
        }
        return pdr;
    }

    /**
     *
     *述: 设置角色[新增或更新]
     *
     * @param:
     * @return:
     * @auther: lcy
     * @date: 2021/5/3 10:54
     */
    @PostMapping("setProduct")
    @ResponseBody
    public Map<String,Object> setProduct(Product product) {
        logger.info("设置产品[新增或更新]！Product:" + product);
        Map<String,Object> data = new HashMap();
        if(product.getId() == null){
            //新增产品
            data = productService.addProduct(product);
        }else{
            //修改产品
            data = productService.updateProduct(product);
        }
        return data;
    }

    /**
     *
     * 功能描述: 删除/恢复 产品
     *
     * @param:
     * @return:
     * @auther: lcy
     * @date: 2021/5/22 11:59
     */
    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delProduct(@RequestParam("id") Integer id) {
        logger.info("删除/恢复产品！id:" + id);
        Map<String, Object> data = new HashMap<>();
        //删除产品
        data = productService.delProduct(id);

        return data;
    }
}
