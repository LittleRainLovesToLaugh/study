package com.xioayu.interview;

/**
 * Description 类初始化和实例初始化的代码执行顺序
 *
 * @author XD
 * createTime 2022年03月03日 16:36:00
 */
public class Father {

    private final int i = test();

    private static final int j = method();

    static {
        System.out.println("(1)");
    }

    public Father() {
        System.out.println("(2)");
    }

    {
        System.out.println("(3)");
    }

    public int test() {
        System.out.println("(4)");
        return 1;
    }

    public static int method() {
        System.out.println("(5)");
        return 1;
    }
}


