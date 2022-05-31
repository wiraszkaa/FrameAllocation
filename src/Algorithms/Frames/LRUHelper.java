package Algorithms.Frames;

import java.util.LinkedList;
import java.util.List;

public class LRUHelper {
    private final List<Integer> list;

    public LRUHelper() {
        list = new LinkedList<>();
    }

    public void add(int reference) {
        if (list.contains(reference)) {
            list.remove((Integer) reference);
        }
        list.add(reference);
    }

    public int getLRU() {
        int result = list.get(0);
        list.remove((Integer) result);
        return result;
    }
}
