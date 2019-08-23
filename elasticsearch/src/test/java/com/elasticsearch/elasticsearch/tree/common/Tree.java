package com.elasticsearch.elasticsearch.tree.common;

/**
 * @author wangxia
 * @date 2019/8/23 9:03
 * @Description:
 */
public abstract class Tree<T extends Node> {

    protected T head;

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

    private void postorderTraversalDieDai(Node node) {
        if (node.getLeft() != null) {
            postorderTraversalDieDai(node.getLeft());
        }
        if (node.getRight() != null) {
            postorderTraversalDieDai(node.getRight());
        }
        System.out.print(node.getValue() + "\t");
    }

}
