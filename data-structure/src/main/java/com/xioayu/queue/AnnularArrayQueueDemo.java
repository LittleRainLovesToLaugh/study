package com.xioayu.queue;

import java.util.Scanner;

/**
 * Description 数组实现环形队列
 *
 * @author XD
 * createTime 2022年02月25日 15:56:00
 */
public class AnnularArrayQueueDemo {

    public static void main(String[] args) {
        System.out.println("环形队列测试：");
        // 创建一个队列
        AnnularArray arrayQueue = new AnnularArray(4);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据");
            System.out.println("g(get):获取数据");
            System.out.println("h(head):查看队列都的数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.printf("取出的头部数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出！");
    }

}

class AnnularArray {

    /**
     * 数组的最大容量
     */
    private int maxSize;

    /**
     * 队列头,指向队列的第一个元素，初始值=0
     */
    private int front;
    /**
     * 队列尾，指向队列的最后一个元素的后一个位置，空出一个位置作为约定，初始值=0
     */
    private int rear;

    /**
     * 存放数据，模拟队列
     */
    private int[] arr;

    public AnnularArray(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    /**
     * 判断队列是否满
     */
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    /**
     * 判断队列是否为空
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /**
     * 添加数据到队列
     */
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列已满，不能加入数据！");
            return;
        }
        arr[rear] = n;
        // rear后移，必须考虑取模
        rear = (rear + 1) % maxSize;
    }

    /**
     * 获取数据
     */
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能获取数据！");
        }
        // 如果直接使用front返回就无法进行front后移操作了，所以需要一个临时变量
        int value = arr[front];
        // 后移front
        front = (front + 1) % maxSize;
        return value;
    }

    /**
     * 显示队列的所有数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
            return;
        }
        // 只遍历有效数据，从front开始遍历，遍历
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    /**
     * 当前队列有效数据的个数
     */
    public int size() {
        // 假设rear=1,front=0,maxSize=3, (1+3 -0) % 3 =1 ，此时的数组应该是 array[0] =有值的状态，其余为默认值0
        // 假设rear=2,front=1,maxSize=3, (2+3 -1) % 3 =1，此时的数组状态是已经获取了一个数据出去，array[1] =有值的状态
        return (rear + maxSize - front) % maxSize;
    }

    /*
     * 显示队列的头数据
     */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，没有数据！");
        }
        return arr[front];
    }
}
