package com.xiaoyu.test.testdemo;

import java.util.Random;

/**
 * TODO
 *
 * @author xiedi
 * @date 2022/7/5 16:07
 */
public class Enums {
    private static Random rand = new Random(47);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
}
