package Algorithms;

import Algorithms.Utils.Process;
import Algorithms.Utils.Utils;

import java.util.*;

public class PFF implements Algorithm {
    private final int frequency;
    private final double lowFaultRatio;
    private final double maxFaultRatio;

    private int completedProcesses;
    private int stoppedProcesses;

    public PFF(int frequency, double lowFaultRatio, double maxFaultRatio) {
        this.frequency = frequency;
        this.lowFaultRatio = lowFaultRatio;
        this.maxFaultRatio = maxFaultRatio;
    }

    @Override
    public int start(List<Process> processes, int allFrames) {
        int freeFrames = AlgorithmHelper.setupAlgorithm(processes, allFrames);

        while (completedProcesses < processes.size()) {
            for (Process process : processes) {
                if (!process.completed) {
                    if (!process.stopped) {
                        int faults = Utils.manageFrames(process, frequency);
                        process.updatePageFaultRatio(faults);
                        if (process.currentReference >= frequency * 2) {
                            if (process.pageFaultRatio < lowFaultRatio) {
                                if (process.framesAmount > 1) {
                                    process.decreaseFrames();
                                    freeFrames++;
                                }
                            } else if (process.pageFaultRatio > maxFaultRatio) {
                                if (freeFrames > 0) {
                                    process.increaseFrames();
                                    freeFrames--;
                                } else {
                                    process.stopped = true;
                                    stoppedProcesses++;
                                    freeFrames += process.framesAmount;
                                    process.setFrames(0);
                                    System.out.println("Stopped " + process.id);
                                }
                            }
                        }
                        if (process.currentReference == process.references.size()) {
                            process.completed = true;
                            completedProcesses++;
                            System.out.println("Completed " + process.id);
                            freeFrames += process.framesAmount;
                            process.setFrames(0);
                        }
                    } else {
                        if (freeFrames > process.framesBeforeStop) {
                            process.setFrames(process.framesBeforeStop + 1);
                            freeFrames -= process.framesAmount;
                            process.stopped = false;
                            stoppedProcesses--;
                            System.out.println("Resumed " + process.id);
                        } else if (stoppedProcesses == processes.size() - completedProcesses) {
                            process.stopped = false;
                            stoppedProcesses--;
                            System.out.println("Resumed " + process.id);
                            process.setFrames(allFrames);
                            int faults = Utils.manageFrames(process);
                            process.updatePageFaultRatio(faults);
                            process.completed = true;
                            System.out.println("Completed " + process.id);
                            completedProcesses++;
                            process.setFrames(0);

                            freeFrames = allFrames;
                        }
                    }
                }
            }
            AlgorithmHelper.printFramesAmount(processes);
        }

        return -1;
    }

    @Override
    public String toString() {
        return "PAGE FAULT FREQUENCY";
    }
}
