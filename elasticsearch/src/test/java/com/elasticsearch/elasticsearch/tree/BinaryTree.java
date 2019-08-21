package com.elasticsearch.elasticsearch.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/21 14:14
 * @Description:  二叉树
 */
public class BinaryTree {

    protected Node head;

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

    /**
     * 树的前序遍历
     */
    public void preorderTraversal(){
        preorderTraversalDieDai(head);
    }

    private void preorderTraversalDieDai(Node node){
        System.out.print(node.getValue()+"\t");
        if(node.getLeft()!=null){
            preorderTraversalDieDai(node.getLeft());
        }
        if(node.getRight()!=null){
            preorderTraversalDieDai(node.getRight());
        }
    }

    /***
     * 树的中序遍历
     */
    public void intermediateTraversal(){
        intermediateTraversalDieDai(head);
    }

    private void intermediateTraversalDieDai(Node node){
        if(node.getLeft()!=null){
            intermediateTraversalDieDai(node.getLeft());
        }
        System.out.print(node.getValue()+"\t");
        if(node.getRight()!=null){
            intermediateTraversalDieDai(node.getRight());
        }
    }

    /**
     * 树的后序遍历
     */
    public void postorderTraversal(){
        postorderTraversalDieDai(head);
    }

    private void postorderTraversalDieDai(Node node){
        if(node.getLeft()!=null){
            postorderTraversalDieDai(node.getLeft());
        }
        if(node.getRight()!=null){
            postorderTraversalDieDai(node.getRight());
        }
        System.out.print(node.getValue()+"\t");
    }

}
