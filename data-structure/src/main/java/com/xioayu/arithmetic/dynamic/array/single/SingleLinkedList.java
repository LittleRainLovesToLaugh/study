package com.xioayu.arithmetic.dynamic.array.single;

import com.xioayu.arithmetic.dynamic.array.AbstractList;
import com.xioayu.arithmetic.dynamic.array.List;

/**
 * Description LinkedList练习
 *
 * @author 小雨
 * createTime 2022年09月18日 22:40:00
 */
public class SingleLinkedList<E> extends AbstractList<E> implements List<E> {

    /**
     * 头结点
     */
    private Node<E> first;
    /**
     * 尾节点
     */
    private Node<E> last;

    /**
     * 节点信息
     */
    private static class Node<E> {

        /**
         * 元素节点
         */
        E element;
        /**
         * 上一个节点元素
         */
        Node<E> prev;
        /**
         * 下一个节点元素
         */
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("NODE对象释放：" + this);
            super.finalize();
        }
    }


    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
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
        // 往最后面添加元素
        if (index == size) {
            // 待添加元素的下一个节
            Node<E> oldLast = last;
            last = new Node<>(oldLast, element, first);
            // 这是链表添加的第一个元素
            if (oldLast == null) {
                first = last;
            } else {
                oldLast.next = last;
            }
        } else {
            rangeCheckForAdd(index);
            // 待添加元素的下一个节点
            Node<E> next = node(index);
            // 待添加元素的上一个节点
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(prev, element, next); // 插入当前元素，并给对应的节点赋值
            next.prev = node;   // 更改下节点的上节点指针
            // index == 0的情况
            if (prev == null) {
                first = node;
            } else {
                prev.next = node;    // 更改上节点的下节点指针
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev == null) { // index==0
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) { //index ==size -1
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 查找指定索引位置元素，索引为多少就需要查找多少次！
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        if (index < (size >> 1)) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(",[");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(",");
            }
            string.append(node.element);
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
