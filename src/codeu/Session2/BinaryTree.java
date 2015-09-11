package codeu.Session2;

import com.sun.istack.internal.NotNull;

import java.util.ArrayDeque;
import java.util.Arrays;

public class BinaryTree<E extends Comparable<E>> {
    BinaryTreeNode root;
    int size = 0;

    BinaryTree() { }

    public void add(@NotNull E item) { // Did I use this annotation right?
        this.size++;
        if (this.root == null) {
//            Empty tree
            this.root = new BinaryTreeNode(item);
        } else {
//            Non-empty tree
            BinaryTreeNode node = this.root;
            BinaryTreeNode current;
            while (node != null) {
                current = node;
                if (item.compareTo(node.getPayload()) <= 0) {
                    node = node.getLeftChild();
                    if (node == null) {
                        current.setLeftChild(new BinaryTreeNode(item));
                    }
                } else {
                    node = node.getRightChild();
                    if (node == null) {
                        current.setRightChild(new BinaryTreeNode(item));
                    }
                }
            }
        }
    }

    public Object[] flatten() {
//        QUESTION: How do I return an array of type E[]. I am really struggling on this?
//        I know I can do it after using array copy, but how do I do it here?
        ArrayDeque<E> result = new ArrayDeque<>(this.size);
        inOrder(result, this.root);
        return result.toArray(new Object[result.size()]);
    }

    private void inOrder(ArrayDeque<E> result, BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(result, node.getLeftChild());
        result.add(node.getPayload());
        inOrder(result, node.getRightChild());
    }

    private class BinaryTreeNode {
        private BinaryTreeNode leftChild;
        private BinaryTreeNode rightChild;
        private E payload;

        BinaryTreeNode(E payload) {
            this.payload = payload;
        }

        public BinaryTreeNode getLeftChild() {
            return leftChild;
        }

        public BinaryTreeNode getRightChild() {
            return rightChild;
        }

        public E getPayload() {
            return payload;
        }

        public void setLeftChild(BinaryTreeNode leftChild) {
            this.leftChild = leftChild;
        }

        public void setRightChild(BinaryTreeNode rightChild) {
            this.rightChild = rightChild;
        }

        public void setPayload(E payload) {
            this.payload = payload;
        }

        public boolean isLeaf() {
            return leftChild == null && rightChild == null;
        }

    }

    public static void main(String[] args) {
        BinaryTree<String> binaryTree;

        binaryTree = new BinaryTree<>();
        binaryTree.add("hello");
        binaryTree.add("abc");
        binaryTree.add("ghi");
        binaryTree.add("def");
        binaryTree.add("abc");
        binaryTree.add("abcd");
        System.out.println(Arrays.toString(binaryTree.flatten()));


        binaryTree = new BinaryTree<>();
        binaryTree.add("g");
        binaryTree.add("x");
        binaryTree.add("d");
        binaryTree.add("a");
        binaryTree.add("b");
        binaryTree.add("w");
        binaryTree.add("x");
        binaryTree.add("z");
        System.out.println(Arrays.toString(binaryTree.flatten()));

        binaryTree = new BinaryTree<>();
        binaryTree.add("feg");
        binaryTree.add("");
        binaryTree.add("");
        binaryTree.add("abcdefghijk");
        binaryTree.add("#@/");
        binaryTree.add("!!!123");
        System.out.println(Arrays.toString(binaryTree.flatten()));

        binaryTree = new BinaryTree<>();
        System.out.println(Arrays.toString(binaryTree.flatten()));

        // Should also test a very long string and long tree, but can't be asked as I'm sure it works :-)

    }
}
