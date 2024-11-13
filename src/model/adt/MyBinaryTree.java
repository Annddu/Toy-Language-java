package model.adt;


import java.util.LinkedList;
import java.util.Queue;

public class MyBinaryTree<T> implements MyIBinaryTree<T>{
    MyBinaryTreeNode<T> head;
    public MyBinaryTree()
    {
        this.head = null;
    }
//    @Override
//    public void insert(T elem)
//    {
//        if (head == null)
//        {
//            head = new MyBinaryTreeNode<T>(elem);
//        }
//        else
//        {
//            MyBinaryTreeNode<T> helper = head;
//            while (helper.left != null && helper.right != null)
//                helper = helper.left;
//            if (helper.left == null)
//                helper.left = new MyBinaryTreeNode<T>(elem);
//            else
//                helper.right = new MyBinaryTreeNode<T>(elem);
//        }
//    }

    @Override
    public void insert(T elem) {
        if (head == null) {
            head = new MyBinaryTreeNode<T>(elem);
        } else {
            Queue<MyBinaryTreeNode<T>> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                MyBinaryTreeNode<T> current = queue.poll();
                if (current.left == null) {
                    current.left = new MyBinaryTreeNode<T>(elem);
                    break;
                } else {
                    queue.add(current.left);
                }
                if (current.right == null) {
                    current.right = new MyBinaryTreeNode<T>(elem);
                    break;
                } else {
                    queue.add(current.right);
                }
            }
        }
    }

//    @Override
//    public String toString()
//    {
//        StringBuilder str = new StringBuilder();
//        str.append("Binary Tree:\n");
//        inFix(head, str);
//        return str.toString();
//    }
//
//    private void inFix(MyBinaryTreeNode<T> node, StringBuilder str)
//    {
//        if (node != null)
//        {
//            inFix(node.left, str);
//            str.append(node.element).append("\n");
//            inFix(node.right, str);
//        }
//    }

    private void inFix(MyBinaryTreeNode<T> node, StringBuilder str) {
        if (node != null) {
            inFix(node.left, str);
            str.append(node.element).append("\n");
            inFix(node.right, str);
        }
    }

    // After
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Binary Tree:\n");
        if (head != null) {
            Queue<MyBinaryTreeNode<T>> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                MyBinaryTreeNode<T> current = queue.poll();
                str.append(current.element).append("\n");
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
        }
        return str.toString();
    }
}