package com.elasticsearch.elasticsearch.tree.common;

import lombok.Data;

/**
 * @author wangxia
 * @date 2019/8/21 14:07
 * @Description:  二叉树节点
 */
@Data
public class Node {

    protected Integer value;

    protected Node left;

    protected Node right;

    public Node(Integer value){
        this.value=value;
    }

    public Node(){

    }

}
