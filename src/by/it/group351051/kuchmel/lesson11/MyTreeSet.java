package by.it.group351051.kuchmel.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {
    private static final int START_SIZE = 16;
    private Object[] data;
    private int count;

    public MyTreeSet() {
        data = new Object[START_SIZE];
        count = 0;
    }

    private void expandIfNeeded() {
        if (count == data.length) {
            int newSize = data.length * 2;
            Object[] extended = new Object[newSize];
            System.arraycopy(data, 0, extended, 0, count);
            data = extended;
        }
    }

    private int findIndex(Object target) {
        int low = 0;
        int high = count - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            @SuppressWarnings("unchecked")
            Comparable<? super E> midVal = (Comparable<? super E>) data[mid];
            int cmp = midVal.compareTo((E) target);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -(low + 1);
    }

    @Override
    public boolean add(E item) {
        if (contains(item)) return false;

        expandIfNeeded();
        int idx = findIndex(item);
        if (idx < 0) idx = -(idx + 1);

        System.arraycopy(data, idx, data, idx + 1, count - idx);
        data[idx] = item;
        count++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int idx = findIndex(o);
        if (idx < 0) return false;

        System.arraycopy(data, idx + 1, data, idx, count - idx - 1);
        data[--count] = null;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return findIndex(o) >= 0;
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
        data = new Object[START_SIZE];
        count = 0;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[");
        for (int i = 0; i < count; i++) {
            out.append(data[i]);
            if (i < count - 1) out.append(", ");
        }
        out.append("]");
        return out.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e : c) {
            if (add(e)) changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            if (remove(o)) changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (int i = 0; i < count; i++) {
            if (!c.contains(data[i])) {
                remove(data[i]);
                changed = true;
                i--;
            }
        }
        return changed;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}
