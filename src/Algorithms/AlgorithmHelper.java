package Algorithms;

import Algorithms.Utils.Process;

import java.util.LinkedList;
import java.util.List;

public class AlgorithmHelper {

    public static int[] allocateFramesProportionally(List<Process> processes, int allFrames) {
        int[] allocation = new int[processes.size() + 1];
        int freeFrames = allFrames;
        int allReferenceRange = processes.stream().mapToInt((o) -> o.referenceRange).sum();

        for (int i = 0; i < allocation.length - 1; i++) {
            int frames = Math.max(processes.get(i).referenceRange * allFrames / allReferenceRange, 1);
            freeFrames -= frames;
            allocation[i] = frames;
        }
        allocation[allocation.length - 1] = freeFrames;

        return allocation;
    }

    public static int setupAlgorithm(List<Process> processes, int allFrames) {
        int[] framesPerProcess = allocateFramesProportionally(processes, allFrames);
        int freeFrames = framesPerProcess[framesPerProcess.length - 1];

        processes.forEach((o) -> {
            o.reset();
            o.createFrames(framesPerProcess[o.id]);
        });
        return freeFrames;
    }

    public static void printFramesAmount(List<Process> processes) {
        List<String> processesFramesAmount = new LinkedList<>();
        processes.forEach((process -> {
            processesFramesAmount.add(String.format("%s: (%s)", process.id, process.framesAmount));
        }));
        System.out.println("Frames Amount\n" + processesFramesAmount);
    }
}
