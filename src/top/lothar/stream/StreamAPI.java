package top.lothar.stream;

import top.lothar.BaseMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : LuTong.Zhao
 * @date : 11:39 2020/8/6
 */
public class StreamAPI extends BaseMethod {

    /**
     * 可接收一个泛型对象或可变成泛型集合，构造一个 Stream 对象。
     */
    private static void of(){
        Stream<String> stringStream = Stream.of("a","b","c");
    }

    /**
     * 创建一个空的 Stream 对象。
     */
    private static void empty(){
        Stream<String> stringStream = Stream.empty();
    }

    /**
     * 连接两个 Stream ，不改变其中任何一个 Steam 对象，返回一个新的 Stream 对象
     */
    private static void concatStream(){
        Stream<String> a = Stream.of("a","b","c");
        Stream<String> b = Stream.of("d","e");
        Stream<String> c = Stream.concat(a,b);
        info(c.collect(Collectors.toList()));
    }

    /**
     * 最大值
     *
     *  min
     * 与 max 用法一样，只不过是求最小值。
     */
    private static void max(){
        Stream<Integer> integerStream = Stream.of(2, 2, 100, 5);
        Integer max = integerStream.max(Integer::compareTo).get();
        info(max);
    }

    /**
     * findFirst
     * 获取 Stream 中的第一个元素。
     *
     * findAny
     * 获取 Stream 中的某个元素，如果是串行情况下，一般都会返回第一个元素，并行情况下就不一定了。
     */
    private static void findFirst(){
        Stream<Integer> integerStream = Stream.of(2, 2, 100, 5);
        Optional<Integer> first = integerStream.findFirst();
        info(first.get());
    }

    /**
     * 返回元素个数
     */
    private static void count(){
        Stream<Integer> integerStream = Stream.of(2, 2, 100, 5);
        info(integerStream.count());
    }

    /**
     * 建立一个通道，在这个通道中对 Stream 的每个元素执行对应的操作，对应 Consumer<T>的函数式接口，这是一个消费者函数式接口，
     * 顾名思义，它是用来消费 Stream 元素的，
     * 比如下面这个方法，把每个元素转换成对应的大写字母并输出,但collect出的还是小写的，因为它只是消费，并不转型变更
     */
    private static void peek() {
        Stream<String> a = Stream.of("a", "b", "c");
        List<String> list = a.peek(e-> info(e.toUpperCase())).collect(Collectors.toList());
        info(list.toString());
    }

    /**
     * 和 peek 方法类似，都接收一个消费者函数式接口，可以对每个元素进行对应的操作，但是和 peek 不同的是，forEach 执行之后，这个 Stream 就真的被消费掉了，之后这个 Stream 流就没有了，
     * 不可以再对它进行后续操作了，而 peek操作完之后，还是一个可操作的 Stream 对象。
     *
     * forEachOrdered
     * 功能与 forEach是一样的，不同的是，forEachOrdered是有顺序保证的，也就是对 Stream 中元素按插入时的顺序进行消费。
     * 为什么这么说呢，当开启并行的时候，forEach和 forEachOrdered的效果就不一样了
     *
     */
    private static void forEach() {
        Stream<String> a = Stream.of("a", "b", "c");
        a.forEach(e->info(e.toUpperCase()));
    }

    /**
     * 获取前 n 条数据，类似于 MySQL 的limit，只不过只能接收一个参数，就是数据条数。
     */
    private static void limit() {
        Stream<String> a = Stream.of("a", "b", "c");
        a.limit(2).forEach(e->info(e));
    }

    /**
     * 跳过前 n 条数据，例如下面代码，返回结果是 c
     */
    private static void skip() {
        Stream<String> a = Stream.of("a", "b", "c");
        a.skip(2).forEach(e->info(e));
    }

    /**
     * 元素去重，例如下面方法返回元素是 a、b、c，将重复的 b 只保留了一个
     */
    private static void distinct() {
        Stream<String> a = Stream.of("a", "b", "c","b");
        a.distinct().forEach(e->info(e));
    }

