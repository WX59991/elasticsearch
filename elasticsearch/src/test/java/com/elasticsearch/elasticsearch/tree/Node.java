package com.elasticsearch.elasticsearch.tree;

import lombok.Data;

/**
 * @author wangxia
 * @date 2019/8/21 14:07
 * @Description:  二叉树节点
 */
@Data
public class Node {

    private Integer value;

    private Node left;

    private Node right;

    public Node(Integer value){
        this.value=value;
    }

}
