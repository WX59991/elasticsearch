package com.elasticsearch.elasticsearch.util;

import java.util.Collection;

/**
 * @author wangxia
 * @date 2019/8/14 9:55
 * @Description:
 */
public class CollectionUtil {

    public static  boolean isEmpty(Collection Obj){
        return Obj==null || Obj.size()<=0;
    }

}
