package com.base.manager.service;

import com.base.manager.dto.PermissionDTO;
import com.base.manager.pojo.BaseAdminPermission;
import com.base.manager.pojo.BaseAdminUser;
import com.base.manager.response.PageDataResult;

import java.util.List;
import java.util.Map;


public interface AdminPermissionService {


    Map<String,Object> addPermission(BaseAdminPermission permission);


    Map<String,Object> updatePermission(BaseAdminPermission permission);


    PageDataResult getPermissionList(Integer pageNum, Integer pageSize);


    List<PermissionDTO> parentPermissionList();


    Map<String, Object> del(long id);


    BaseAdminPermission getById(Object id);

    Map<String, Object> getUserPerms(BaseAdminUser user);

}
