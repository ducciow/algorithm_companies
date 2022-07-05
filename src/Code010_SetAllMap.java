import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 05, 07, 2022
 * @Description: Implement a Map with an extra function setAll() that set all its item to be an given value in O(1).
 * @Note:   - Introduce an extra attribute: time for each element indicating its put time.
 *          - Introduce two extra attributes: time for tracking the global map time, and time of the last
 *            setAll operation.
 *          - Return accordingly.
 */
public class Code010_SetAllMap {

    public static class MyValue<V> {
        V value;
        long time;

        public MyValue(V v, long t) {
            value = v;
            time = t;
        }
    }

    public static class MyMap<K, V> {
        private HashMap<K, MyValue<V>> map;
        private long time;
        private MyValue<V> lastSetting;

        public MyMap() {
            map = new HashMap<>();
            time = 0;
            lastSetting = new MyValue<>(null, -1);
        }

        public void put(K key, V val) {
            map.put(key, new MyValue<>(val, time++));
        }

        public void setAll(V val) {
            lastSetting = new MyValue<>(val, time++);
        }

        public V get(K key) {
            if (!map.containsKey(key)) {
                return null;
            }
            MyValue<V> theV = map.get(key);
            return theV.time > lastSetting.time ? theV.value : lastSetting.value;
        }

    }

}
