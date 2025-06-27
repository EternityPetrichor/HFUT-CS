package com.base.manager.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid;

    private Integer pid;

    private Integer num;

    private String orderTime;

    private String pname;

    private Integer status;

    private Date createTime;

    private String username;

    @Column(length=10 ,scale=2)
    private BigDecimal allprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String panme) {
        this.pname = panme;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
    public BigDecimal getAllprice() {
        return allprice;
    }

    public void setAllprice(BigDecimal allprice) {
        this.allprice = allprice;
    }
}