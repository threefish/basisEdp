package com.sgaop.common.WebPojo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: 黄川
 * Date Time: 2015/9/916:21
 */

public class DataTablePager {

    private int pageSize = 10;
    private int pageNumber = 1;

    public static DataTablePager CreateDataTablePager(HttpServletRequest req) {

        DataTablePager dataTablePager = new DataTablePager();
        int start = Integer.parseInt(req.getParameter("start"));
        int pageSize = Integer.parseInt(req.getParameter("length"));
        int pageNumber = 0;
        if (start == 0) {
            pageNumber = 1;
        } else {
            pageNumber = start / pageSize;
            pageNumber++;
        }
        dataTablePager.setPageSize(pageSize);
        dataTablePager.setPageNumber(pageNumber);
        return dataTablePager;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
