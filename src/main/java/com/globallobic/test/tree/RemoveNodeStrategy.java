package com.globallobic.test.tree;

import com.globallobic.test.tree.exception.NoRemoveNodeStrategyFoundException;

abstract class RemoveNodeStrategy<T extends Comparable<T>> {

    abstract void remove();

    static <T extends Comparable<T>> RemoveNodeStrategy removeNodeStrategy(Node<T> parentNode, T element) {
        Node<T> removeNode = removeNode(parentNode, element);
        if (removeNode.getLeftChild() == null && removeNode.getRightChild() == null) {
            return new SimpleNodeRemoveStrategy<>(parentNode, element);
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

    private static class SimpleNodeRemoveStrategy<T extends Comparable<T>> extends RemoveNodeStrategy<T> {
        private Node<T> parentNode;
        private T element;

        SimpleNodeRemoveStrategy(Node<T> parentNode, T element) {
            this.parentNode = parentNode;
            this.element = element;
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
}