package top.lothar.juc.lock.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * 描述：     演示LongAccumulator的用法非常灵活 ,更通用版本的Adder累加器
 *
 * 场景：     1.大量计算 ,多线程并行计算  2.线程计算顺序不影响结果的场景 不取决与顺序
 */
public class LongAccumulatorDemo {

    public static void main(String[] args) {
        // x初始值 y下一个值(算出来的)
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 1);

        //更灵活的可以用math函数 - 一系列的计算操作都可以灵活更改很方便
        //LongAccumulator accumulator = new LongAccumulator((x, y) -> Math.max(x,y), 1);
        /**
         * 类似数学归纳法的计算
         */
        //accumulator.accumulate(1);// x 是1 identity是1 累加后是 2 = y
        //accumulator.accumulate(2);// x 是2 y是2 列累加后是4
        //accumulator.accumulate(1);// x 是1 y是4 列累加后是5
        //System.out.println(accumulator.getThenReset());

        ExecutorService executor = Executors.newFixedThreadPool(8);
        // 1 - 9 的计算 x[1....9]
        IntStream.range(1, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));
        //关闭线程池 执行完任务
        executor.shutdown();
        while (!executor.isTerminated()) {
            //确认任务执行完
        }
        System.out.println(accumulator.getThenReset());
    }
}
