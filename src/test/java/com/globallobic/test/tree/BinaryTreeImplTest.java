package com.globallobic.test.tree;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.globallobic.test.tree.BinaryTreeImpl.of;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BinaryTreeImplTest {
    private BinaryTreeImpl<Integer> binaryTree;

    @Before
    public void setUp() {
        binaryTree = new BinaryTreeImpl<>();
    }

    @Test
    public void isEmptyWhenNewBinaryTreeReturnsTrue() {
        assertTrue(binaryTree.isEmpty());
    }

    @Test
    public void insertWhenNewBinaryTreeReturnsTrueAndAddsElementToRoot() {
        Integer insertedElement = 35;
        boolean inserted = binaryTree.insert(insertedElement);
        assertTrue(inserted);
        assertEquals(binaryTree.root.getElement(), insertedElement);
    }

    @Test
    public void isEmptyWhenBinaryTreeHasElementsReturnsFalse() {
        binaryTree.insert(35);
        assertFalse(binaryTree.isEmpty());
    }

    @Test
    public void insertWhenRootExistsAndElementIsLessThenRootAddsElementToLeftChild() {
        binaryTree.insert(35);
        Integer newElement = 18;
        boolean inserted = binaryTree.insert(newElement);
        Integer expected = binaryTree.root.getLeftChild().getElement();
        assertTrue(inserted);
        assertEquals(expected, newElement);
    }

    @Test
    public void insertWhenRootExistsAndElementIsMoreThenRootAddsElementToLeftChild() {
        binaryTree.insert(35);
        Integer newElement = 36;
        boolean inserted = binaryTree.insert(newElement);
        Integer expected = binaryTree.root.getRightChild().getElement();
        assertTrue(inserted);
        assertEquals(expected, newElement);
    }

    @Test
    public void insertWhenElementExistsReturnsFalse() {
        binaryTree.insert(35);
        Integer element = 36;
        binaryTree.insert(element);
        assertFalse(binaryTree.insert(element));
    }

    @Test
    public void insertMultipleElementsReturnsTrueOrFalseDependsOnExistingElements() {
        binaryTree = of(35, 18, 36);
        boolean insertedNewElement = binaryTree.insert(45);
        boolean insertedExistedElement = binaryTree.insert(36);
        assertFalse(insertedExistedElement);
        assertTrue(insertedNewElement);
    }

    @Test
    public void sizeWhenTreeIsEmptyReturns0() {
        assertEquals(0, binaryTree.size());
    }

    @Test
    public void sizeWhenTreeIsNotEmptyReturnsNumberOfUniqueElements() {
        binaryTree = of(35, 18, 36, 45, 36);
        assertEquals(4, binaryTree.size());
    }

    @Test
    public void maxWhenBinaryTreeIsEmptyReturnsOptionalEmpty() {
        assertEquals(Optional.empty(), binaryTree.max());
    }

    @Test
    public void maxWhenTreeIsNotEmptyReturnsMaxElement() {
        Integer maxValue = 45;
        binaryTree = of(35, 18, 36, maxValue, 44);
        Integer actual = binaryTree.max().orElseThrow(NoSuchElementException::new);
        assertEquals(maxValue, actual);
    }


    @Test
    public void minWhenBinaryTreeIsEmptyReturnsOptionalEmpty() {
        binaryTree.min();
    }


    @Test
    public void minWhenTreeIsNotEmptyReturnsMinElement() {
        Integer minValue = 16;
        binaryTree = of(35, 18, 16, minValue, 36, 44);
        Integer actual = binaryTree.min().orElseThrow(NoSuchElementException::new);
        assertEquals(5, binaryTree.size());
        assertEquals(minValue, actual);
    }

    @Test
    public void ofWithVarargsAddsElementsToTree() {
        Integer rootElement = 35;
        binaryTree = of(rootElement, 18, 16, 36, 44);
        assertEquals(rootElement, binaryTree.root.getElement());
        assertEquals(5, binaryTree.size());
    }

    @Test
    public void filterWhenEmptyTreeReturnsEmptyList() {
        List<Integer> result = binaryTree.filter(x -> x > 0);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void filterWhenTreeNotEmptyReturnsListWithFilteredElements() {
        binaryTree = of(15, 26, 5, 17, 3, 2);
        List<Integer> filtered = binaryTree.filter(x -> x > 10);
        assertEquals(3, filtered.size());
        MatcherAssert.assertThat(filtered, CoreMatchers.hasItems(15, 26, 17));
    }

    @Test
    public void containsWhenTreeIsEmptyReturnsFalse() {
        assertFalse(binaryTree.contains(35));
    }

    @Test
    public void containsWhenElementExistsReturnsTrue() {
        binaryTree = of(15, 26, 5, 17, 3, 2);
        assertTrue(binaryTree.contains(17));
    }

    @Test
    public void containsWhenElementNotExistsReturnsFalse() {
        binaryTree = of(15, 26, 5, 17, 3, 2);
        assertFalse(binaryTree.contains(100));
    }

    @Test
    public void parentNodeWhenElementNotExistsReturnsNull() {
        binaryTree = of(15, 26, 5, 17, 3, 2);
        assertNull(binaryTree.parentNode(100));
    }

    @Test
    public void parentNodeWhenElementExistsReturnsParentNode() {
        binaryTree = of(12, 5, 18, 2, 9, 18, 15, 19);
        Integer expectedParentValueFor9 = 5;
        Integer actualParentValueFor9 = binaryTree.parentNode(9).getElement();
        assertEquals(expectedParentValueFor9, actualParentValueFor9);
        Integer expectedParentValueFor15 = 18;
        Integer actualParenValueFor15 = binaryTree.parentNode(15).getElement();
        assertEquals(expectedParentValueFor15, actualParenValueFor15);
    }

    @Test
    public void removeWhenEmptyTreeReturnsFalse() {
        assertFalse(binaryTree.remove(35));
    }

/*
    @Test
    public void removeWhenNodeIsLastRemovesNodeAndReturnsTrue(){
        binaryTree = of(15, 26, 5);
        assertTrue(binaryTree.remove(5));
        assertFalse(binaryTree.contains(5));
    }
*/
}