    /**
     * 有两个重载，一个无参数，另外一个有个 Comparator类型的参数。
     *
     * 无参类型的按照自然顺序进行排序，只适合比较单纯的元素，比如数字、字母等。
     */
    private static void sorted() {
        Stream<String> a = Stream.of("a", "c", "b");
        a.sorted().forEach(e->info(e));
    }

    /**
     * 有参数的需要自定义排序规则，例如下面这个方法，按照第二个字母的大小顺序排序，比较每个字符的截取1后位,1 输出最后输出的结果是 a1、b3、c6。
     */
    private static void sortedWithComparator() {
        Stream<String> a = Stream.of("a1", "c6", "b3");
        a.sorted((x,y)->Integer.parseInt(x.substring(1))>Integer.parseInt(y.substring(1))?1:-1).forEach(e->System.out.println(e));
    }

    /**
     * 筛选过滤数据
     */
    private static void filter(){
        Stream<Integer> num = Stream.of(1, 3, 5,6);
        num.filter(n -> n>3 && n==5).forEach(e->info(e));
    }

    /**
     * map方法的接口方法声明如下，接受一个 Function函数式接口，把它翻译成映射最合适了，通过原始数据元素，映射出新的类型。
     * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
     * 而 Function的声明是这样的，源码观察 apply方法，接受一个 T 型参数，返回一个 R 型参数。用于将一个类型转换成另外一个类型正合适，这也是 map的初衷所在，用于改变当前元素的类型，
     * 例如将 Integer 转为 String类型，将 DAO 实体类型，转换为 DTO 实例类型。
     * 当然了，T 和 R 的类型也可以一样，这样的话，就和 peek方法没什么不同了。
     */
    private static void map(){
        Stream<Integer> num = Stream.of(1, 2, 3);
        List<String> collect = num.map(e -> String.valueOf(e)).collect(Collectors.toList());
        info(collect.toString());
    }

    /**
     * mapToInt
     * 将元素转换成 int 类型，在 map方法的基础上进行封装。
     *
     * mapToLong
     * 将元素转换成 Long 类型，在 map方法的基础上进行封装。
     *
     * mapToDouble
     * 将元素转换成 Double 类型，在 map方法的基础上进行封装。
     */
    private static void mapToInt(){
        Stream<String> num = Stream.of("one", "two", "three");
        IntStream intStream = num.mapToInt(String::length);
        intStream.forEach(e -> info(e));
    }

    /**
     * 这是用在一些比较特别的场景下，当你的 Stream 是以下这几种结构的时候，需要用到 flatMap方法，用于将原有二维结构扁平化。
     * Stream<String[]>
     * Stream<Set<String>>
     * Stream<List<String>>
     * 以上这三类结构，通过 flatMap方法，可以将结果转化为 Stream<String>这种形式，方便之后的其他操作。
     * 比如下面这个方法，将List<List<String>>扁平处理，然后再使用 map或其他方法进行操作。
     * 白话： 遍历一层stream , 在把stream处理 ，类似双层for循环
     *
     * flatMapToInt
     * 用法参考 flatMap，将元素扁平为 int 类型，在 flatMap方法的基础上进行封装。
     * flatMapToLong
     * 用法参考 flatMap，将元素扁平为 Long 类型，在 flatMap方法的基础上进行封装。
     * flatMapToDouble
     * 用法参考 flatMap，将元素扁平为 Double 类型，在 flatMap方法的基础上进行封装。
     *
     */
    private static void flatMap(){
        List<String> name = new ArrayList<>();
        name.add("LT");
        name.add("TL");
        List<String> address = new ArrayList<>();
        address.add("CHINA");
        List<List<String>> lists = new ArrayList<>();
        lists.add(name);
        lists.add(address);
        //结构 [ ['LT','TL'] , ['CHINA'] ]
        Stream<List<String>> stream = lists.stream();
        List<String> collect = stream.flatMap(infoList -> infoList.stream()).map(mes -> mes.toLowerCase()).collect(Collectors.toList());
        collect.forEach(e -> info(e));
    }

    private static void collectors(){
        // 收集器：可以看源码
    }

    public static void main(String[] args) {
        flatMap();
    }


}
