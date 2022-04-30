package com.xioayu.jvm;

/**
 * Description 此代码演示了两点:
 * 1.对象可以在被GC时自我拯救。
 * 2.这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次
 *
 * @author XD
 * createTime 2022年04月19日 17:51:00
 */
public class FinalizeEscapeGc {

    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes,i am still alive: 是的，我还活着");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed finalize方法执行");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();
        // 对象第一次拯救自己
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级别很低，暂停0.5秒，以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no,i am dead:不,我死了");
        }
        // 下面这段代码与上面的完全相同，但是这次却自救失败了
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级别很低，暂停0.5秒，以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no,i am dead:不,我死了");
        }

    }


}
