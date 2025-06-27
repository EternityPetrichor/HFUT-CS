package com.base.manager.dto;


public class PageRequest {
    /** 默认页面大小 */
    public static Integer		DEFAULT_PAGE_SIZE = 10;

    /** 默认页码 */
    public static Integer		DEFAULT_PAGE_NUM  = 1;

    private Integer pageNum;
    private Integer pageSize;


    public Integer getPageNum() {
        return pageNum <= 0 ? DEFAULT_PAGE_NUM : pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
