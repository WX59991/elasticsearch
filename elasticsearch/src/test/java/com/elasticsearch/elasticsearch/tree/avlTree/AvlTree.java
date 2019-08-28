package com.elasticsearch.elasticsearch.tree.avlTree;

import com.elasticsearch.elasticsearch.tree.common.Tree;

/**
 * @author wangxia
 * @date 2019/8/22 16:16
 * @Description: avl树，自平衡二叉树
 */
public class AvlTree extends Tree<AvlNode> {

    private int maxLeftHeight = 0;
    private int maxRightHeight = 0;

    public AvlTree(Integer[] values) {
        for (int i = 0; i < values.length; i++) {
            insert(head, values[i]);
        }
    }

    //插入数据
    public void insert(AvlNode begin, int val) {
        if (head == null) {
            head = new AvlNode(val, 1);
            return;
        }
        AvlNode temp = begin;
        while (temp != null) {
            if (temp.getValue() == val)
                return;
            //往左边插入
            if (temp.getValue() > val) {
                if (temp.getLeft() == null) {
                    insertLeft(temp, val);
                    return;
                }
                temp = (AvlNode) temp.getLeft();
                continue;
            }
            //往右边插入
            if (temp.getValue() < val) {
                if (temp.getRight() == null) {
                    insertRight(temp, val);
                    return;
                }
                temp = (AvlNode) temp.getRight();
                continue;
            }
        }
    }


    //左插
    private void insertLeft(AvlNode avlNode, int val) {
        int newHeight = avlNode.getHeight() + 1;
        boolean isLeft = head.getValue() > val;
        //判断是否需要单旋或双旋   当数高差>2的时候就需要旋转了
        if (avlNode.getPrev() == null || newHeight - Math.min(maxRightHeight, maxLeftHeight) < 2) {
            //不需要单旋直接插入
            avlNode.setLeft(new AvlNode(avlNode, val, newHeight));
            setMaxValue(newHeight, isLeft);
            return;
        }
        //左左 单旋
        if (!dealLeftSingl(avlNode, val, isLeft)) {
            //左右 双旋
            if (avlNode.getPrev().getRight() == null) {
                int tempval = avlNode.getValue();
                avlNode.setValue(val);
                avlNode.setRight(new AvlNode(avlNode, tempval, avlNode.getHeight() + 1));
            }
            //插入节点原值  与书中略有不同
            insert((AvlNode) avlNode.getPrev().getPrev().getLeft(), avlNode.getPrev().getPrev().getValue());
            avlNode.getPrev().getPrev().setValue(avlNode.getValue());
            avlNode.setValue(val);
        }

    }

    //右插
    private void insertRight(AvlNode avlNode, int val) {
        int newHeight = avlNode.getHeight() + 1;
        boolean isLeft = head.getValue() > val;
        //判断是否需要单旋或双旋
        if (avlNode.getPrev() == null || newHeight - Math.min(maxRightHeight, maxLeftHeight) < 2) {
            //不需要单旋直接插入
            avlNode.setRight(new AvlNode(avlNode, val, newHeight));
            setMaxValue(newHeight, isLeft);
            return;
        }

        //判断是否为右右 单旋
        if (!dealRightSingl(avlNode, val, isLeft)) {
            //双旋
            //左右 双旋
            if (avlNode.getPrev().getLeft() == null) {
                int tempval = avlNode.getValue();
                avlNode.setValue(val);
                avlNode.setLeft(new AvlNode(avlNode, tempval, avlNode.getHeight() + 1));
            }
            //插入节点原值  与书中略有不同
            insert((AvlNode) avlNode.getPrev().getPrev().getRight(), avlNode.getPrev().getPrev().getValue());
            avlNode.getPrev().getPrev().setValue(avlNode.getPrev().getLeft().getValue());
            avlNode.getPrev().getLeft().setValue(avlNode.getPrev().getValue());
            avlNode.getPrev().setValue(avlNode.getValue());
            avlNode.setValue(val);
        }

    }

