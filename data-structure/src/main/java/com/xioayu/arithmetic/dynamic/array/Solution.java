package com.xioayu.arithmetic.dynamic.array;

/**
 * Description
 *
 * @author 小雨
 * createTime 2022年09月04日 20:42:00
 */
public class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }
        int first = 0;
        int sencond = 1;
        for (int i = 0; i < n -1; i++) {
            int sum = first + sencond;
            first = sencond;
            sencond = sum;
        }
        return sencond;
    }
}
