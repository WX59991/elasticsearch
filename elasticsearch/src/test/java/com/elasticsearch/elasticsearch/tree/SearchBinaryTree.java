package com.elasticsearch.elasticsearch.tree;

import com.elasticsearch.elasticsearch.tree.common.Node;

/**
 * @author wangxia
 * @date 2019/8/21 15:00
 * @Description:  二叉查找树
 */
public class SearchBinaryTree extends BinaryTree {

    /**
     * 初始化二叉查找树
     * @param values
     */
    public SearchBinaryTree(Integer[] values){
        for(int i=0;i<values.length;i++){
            insert(values[i]);
        }
    }

    /**
     * 放入新值
     * @param value
     */
    public void insert(Integer value){
        if(value==null){
            return;
        }
        Node newNode=new Node(value);
        if(head==null){
            head=newNode;
            return;
        }
        Node temp=head;
        while (temp!=null){
            if(temp.getValue()==value)
                return;
            if(temp.getValue()<value){
                if(temp.getRight()==null){
                    temp.setRight(newNode);
                    break;
                }
                temp=temp.getRight();
                continue;
            }
            if(temp.getLeft()==null){
                temp.setLeft(newNode);
                break;
            }
            temp=temp.getLeft();
        }
    }

    /**
     * 将L,R内的数据进行求和
     * @param node
     * @param L
     * @param R
     * @return
     */
    public int rangeSumBST(Node node,int L, int R) {
        int sum=0;
        if(node.getValue()>=L && node.getValue()<=R)
            sum+=node.getValue();
        if(node.getLeft()!=null && node.getValue()>L){
            sum+=rangeSumBST(node.getLeft(),L,R);
        }
        if(node.getRight()!=null && node.getValue()<R){
            sum+=rangeSumBST(node.getRight(),L,R);
        }
        return sum;
    }
}
