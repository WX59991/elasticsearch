package com.elasticsearch.elasticsearch.tree.avlTree;

import com.elasticsearch.elasticsearch.tree.common.Node;
import lombok.Data;

/**
 * @author wangxia
 * @date 2019/8/22 16:38
 * @Description:
 */
@Data
public class AvlNode extends Node {

    //存储上一个节点，用作旋转
    private AvlNode prev;
    private Integer height;

    public AvlNode(Integer value,Integer height){
        this.value=value;
        this.height=height;
    }

    public AvlNode(AvlNode prevNode,Integer value,Integer height){
        this.value=value;
        this.height=height;
        this.prev=prevNode;
    }
}
