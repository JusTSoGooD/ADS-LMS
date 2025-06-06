package by.it.group351051.kuchmel.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {

    private E[] elements;
    private int size;
    private int front;
    private int rear;

    // Инициализация с начальной емкостью
    public MyArrayDeque() {
        elements = (E[]) new Object[10]; // Начальная емкость
        size = 0;
        front = 0;
        rear = 0;
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int current = front;
        for (int i = 0; i < size; i++) {
            sb.append(elements[current]);
            if (i < size - 1) {
                sb.append(", ");
            }
            current = (current + 1) % elements.length;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public void addFirst(E element) {
        ensureCapacity();
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        ensureCapacity();
        elements[rear] = element;
        rear = (rear + 1) % elements.length;
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E element() {
        if (size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        return elements[front];
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        return elements[front];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        int last = (rear - 1 + elements.length) % elements.length;
        return elements[last];
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E element = elements[front];
        front = (front + 1) % elements.length;
        size--;
        return element;
    }

    @Override
    public E pollFirst() {
        return poll();
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        rear = (rear - 1 + elements.length) % elements.length;
        E element = elements[rear];
        size--;
        return element;
    }

    // Проверка и увеличение емкости массива
    private void ensureCapacity() {
        if (size == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[(front + i) % elements.length];
            }
            elements = newElements;
            front = 0;
            rear = size;
        }
    }
}
