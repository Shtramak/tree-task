package com.globallobic.test.tree;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface BinaryTree<T extends Comparable<T>> {

    boolean isEmpty();

    boolean insert(T element);

    int size();

    Optional<T> max();

    Optional<T> min();

    List<T> filter(Predicate<T> predicate);

    boolean contains(T element);

    boolean remove(T element);
}
