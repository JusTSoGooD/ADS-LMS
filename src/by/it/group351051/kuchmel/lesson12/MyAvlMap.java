package by.it.group351051.kuchmel.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {
    private Node root;
    private int size;

    private static class Node {
        int key;
        String value;
        Node left, right;
        int height;

        Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        Node leftOfRight = rightChild.left;

        rightChild.left = node;
        node.right = leftOfRight;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        rightChild.height = Math.max(height(rightChild.left), height(rightChild.right)) + 1;

        return rightChild;
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        Node rightOfLeft = leftChild.right;

        leftChild.right = node;
        node.left = rightOfLeft;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        leftChild.height = Math.max(height(leftChild.left), height(leftChild.right)) + 1;

        return leftChild;
    }

    private Node balance(Node node) {
        int balance = getBalanceFactor(node);

        if (balance > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node insert(Node node, int key, String value) {
        if (node == null) return new Node(key, value);

        if (key < node.key) {
            node.left = insert(node.left, key, value);
        } else if (key > node.key) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return balance(node);
    }

    private Node delete(Node node, int key) {
        if (node == null) return null;

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node successor = findMin(node.right);
                node.key = successor.key;
                node.value = successor.value;
                node.right = delete(node.right, successor.key);
            }
        }

        if (node == null) return null;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return balance(node);
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node getNode(Node node, int key) {
        if (node == null) return null;
        if (key < node.key) return getNode(node.left, key);
        if (key > node.key) return getNode(node.right, key);
        return node;
    }

    private void inOrderTraversal(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);
            sb.append(node.key).append("=").append(node.value).append(", ");
            inOrderTraversal(node.right, sb);
        }
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        Node existing = getNode(root, key);
        if (existing != null) {
            String oldValue = existing.value;
            existing.value = value;
            return oldValue;
        }

        root = insert(root, key, value);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        if (key == null || !(key instanceof Integer)) return null;

        Node node = getNode(root, (Integer) key);
        if (node == null) return null;

        root = delete(root, (Integer) key);
        size--;
        return node.value;
    }

    @Override
    public String get(Object key) {
        if (key == null || !(key instanceof Integer)) return null;

        Node node = getNode(root, (Integer) key);
        return node != null ? node.value : null;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null || !(key instanceof Integer)) return false;
        return getNode(root, (Integer) key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        inOrderTraversal(root, sb);
        if (sb.length() > 1) sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    // Unsupported operations
    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
