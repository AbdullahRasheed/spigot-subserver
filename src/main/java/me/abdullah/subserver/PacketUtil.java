package me.abdullah.subserver;

import java.lang.reflect.Array;
import java.util.function.Function;

public class PacketUtil {

    // Recommend against using this for time-sensitive packets because the use of reflection
    // can considerably impact time
    public static <T, R> R[] toArray(T[] arr, Class<R> clazz, Function<T, R> converter){
        R[] r = (R[]) Array.newInstance(clazz, arr.length);
        for (int i = 0; i < arr.length; i++) {
            r[i] = converter.apply(arr[i]);
        }

        return r;
    }
}
