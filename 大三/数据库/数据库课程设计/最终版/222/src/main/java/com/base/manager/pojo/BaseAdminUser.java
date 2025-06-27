package com.base.manager.pojo;

import javax.persistence.*;

@Table(name = "base_admin_user")
public class BaseAdminUser {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统用户名称
     */
    @Column(name = "sys_user_name")
    private String sysUserName;

    /**
     * 系统用户密码
     */
    @Column(name = "sys_user_pwd")
    private String sysUserPwd;

    /**
     * 角色
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 手机号
     */
    @Column(name = "user_phone")
    private String userPhone;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 登记时间
     */
    @Column(name = "reg_time")
    private String regTime;

    /**
     * 状态（0：无效；1：有效）
     */
    @Column(name = "user_status")
    private Integer userStatus;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取系统用户名称
     *
     * @return sys_user_name - 系统用户名称
     */
    public String getSysUserName() {
        return sysUserName;
    }

    /**
     * 设置系统用户名称
     *
     * @param sysUserName 系统用户名称
     */
    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    /**
     * 获取系统用户密码
     *
     * @return sys_user_pwd - 系统用户密码
     */
    public String getSysUserPwd() {
        return sysUserPwd;
    }

    /**
     * 设置系统用户密码
     *
     * @param sysUserPwd 系统用户密码
     */
    public void setSysUserPwd(String sysUserPwd) {
        this.sysUserPwd = sysUserPwd;
    }

    /**
     * 获取角色
     *
     * @return role_id - 角色
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色
     *
     * @param roleId 角色
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取手机号
     *
     * @return user_phone - 手机号
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 设置手机号
     *
     * @param userPhone 手机号
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置地址
     *
     * @param email 地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取登记时间
     *
     * @return reg_time - 登记时间
     */
    public String getRegTime() {
        return regTime;
    }

    /**
     * 设置登记时间
     *
     * @param regTime 登记时间
     */
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    /**
     * 获取状态（0：无效；1：有效）
     *
     * @return user_status - 状态（0：无效；1：有效）
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 设置状态（0：无效；1：有效）
     *
     * @param userStatus 状态（0：无效；1：有效）
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "BaseAdminUser{" +
                "id=" + id +
                ", sysUserName='" + sysUserName + '\'' +
                ", sysUserPwd='" + sysUserPwd + '\'' +
                ", roleId=" + roleId +
                ", address=" + address +
                ", email=" + email +
                ", userPhone='" + userPhone + '\'' +
                ", regTime='" + regTime + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}