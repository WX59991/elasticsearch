package com.elasticsearch.elasticsearch.tree;

import com.elasticsearch.elasticsearch.tree.common.Node;
import com.elasticsearch.elasticsearch.tree.common.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/21 14:14
 * @Description:  二叉树
 */
public class BinaryTree extends Tree<Node> {

    List<Node> nodeList;

    public BinaryTree(){}

    public BinaryTree(int[] value){
        for(int i=0;i<value.length;i++){
            insertValue(value[i]);
        }
    }

    public void insertValue(int value){
        if(nodeList==null)
            nodeList=new ArrayList<>();
        Node temp=new Node(value);
        if(nodeList.size()==0){
            head=temp;
            nodeList.add(head);
            return;
        }
        nodeList.add(temp);
        if(nodeList.get(0).getLeft()==null){
            nodeList.get(0).setLeft(temp);
            return;
        }
        nodeList.get(0).setRight(temp);
        nodeList.remove(0);
        return;
    }
}
