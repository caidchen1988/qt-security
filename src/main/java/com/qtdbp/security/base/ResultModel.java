package com.qtdbp.security.base;

import java.util.List;

/**
 * 结果集
 *
 * @author: caidchen
 * @create: 2017-05-11 15:38
 * To change this template use File | Settings | File Templates.
 */
public class ResultModel {

    public ResultModel() {
    }

    public ResultModel(Integer totalCount, List<?> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    private Integer totalCount ;

    private List<?> list ;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
