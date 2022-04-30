package com.xioayu.interview;

/**
 * Description
 *
 * @author XD
 * createTime 2022年03月04日 19:52:00
 */
public class Recursion {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(f(42));
        long end = System.currentTimeMillis();
        System.out.printf("耗时毫秒为：%d", end - start);
    }

    /**
     * 有n步台阶，一次只能上1步或2步，共有多少种走法的递归代码实现
     * 性能损耗比较严重，特别是数据量大的时候，还会有可能造成栈溢出
     *
     * @param n 待计算的值
     * @return 计算结果
     */
    public static int f(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        return f(n - 2) + f(n - 1);
    }
}
