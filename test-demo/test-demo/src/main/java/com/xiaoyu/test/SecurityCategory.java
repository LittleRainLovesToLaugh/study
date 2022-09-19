package com.xiaoyu.test;

import com.xiaoyu.test.testdemo.Enums;

/**
 * TODO
 *
 * @author xiedi
 * @date 2022/7/5 16:06
 */
enum SecurityCategory {
    /**
     *
     */
    STOCK(Security.Stock.class),
    BOND(Security.Bond.class);
    final Security[] values;

    SecurityCategory(Class<? extends Security> kind) {
        // 此 Class 对象表示的枚举类的值
        values = kind.getEnumConstants();
    }

    interface Security {
        enum Stock implements Security {
            SHORT, LONG, MARGIN
        }

        enum Bond implements Security {
            MUNICIPAL, JUNK
        }
    }

    public Security randomSelection() {
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            SecurityCategory category =
                    Enums.random(SecurityCategory.class);
            System.out.println(category + ": " +
                    category.randomSelection());
        }
    }
}
