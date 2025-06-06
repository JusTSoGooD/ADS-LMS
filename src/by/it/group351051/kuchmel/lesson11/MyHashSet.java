package by.it.group351051.kuchmel.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class MyHashSet<T> implements Set<T> {
    private static final int INITIAL_SIZE = 16;
    private BucketNode<T>[] buckets;
    private int elementCount;

    public MyHashSet() {
        buckets = new BucketNode[INITIAL_SIZE];
        elementCount = 0;
    }

    private static class BucketNode<T> {
        T data;
        BucketNode<T> next;

        BucketNode(T data, BucketNode<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private int computeIndex(Object key) {
        return key == null ? 0 : Math.abs(key.hashCode() % buckets.length);
    }

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public boolean isEmpty() {
        return elementCount == 0;
    }

    @Override
    public boolean contains(Object o) {
        int idx = computeIndex(o);
        BucketNode<T> current = buckets[idx];
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean add(T t) {
        int idx = computeIndex(t);
        BucketNode<T> node = buckets[idx];
        while (node != null) {
            if (Objects.equals(node.data, t)) {
                return false;
            }
            node = node.next;
        }
        buckets[idx] = new BucketNode<>(t, buckets[idx]);
        elementCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int idx = computeIndex(o);
        BucketNode<T> curr = buckets[idx];
        BucketNode<T> prev = null;
        while (curr != null) {
            if (Objects.equals(curr.data, o)) {
                if (prev == null) {
                    buckets[idx] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                elementCount--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        elementCount = 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        boolean firstEntry = true;
        for (BucketNode<T> entry : buckets) {
            while (entry != null) {
                if (!firstEntry) {
                    result.append(", ");
                }
                result.append(entry.data);
                firstEntry = false;
                entry = entry.next;
            }
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <U> U[] toArray(U[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
