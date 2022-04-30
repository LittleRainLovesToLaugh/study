package com.xioayu.juc;

/**
 * Description
 *
 * @author XD
 * createTime 2022年03月29日 14:24:00
 */
public class LockTest {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类,把资源类丢入线程
        Ticket ticket = new Ticket();

        // @FunctionalInterface 函数式接口，jdk1.8 lambda 表达式 (参数) -> {代码}
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();

    }
}

// 资源类 OOP(面向对象程序设计)
class Ticket {
    // 属性、方法
    private int number = 30;

    // 卖票的方式
    // synchronized本质：队列，锁（对象，class）
    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出了" + (number--) + "票，剩余：" + number);
        }
    }
}
