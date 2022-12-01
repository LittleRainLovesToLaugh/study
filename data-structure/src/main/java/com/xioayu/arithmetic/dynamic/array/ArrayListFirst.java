package com.xioayu.arithmetic.dynamic.array;

@SuppressWarnings("unchecked")

/**
 * 有动态缩容操作，动态数组，添加first提升添加和删除首节点元素的性能
 *
 */
public class ArrayListFirst<E> extends AbstractList<E> {
    /**
     * 所有的元素
     */
    private E[] elements;

    /**
     * 默认指向第一个数组元素，用来删除第一个元素，或者添加第一个元素
     */
    private int first = 0;

    private static final int DEFAULT_CAPACITY = 10;

    public ArrayListFirst(int capaticy) {
        capaticy = Math.max(capaticy, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capaticy];
    }

    public ArrayListFirst() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;

        // 仅供参考
        if (elements != null && elements.length > DEFAULT_CAPACITY) {
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        }
    }

    /**
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    public E get(int index) { // O(1)
        rangeCheck(index);

        return elements[index];
    }

    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    public E set(int index, E element) { // O(1)
        rangeCheck(index);

        E old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * 在index位置插入一个元素
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        /*
         * 最好：O(1)
         * 最坏：O(n)
         * 平均：O(n)
         */
        rangeCheckForAdd(index);

        ensureCapacity(size + 1);
        if (index == 0) {
            // elements
        } else {
            for (int i = size; i > index; i--) {
                elements[i] = elements[i - 1];
            }
            elements[index] = element;
            size++;
        }
    } // size是数据规模

    /**
     * 删除index位置的元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        /*
         * 最好：O(1)
         * 最坏：O(n)
         * 平均：O(n)
         */
        rangeCheck(index);

        E old = elements[index];
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        elements[--size] = null;

        trim();

        return old;
    }

    /**
     * 查看元素的索引
     *
     * @param element
     * @return
     */
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 保证要有capacity的容量
     *
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        // 新容量为旧容量的2倍
        // int newCapacity = oldCapacity << 1;
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;

        System.out.println(oldCapacity + "扩容为" + newCapacity);
    }

    /**
     * 缩容操作，释放内存空间，注意，如果缩容和扩容倍数设计得不得当，有可能会导致复杂度震荡
     */
    private void trim() {
        // 数组的长度假设为：30
        int oldCapacity = elements.length;
        // 右移一位，就是除以2 =15
        int newCapacity = oldCapacity >> 1;
        // oldCapacity是数组的长度，而size为当前数据所有的元素的长度，所以当size>一半和默认大小时的时候无需缩容操作
        if (size > (newCapacity) || oldCapacity <= DEFAULT_CAPACITY) return;

        // 剩余空间还很多
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;

        System.out.println(oldCapacity + "缩容为" + newCapacity);
    }

    @Override
    public String toString() {
        // size=3, [99, 88, 77]
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(elements[i]);

//			if (i != size - 1) {
//				string.append(", ");
//			}
        }
        string.append("]");
        return string.toString();
    }
}
