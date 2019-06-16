package com.globallobic.test.tree;

class Node<E extends Comparable> {
    private E element;
    private Node<E> leftChild;
    private Node<E> rightChild;

    private Node(E element) {
        this.element = element;
    }

    public static <E extends Comparable> Node<E> valueOf(E element) {
        return new Node<>(element);
    }

    public Node<E> getLeftChild() {
        return leftChild;
    }

    public Node<E> getRightChild() {
        return rightChild;
    }

    public void setLeftChild(Node<E> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node<E> rightChild) {
        this.rightChild = rightChild;
    }

    public E getElement() {
        return element;
    }
}
