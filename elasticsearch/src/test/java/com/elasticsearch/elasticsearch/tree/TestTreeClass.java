package com.elasticsearch.elasticsearch.tree;

/**
 * @author wangxia
 * @date 2019/8/21 14:50
 * @Description:测试类
 */
public class TestTreeClass {

    public static void main(String[] args) {
//        //二叉树
//        BinaryTree binaryTree=new BinaryTree(new int[]{1,2,3,4,5,6,7,8});
//        System.out.println("树的前序遍历");
//        binaryTree.preorderTraversal();
//        System.out.println("\n树的中序遍历");
//        binaryTree.intermediateTraversal();
//        System.out.println("\n树的后序遍历");
//        binaryTree.postorderTraversal();

        //二叉搜索树
        SearchBinaryTree searchBinaryTree=new SearchBinaryTree(new Integer[]{10,5,15,3,7,null,18});
        System.out.println("二叉搜索树的前序遍历:");
        searchBinaryTree.preorderTraversal();
        System.out.println("\n二叉搜索树的中序遍历");
        searchBinaryTree.intermediateTraversal();
        System.out.println("\n二叉搜索树的后序遍历");
        searchBinaryTree.postorderTraversal();
        System.out.println("\n计算结果:"+searchBinaryTree.rangeSumBST(searchBinaryTree.head,7,15));
    }

}
