package Algorithms;

import Algorithms.Utils.Process;
import Algorithms.Utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class Proportional implements Algorithm {

    @Override
    public int start(List<Process> processes, int allFrames) {
        int[] framesPerProcess = AlgorithmHelper.allocateFramesProportionally(processes, allFrames);

        for (Process process : processes) {
            process.reset();

            process.createFrames(framesPerProcess[process.id]);
            int faults = Utils.manageFrames(process);
            process.currentReference = process.references.size();
            process.updatePageFaultRatio(faults);
        }
        AlgorithmHelper.printFramesAmount(processes);

        return processes.get(0).references.size();
    }


    @Override
    public String toString() {
        return "PROPORTIONAL";
    }
}
