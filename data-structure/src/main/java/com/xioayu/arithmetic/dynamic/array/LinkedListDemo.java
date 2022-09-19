package com.xioayu.arithmetic.dynamic.array;

/**
 * Description LinkedList练习
 *
 * @author 小雨
 * createTime 2022年09月18日 22:40:00
 */
public class LinkedListDemo<E> extends AbstractList<E> implements List<E> {

    private int size;

    private Node<E> first;

    /**
     * 节点信息
     */
    private static class Node<E> {

        E element;

        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }


    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0) {
            first.element = element;
            first.next = null;
            // first = new Node<>(element, first);
        } else {
            // 获取当前索引的上一个节点
            Node<E> prev = node(index - 1);
            // 当前节点插入到上一个节点的next（即当前索引节点） prev.next 当前索引的下一个节点对象信息
            prev.next = new Node<>(element, prev.next);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        if (index == 0) {
            first = first.next;
        } else {
            // 删除只需要把当前索引的上一个元素指向当前索引的下一个元素即可
            Node<E> preb = node(index - 1);
            // 当前索引的元素
            // Node<E> next = preb.next;
            preb.next = preb.next.next;
        }
        return null;
    }

    @Override
    public int indexOf(E element) {
        return 0;
    }

    /**
     * 查找指定索引位置元素，索引为多少就需要查找多少次！
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
