package com.base.manager.dao;


import com.base.manager.dto.PermissionDTO;
import com.base.manager.pojo.BaseAdminPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface BaseAdminPermissionMapper extends MyMapper<BaseAdminPermission> {
    List<PermissionDTO> getPermissionList();

    List<PermissionDTO> parentPermissionList();

    int updatePermission(BaseAdminPermission permission);

    List<PermissionDTO> getPermissionListByPId(@Param("pid") Integer pid);
}