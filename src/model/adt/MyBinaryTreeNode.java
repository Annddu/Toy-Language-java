package model.adt;

public class MyBinaryTreeNode<T>
{
    T element;
    MyBinaryTreeNode<T> left;
    MyBinaryTreeNode<T> right;
    public MyBinaryTreeNode(T elem, MyBinaryTreeNode<T> left, MyBinaryTreeNode<T> right)
    {
        this.element = elem;
        this.left = left;
        this.right = right;
    }
    public MyBinaryTreeNode(T elem)
    {
        this.element = elem;
        this.left = null;
        this.right = null;
    }
}