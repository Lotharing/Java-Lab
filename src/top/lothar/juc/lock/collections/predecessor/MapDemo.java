package top.lothar.juc.lock.collections.predecessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：     演示Map的基本用法
 */
public class MapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        System.out.println(map.isEmpty()); // 是否是空的
        map.put("东哥", 38); //放入k v
        map.put("西哥", 28);
        System.out.println(map.keySet()); // 返回一个key的set集合
        System.out.println(map.values()); // 返回一个value的collection集合
        System.out.println(map.get("西哥")); // 根据k获取v
        System.out.println(map.size()); // size
        System.out.println(map.containsKey("东哥")); //是否包含
        map.remove("东哥"); // 移除
        System.out.println(map.containsKey("东哥"));

    }
}
