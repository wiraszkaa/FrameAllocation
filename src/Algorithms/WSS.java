package Algorithms;

import Algorithms.Utils.Process;
import Algorithms.Utils.Utils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class WSS implements Algorithm {
    private final int frequency;
    private int allFrames;
    private int stoppedProcesses;
    private int completedProcesses;
    private boolean validation;
    private boolean checkStopped;

    public WSS(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int start(List<Process> processes, int allFrames) {
        AlgorithmHelper.setupAlgorithm(processes, allFrames);
        this.allFrames = allFrames;

        while (completedProcesses < processes.size()) {
            processes.sort(Comparator.comparingInt((o) -> o.framesAmount));
            for (Process process : processes) {
                if (!process.stopped && !process.completed) {
                    int faults = Utils.manageFrames(process, frequency);
                    process.updatePageFaultRatio(faults);

                    if (process.currentReference >= process.references.size()) {
                        process.completed = true;
                        process.setFrames(0);
                        completedProcesses++;
                        System.out.println("Completed " + process.id);
                        checkStopped = true;
                    }
                }
            }
            int referencesSum = checkProcesses(processes);
            if (referencesSum > allFrames) {
                handleProcessStop(processes, referencesSum - allFrames);
                referencesSum = checkProcesses(processes);
                if (referencesSum <= allFrames) {
                    if (validation) {
                        handleFramesSize(processes);
                        validation = false;
                    }
                }
            } else {
                if (validation) {
                    handleFramesSize(processes);
                    validation = false;
                }
                if (checkStopped) {
                    while (referencesSum <= allFrames && stoppedProcesses > 0) {
                        Process stoppedProcess = getSmallestStopped(processes);
                        if (stoppedProcess != null) {
                            referencesSum += stoppedProcess.getUniqueReferences();
                            if (referencesSum <= allFrames || processes.size() - completedProcesses == 1) {
                                stoppedProcess.stopped = false;
                                stoppedProcesses--;
                                System.out.println("Resumed " + stoppedProcess.id);
                                stoppedProcess.setFrames(Math.min(stoppedProcess.getUniqueReferences(), allFrames));
                            }
                            checkStopped = false;
                        }
                    }
                }
            }
            AlgorithmHelper.printFramesAmount(processes);
        }

        return -1;
    }

    private void handleFramesSize(List<Process> processes) {
        for (Process process : processes) {
            if (!process.completed) {
                if (!process.stopped) {
                    process.setFrames(process.getUniqueReferences());
                }
            }
        }
    }

    private void handleProcessStop(List<Process> processes, int frames) {
        if (stoppedProcesses == processes.size() - completedProcesses) {
            Process stoppedProcess = getSmallestStopped(processes);
            if (stoppedProcess != null) {
                stoppedProcess.stopped = false;
                stoppedProcesses--;
                System.out.println("Resumed " + stoppedProcess.id);
                stoppedProcess.setFrames(allFrames);
            }
        } else {
            int freeFrames = 0;
            for (Process process : processes) {
                if (!process.stopped && !process.completed) {
                    process.stopped = true;
                    stoppedProcesses++;
                    System.out.println("Stopped " + process.id);
                    freeFrames += process.framesAmount;
                    process.setFrames(0);
                    if (freeFrames >= frames) {
                        break;
                    }
                }
            }
            if (stoppedProcesses == processes.size() - completedProcesses) {
                Process stoppedProcess = getSmallestStopped(processes);
                if (stoppedProcess != null) {
                    stoppedProcess.stopped = false;
                    stoppedProcesses--;
                    System.out.println("Resumed " + stoppedProcess.id);
                    stoppedProcess.setFrames(allFrames);
                }
            }
        }
    }

    private int checkProcesses(List<Process> processes) {
        int referencesSum = 0;
        for (Process process : processes) {
            if (!process.completed && !process.stopped) {
                referencesSum += process.getUniqueReferences();
                if (process.framesAmount < process.getUniqueReferences()) {
                    validation = true;
                }
            }
        }
        return referencesSum;
    }

    private Process getSmallestStopped(List<Process> processes) {
        for (Process process : processes) {
            if (!process.completed) {
                if (process.stopped) {
                    return process;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WORKING SETS";
    }
}
