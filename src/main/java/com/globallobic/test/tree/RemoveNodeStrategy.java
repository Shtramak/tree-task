package com.globallobic.test.tree;

abstract class RemoveNodeStrategy<T extends Comparable<T>> {

    abstract void remove();
/*

    static <T extends Comparable<T>> RemoveNodeStrategy removeNodeStrategy(Node<T> parentNode, T element){
        Node<T> deletionNode = null;
        deletionNode = parentNode.getLeftChild().e
    }
*/
}
