package com.elasticsearch.elasticsearch.tree;

import com.elasticsearch.elasticsearch.tree.avlTree.AvlTree;

/**
 * @author wangxia
 * @date 2019/8/21 14:50
 * @Description:测试类
 */
public class TestTreeClass {

    public static void main(String[] args) {
        //二叉搜索树
        AvlTree avlTree=new AvlTree(new Integer[]{4,2,7,1,3,6,15,5,14,16,13});
        System.out.println("二叉搜索树的前序遍历:");
        avlTree.preorderTraversal();
    }

}