    //处理左左单旋
    public boolean dealLeftSingl(AvlNode avlNode, int val, boolean isLeft) {
        int preHeight = avlNode.getPrev().getHeight();
        int tempvalue = avlNode.getPrev().getValue();
        //判断是否需要单旋
        if (avlNode.getPrev().getLeft() != null && avlNode.getPrev().getLeft().getValue() == avlNode.getValue()) {
            if (avlNode.getPrev().getRight() != null) {
                balanceInRoot(avlNode.getPrev(), avlNode, val, isLeft);
                //插入新节点
                avlNode.setLeft(new AvlNode(avlNode, val, avlNode.getHeight() + 1));
                setMaxValue(avlNode.getHeight() + 1, head.getValue() > val);
                return true;
            }
            //单旋
            avlNode.getPrev().setValue(avlNode.getValue());
            avlNode.getPrev().setHeight(preHeight);
            avlNode.getPrev().setLeft(new AvlNode(avlNode.getPrev(), val, preHeight + 1));
            setMaxValue(preHeight + 1, head.getValue() > val);
            avlNode.getPrev().setRight(new AvlNode(avlNode.getPrev(), tempvalue, preHeight + 1));
            setMaxValue(preHeight + 1, head.getValue() > tempvalue);
            return true;
        }
        return false;
    }

    public boolean dealRightSingl(AvlNode avlNode, int val, boolean isLeft) {
        int preHeight = avlNode.getPrev().getHeight();
        int tempvalue = avlNode.getPrev().getValue();
        if (avlNode.getPrev().getRight() != null && avlNode.getPrev().getRight().getValue() == avlNode.getValue()) {
            //判断是否需要调节根节点
            if (avlNode.getPrev().getLeft() != null) {
                balanceInRoot(avlNode.getPrev(), avlNode, val, isLeft);
                //插入新节点
                avlNode.setRight(new AvlNode(avlNode, val, avlNode.getHeight() + 1));
                setMaxValue(avlNode.getHeight() + 1, head.getValue() > val);
                return true;
            }
            //单旋
            avlNode.getPrev().setValue(avlNode.getValue());
            avlNode.getPrev().setHeight(preHeight);
            avlNode.getPrev().setRight(new AvlNode(avlNode.getPrev(), val, preHeight + 1));
            setMaxValue(preHeight + 1, head.getValue() > val);
            avlNode.getPrev().setLeft(new AvlNode(avlNode.getPrev(), tempvalue, preHeight + 1));
            setMaxValue(preHeight + 1, head.getValue() > tempvalue);
            return true;
        }
        return false;
    }

    /**
     * 判断是否在根节点处平衡  如果需要在根节点进行自平衡则进行自平衡
     *
     * @param preNode
     * @param avlNode
     * @param val
     * @param isLeft
     * @return
     */
    private void balanceInRoot(AvlNode preNode, AvlNode avlNode, int val, boolean isLeft) {
        if (isLeft) {
            //把头结点加入右边
            insert((AvlNode) head.getRight(), head.getValue());
            //调整头节点
            head.setValue(head.getLeft().getValue());
            //右子树加入新节点
            insert((AvlNode) head.getRight(), head.getLeft().getRight().getValue());
            head.setLeft(head.getLeft().getLeft());
            return;
        }
        //把头结点加入左边
        insert((AvlNode) head.getLeft(), head.getValue());
        //调整头节点
        head.setValue(head.getRight().getValue());
        //左子树加入新节点
        insert((AvlNode) head.getLeft(), head.getRight().getLeft().getValue());
        head.setRight(head.getRight().getRight());
    }


    public int setMaxValue(int height, boolean isLeft) {
        if (isLeft) {
            return maxLeftHeight = Math.max(maxLeftHeight, height);
        }
        return maxRightHeight = Math.max(maxRightHeight, height);
    }

}
