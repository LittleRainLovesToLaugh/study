package com.xioayu.arithmetic.dynamic.array.single;

import com.xioayu.arithmetic.dynamic.array.AbstractList;
import com.xioayu.arithmetic.dynamic.array.List;

/**
 * Description 添加虚拟头结点,为了使得代码更加精简，防止索引为0时逻辑不能通用
 * 添加虚拟头结点后，索引为0的元素也无需特殊处理，使得代码逻辑可以通用。一般不建议这样使用，
 *
 * @author 小雨
 * createTime 2022年09月18日 22:40:00
 */
public class SingleLinkedList2<E> extends AbstractList<E> implements List<E> {


    private final Node<E> first;

    public SingleLinkedList2() {
        first = new Node<>(null, null);
    }

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

        @Override
        protected void finalize() throws Throwable {
            System.out.println("NODE对象释放：" + this);
            super.finalize();
        }
    }


    @Override
    public void clear() {
        size = 0;
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
        // 如果index为0，就代表前一个节点就是first，如果不是0，就是node方法查找出来的位置！
        Node<E> prev = index == 0 ? first : node(index - 1);
        // 当前节点插入到上一个节点的next（即当前索引节点） prev.next 当前索引的下一个节点对象信息
        prev.next = new Node<>(element, prev.next);
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        // 删除只需要把当前索引的上一个元素指向当前索引的下一个元素即可
        Node<E> prev = index == 0 ? first : node(index - 1);
        // 当前索引的元素
        Node<E> node = prev.next;
        prev.next = prev.next.next;
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        // Node<E> node = first.next;
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
        Node<E> node = first.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(",[");
        Node<E> node = first.next;
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
