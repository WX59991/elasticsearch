package com.elasticsearch.elasticsearch.tree;

/**
 * @author wangxia
 * @date 2019/8/22 8:54
 * @Description:
 */
public class TestLetCode {

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1==null && t2==null){
            return null;
        }
        if(t1==null)
            return t2;
        if(t2==null)
            return t1;
        TreeNode result=new TreeNode(t1.val+t2.val);
        if(t1.left!=null || t2.left!=null)
            result.left= mergeTrees(t1.left,t2.left);
        if(t1.right!=null || t2.right!=null)
            result.right= mergeTrees(t1.right,t2.right);
        return result;
    }

}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}