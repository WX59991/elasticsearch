package com.elasticsearch.elasticsearch.search;

/**
 * @author wangxia
 * @date 2019/8/14 10:32
 * @Description:
 */
public enum  AvgEnum {


    SUM("sum"),
    MAX("max"),
    MIN("min"),
    COUNT("count");

    private String type;

    private AvgEnum(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }

}
