package com.asiainfo.dto;

import java.util.List;

public class PageDto {

    private int curPage;//当前页数
    private int size=20;//每页显示条数
    private long totalRecords=0;//最大条数
    private List<?> rows;//所对应的json数据

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage+1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
