package cn.partner.leetcode;

/**
 *  打印零与奇偶数
 *
 *  假设有这么一个类：
 *
 * class ZeroEvenOdd {
 *   public ZeroEvenOdd(int n) { ... }      // 构造函数
 *   public void zero(printNumber) { ... }  // 仅打印出 0
 *   public void even(printNumber) { ... }  // 仅打印出 偶数
 *   public void odd(printNumber) { ... }   // 仅打印出 奇数
 * }
 * 相同的一个 ZeroEvenOdd 类实例将会传递给三个不同的线程：
 *
 * 线程 A 将调用 zero()，它只输出 0 。
 * 线程 B 将调用 even()，它只输出偶数。
 * 线程 C 将调用 odd()，它只输出奇数。
 * 每个线程都有一个 printNumber 方法来输出一个整数。请修改给出的代码以输出整数序列 010203040506... ，其中序列的长度必须为 2n。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：n = 2
 * 输出："0102"
 * 说明：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
 * 示例 2：
 *
 * 输入：n = 5
 * 输出："0102030405"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-zero-even-odd
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

// LeetCode start
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ZeroEvenOdd {
    private int n;

    private volatile int i;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition evenCondition = lock.newCondition();
    private final Condition oddCondition = lock.newCondition();

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        for(i = 1; i <= n; i++) {
            printNumber.accept(0);
            Condition c;
            if (i % 2 == 0) {
                c = evenCondition;
            } else {
                c = oddCondition;
            }
            c.signal();
            c.await();
        }
        oddCondition.signal();
        evenCondition.signal();
        lock.unlock();
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        while (n % 2 == 0 ? i <= n : i <= n-1) {
            if (i % 2 == 0) {
                printNumber.accept(i);
            }
            evenCondition.signal();
            evenCondition.await();
        }
        lock.unlock();
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        while (n % 2 == 0 ? i <= n - 1 : i <= n) {
            if (i % 2 != 0) {
                printNumber.accept(i);
            }
            oddCondition.signal();
            oddCondition.await();
        }
        lock.unlock();
    }
}
// LeetCode end

// Test
public class No_1116_Con_0003_PrintZeroEvenOdd {
    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);
        IntConsumer intConsumer = new IntConsumer();
        new Thread(() -> {
            try {
                zeroEvenOdd.zero(intConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.even(intConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(intConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

/**
 * Output:
 * 0102030405
 */