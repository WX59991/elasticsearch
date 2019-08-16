package com.elasticsearch.elasticsearch.search;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/14 11:20
 * @Description:
 */
@Data
public class MyPage {

    private Integer pageNo;

    private Integer pageSize;

    private Long total;

    private List data;

    private JSONObject agg;

}
