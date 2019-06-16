package com.globallobic.test.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BinaryTreeImpl<T extends Comparable<T>> implements BinaryTree<T> {
    Node<T> root;
    private int size;

    @SafeVarargs
    public static <T extends Comparable<T>> BinaryTreeImpl<T> of(T... elements) {
        Objects.requireNonNull(elements);
        BinaryTreeImpl<T> binaryTree = new BinaryTreeImpl<>();
        Stream.of(elements).forEach(binaryTree::insert);
        return binaryTree;
    }

    @Override
    public boolean insert(T element) {
        Objects.requireNonNull(element);
        if (isEmpty()) {
            root = Node.valueOf(element);
            size++;
            return true;
        } else {
            return insertNewElement(root, element);
        }
    }

    private boolean insertNewElement(Node<T> node, T element) {
        if (node.getElement().compareTo(element) > 0) {
            return insertIntoLeftSubTree(node, element);
        } else if (node.getElement().compareTo(element) < 0) {
            return insertIntoRightSubTree(node, element);
        }
        return false;
    }

    private boolean insertIntoRightSubTree(Node<T> node, T element) {
        if (node.getRightChild() == null) {
            Node<T> newNode = Node.valueOf(element);
            node.setRightChild(newNode);
            size++;
            return true;
        } else {
            return insertNewElement(node.getRightChild(), element);
        }
    }

    private boolean insertIntoLeftSubTree(Node<T> node, T element) {
        if (node.getLeftChild() == null) {
            Node<T> newNode = Node.valueOf(element);
            node.setLeftChild(newNode);
            size++;
            return true;
        } else {
            return insertNewElement(node.getLeftChild(), element);
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Optional<T> max() {
        if (isEmpty()) {
            return Optional.empty();
        }
        Node<T> maxNode = root;
        while (maxNode.getRightChild() != null) {
            maxNode = maxNode.getRightChild();
        }
        return Optional.of(maxNode.getElement());
    }

    @Override
    public Optional<T> min() {
        if (isEmpty()) {
            return Optional.empty();
        }
        Node<T> maxNode = root;
        while (maxNode.getLeftChild() != null) {
            maxNode = maxNode.getLeftChild();
        }
        return Optional.of(maxNode.getElement());
    }

    @Override
    public List<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        List<T> result = new ArrayList<>();
        return walkByTreeAndFilter(root, predicate, result);
    }

    private List<T> walkByTreeAndFilter(Node<T> node, Predicate<T> predicate, List<T> result) {
        if (node != null) {
            walkByTreeAndFilter(node.getLeftChild(), predicate, result);
            walkByTreeAndFilter(node.getRightChild(), predicate, result);
            T element = node.getElement();
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        return result;
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);
        return walkByTreeAndFindNode(root, element) != null;
    }

    private Node<T> walkByTreeAndFindNode(Node<T> node, T element) {
        Objects.requireNonNull(element);
        if (node == null) {
            return null;
        }
        if (node.getElement().equals(element)) {
            return node;
        }
        if (node.getElement().compareTo(element) > 0) {
            return walkByTreeAndFindNode(node.getLeftChild(), element);
        } else {
            return walkByTreeAndFindNode(node.getRightChild(), element);
        }
    }

    Node<T> parentNode(T element) {
        Objects.requireNonNull(element);
        return walkByTreeAndFindParentNode(root, element);
    }

    private Node<T> walkByTreeAndFindParentNode(Node<T> node, T element) {
        if (node == null) {
            return null;
        }

        if (nodeHasElement(node.getLeftChild(), element) || nodeHasElement(node.getRightChild(), element)) {
            return node;
        }

        if (node.getElement().compareTo(element) > 0) {
            return walkByTreeAndFindParentNode(node.getLeftChild(), element);
        } else {
            return walkByTreeAndFindParentNode(node.getRightChild(), element);
        }
    }

    private boolean nodeHasElement(Node<T> node, T element) {
        if (node == null) {
            return false;
        }
        return node.getElement().equals(element);
    }

    @Override
    public boolean remove(T element) {
        Node<T> nodeByElement = walkByTreeAndFindNode(root, element);
        if (nodeByElement == null) {
            return false;
        }
        return false;
    }
}
