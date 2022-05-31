package Algorithms.Frames;

import Algorithms.Utils.Process;

public class LRU {

    public int[] handleSwap(Process p) {
        int currReference = p.lruHelper.getLRU();
        while(!p.frames.contains(currReference)) {
            currReference = p.lruHelper.getLRU();
        }
        int newReference = p.references.get(p.currentReference);
        p.frames.swap(currReference, newReference);

        return new int[]{currReference, newReference};
    }

    @Override
    public String toString() {
        return "LRU";
    }
}
