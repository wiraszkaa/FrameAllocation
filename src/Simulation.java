import Algorithms.Algorithm;
import Algorithms.Utils.Process;
import Algorithms.Utils.Utils;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulation {
    private final int frames;
    private final List<Process> processes;

    private final JPanel panel;

    public Simulation(PageReferenceBuilder prb, int framesAmount) {
        this.frames = framesAmount;
        this.processes = new ArrayList<>();

        for (int i = 0; i < prb.getProcessesAmount(); i++) {
            processes.add(new Process(i, prb.create(), prb.getReferenceRange(i)));
        }

        panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
    }

    public void start(Algorithm algorithm) {
        System.out.println(algorithm);

        algorithm.start(processes, frames);

        List<String> processesFaultsFreq = new LinkedList<>();
        processes.forEach((process -> {
            processesFaultsFreq.add(String.format("%s: %.0f%%", process.id, process.pageFaultRatio * 100));
        }));
        System.out.println("\nPage Fault Percentage\n" + processesFaultsFreq + "\n\n");
    }

    public void showVisuals() {
        Utils.show(panel, "Frames");
    }

    private String getTime(long nanoseconds) {
        StringBuilder sb = new StringBuilder();
        if (nanoseconds < 1_000_000) {
            return nanoseconds + "ns";
        }
        if (nanoseconds > 60_000_000_000L) {
            long temp = nanoseconds / 60_000_000_000L;
            nanoseconds -= temp * 60_000_000_000L;
            sb.append(temp).append(" min, ");
        }
        if (nanoseconds > 1_000_000_000) {
            long temp = nanoseconds / 1_000_000_000;
            nanoseconds -= temp * 1_000_000_000;
            sb.append(temp).append(" s, ");
        }
        if (nanoseconds > 1_000_000) {
            long temp = nanoseconds / 1_000_000;
            sb.append(temp).append(" ms");
        }
        return sb.toString();
    }
}
