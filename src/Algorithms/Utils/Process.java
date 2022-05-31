package Algorithms.Utils;

import Algorithms.Frames.Frames;
import Algorithms.Frames.LRUHelper;

import java.util.List;
import java.util.TreeSet;

public class Process {
    public final int id;
    public List<Integer> references;
    // Proportional
    public int referenceRange;

    public int framesBeforeStop;
    public int framesAmount;
    public int faults;
    public int currentReference;
    public double pageFaultRatio;
    public boolean stopped;
    public boolean completed;

    public Frames frames;
    public LRUHelper lruHelper;
    public TreeSet<Integer> uniqueReferences;

    public Process(int id, List<Integer> references, int referenceRange) {
        this.id = id;
        this.references = references;
        this.referenceRange = referenceRange;

        lruHelper = new LRUHelper();
        uniqueReferences = new TreeSet<>();
    }

    public void updatePageFaultRatio(int faults) {
        this.faults += faults;
        pageFaultRatio = (double) this.faults / (currentReference + 1);
    }

    public void createFrames(int framesAmount) {
        this.framesAmount = framesAmount;
        frames = new Frames(framesAmount);
    }

    public void decreaseFrames() {
        framesAmount--;
        frames.decreaseSize();
    }

    public void increaseFrames() {
        framesAmount++;
        frames.increaseSize();
    }

    public void setFrames(int amount) {
        if (amount == 0) {
            framesBeforeStop = framesAmount;
        }
        framesAmount = amount;
        frames.setSize(amount);
    }

    public void addReference(int index) {
        int reference = references.get(index);
        uniqueReferences.add(reference);
        lruHelper.add(reference);
        currentReference = index;
    }

    public int getUniqueReferences() {
        return uniqueReferences.size();
    }

    public void reset() {
        currentReference = 0;
        faults = 0;
        stopped = false;
        completed = false;

        lruHelper = new LRUHelper();
        uniqueReferences = new TreeSet<>();
    }
}
