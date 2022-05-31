package Algorithms;

import Algorithms.Utils.Process;
import Algorithms.Utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class Equal implements Algorithm {

    @Override
    public int start(List<Process> processes, int allFrames) {
        int framesPerProcess = allFrames / processes.size();
        int freeFrames = allFrames - framesPerProcess * processes.size();

        for (Process process : processes) {
            process.reset();

            int frames = framesPerProcess;
            if (freeFrames > 0) {
                frames++;
                freeFrames--;
            }

            process.createFrames(frames);
            int faults = Utils.manageFrames(process);
            process.currentReference = process.references.size();
            process.updatePageFaultRatio(faults);
        }
        AlgorithmHelper.printFramesAmount(processes);

        return processes.get(0).references.size();
    }

    @Override
    public String toString() {
        return "EQUAL";
    }
}
