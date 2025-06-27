package com.base.manager.service;

import com.base.manager.pojo.BaseAdminRole;
import com.base.manager.response.PageDataResult;

import java.util.List;
import java.util.Map;


public interface AdminRoleService {

    PageDataResult getRoleList(Integer pageNum, Integer pageSize);

    List<BaseAdminRole> getRoles();

    BaseAdminRole findRoleById(Integer id);

    Map<String,Object> updateRole(BaseAdminRole role);

    Map<String,Object> delRole(Integer id,Integer status);

    Map<String,Object> recoverRole(Integer id,Integer status);

    Map<String,Object> addRole(BaseAdminRole role);

}
