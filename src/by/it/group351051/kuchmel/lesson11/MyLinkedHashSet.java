package by.it.group351051.kuchmel.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class MyLinkedHashSet<T> implements Set<T> {
    private static final int INITIAL_CAPACITY = 16;
    private Entry<T>[] storage;
    private ChainNode<T> first;
    private ChainNode<T> last;
    private int count;

    public MyLinkedHashSet() {
        storage = new Entry[INITIAL_CAPACITY];
        first = null;
        last = null;
        count = 0;
    }

    private static class Entry<T> {
        T value;
        Entry<T> next;
        ChainNode<T> linked;

        Entry(T value, Entry<T> next, ChainNode<T> linked) {
            this.value = value;
            this.next = next;
            this.linked = linked;
        }
    }

    private static class ChainNode<T> {
        T value;
        ChainNode<T> prev;
        ChainNode<T> next;

        ChainNode(T value) {
            this.value = value;
        }
    }

    private int hashIndex(Object key) {
        return key == null ? 0 : Math.abs(key.hashCode() % storage.length);
    }

    @Override
    public boolean add(T t) {
        int index = hashIndex(t);
        Entry<T> current = storage[index];

        while (current != null) {
            if (Objects.equals(current.value, t)) {
                return false;
            }
            current = current.next;
        }

        ChainNode<T> newChain = new ChainNode<>(t);
        if (last == null) {
            first = last = newChain;
        } else {
            last.next = newChain;
            newChain.prev = last;
            last = newChain;
        }

        storage[index] = new Entry<>(t, storage[index], newChain);
        count++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = hashIndex(o);
        Entry<T> current = storage[index];
        Entry<T> prev = null;

        while (current != null) {
            if (Objects.equals(current.value, o)) {
                ChainNode<T> chain = current.linked;
                if (chain.prev != null) {
                    chain.prev.next = chain.next;
                } else {
                    first = chain.next;
                }
                if (chain.next != null) {
                    chain.next.prev = chain.prev;
                } else {
                    last = chain.prev;
                }

                if (prev == null) {
                    storage[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                count--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index = hashIndex(o);
        Entry<T> current = storage[index];

        while (current != null) {
            if (Objects.equals(current.value, o)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public void clear() {
        storage = new Entry[INITIAL_CAPACITY];
        first = last = null;
        count = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ChainNode<T> current = first;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean changed = false;
        for (T item : c) {
            if (add(item)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object item : c) {
            if (remove(item)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        ChainNode<T> current = first;

        while (current != null) {
            T value = current.value;
            current = current.next; // move before removal
            if (!c.contains(value)) {
                remove(value);
                modified = true;
            }
        }

        return modified;
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
}