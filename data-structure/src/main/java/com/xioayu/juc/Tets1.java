package com.xioayu.juc;

import java.util.concurrent.TimeUnit;

/**
 * Description 8锁现象彻底理解锁 Synchronized 锁的理解
 *
 * @author 小雨
 * createTime 2022年06月05日 11:59:00
 */
public class Tets1 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(phone::sendSms, "A").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phone.call();
        }, "B").start();
    }
}

class Phone {
    public synchronized void sendSms() {
        System.out.println(Thread.currentThread().getName() + "发短信");
    }

    public synchronized void call() {
        System.out.println(Thread.currentThread().getName() + "打电话");
    }
}

