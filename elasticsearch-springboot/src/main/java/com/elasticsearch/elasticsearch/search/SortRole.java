package com.elasticsearch.elasticsearch.search;

import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @author wangxia
 * @date 2019/8/14 10:19
 * @Description:
 */
@Data
public class SortRole {

    private String sortFields;

    private SortOrder sortOrder;

}
