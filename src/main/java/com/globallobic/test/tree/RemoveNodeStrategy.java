package com.globallobic.test.tree;

import com.globallobic.test.tree.exception.NoRemoveNodeStrategyFoundException;

abstract class RemoveNodeStrategy<T extends Comparable<T>> {
    Node<T> parentNode;
    T element;

    RemoveNodeStrategy(Node<T> parentNode, T element) {
        this.parentNode = parentNode;
        this.element = element;
    }

    abstract void remove();

    static <T extends Comparable<T>> RemoveNodeStrategy removeNodeStrategy(Node<T> parentNode, T element) {
        Node<T> removeNode = removeNode(parentNode, element);
        Node<T> leftChild = removeNode.getLeftChild();
        Node<T> rightChild = removeNode.getRightChild();
        if (leftChild == null && rightChild == null) {
            return new SimpleRemoveStrategy<>(parentNode, element);
        } else if (leftChild == null || rightChild == null) {
            return new SimpleReplaceWithChildNodeRemoveStrategy<>(parentNode, element);
        }
        String message = "Element was found but can't be removed..." +
                "Strategy for this kind of removal was not implemented";
        throw new NoRemoveNodeStrategyFoundException(message);
    }

    private static <T extends Comparable<T>> Node<T> removeNode(Node<T> parentNode, T element) {
        Node<T> leftChild = parentNode.getLeftChild();
        Node<T> rightChild = parentNode.getRightChild();
        if (leftChild != null && element.equals(leftChild.getElement())) {
            return leftChild;
        } else {
            return rightChild;
        }
    }

    private static class SimpleRemoveStrategy<T extends Comparable<T>> extends RemoveNodeStrategy<T> {

        SimpleRemoveStrategy(Node<T> parentNode, T element) {
            super(parentNode, element);
        }

        @Override
        void remove() {
            Node<T> leftChild = parentNode.getLeftChild();
            if (leftChild != null && element.equals(leftChild.getElement())) {
                parentNode.setLeftChild(null);
            } else {
                parentNode.setRightChild(null);
            }
        }
    }

    private static class SimpleReplaceWithChildNodeRemoveStrategy<T extends Comparable<T>> extends RemoveNodeStrategy<T> {

        SimpleReplaceWithChildNodeRemoveStrategy(Node<T> parentNode, T element) {
            super(parentNode, element);
        }

        @Override
        void remove() {
            Node<T> leftChild = parentNode.getLeftChild();
            if (leftChild != null && element.equals(leftChild.getElement())) {
                replaceLeftParentNode(leftChild);
            } else {
                replaceRightParentNode(parentNode.getRightChild());
            }
        }

        private void replaceRightParentNode(Node<T> rightChild) {
            if (rightChild.getLeftChild() != null) {
                parentNode.setRightChild(rightChild.getLeftChild());
            } else {
                parentNode.setRightChild(rightChild.getRightChild());
            }
        }

        private void replaceLeftParentNode(Node<T> leftChild) {
            if (leftChild.getLeftChild() != null) {
                parentNode.setLeftChild(leftChild.getLeftChild());
            } else {
                parentNode.setLeftChild(leftChild.getRightChild());
            }
        }
    }

}