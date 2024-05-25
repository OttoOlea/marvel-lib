package com.marvel.dto;

import java.util.ArrayList;
import java.util.List;

public class MarvelResponse {

    private Integer total;
    private Integer count;
    private Integer limit;
    private List<CharacterDto> resultList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<CharacterDto> getResultList() {
        if (resultList == null) resultList = new ArrayList<>();
        return resultList;
    }

    public MarvelResponse() {
    }

    @Override
    public String toString() {
        return "MarvelResponse{" +
                "total=" + total +
                ", count=" + count +
                ", limit=" + limit +
                ", resultList=" + resultList +
                '}';
    }
}
