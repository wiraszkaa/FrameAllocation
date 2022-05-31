import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PageReferenceBuilder {
    private final int references;
    private final int processes;
    private int lastProcess;
    private int lastProcessMax;

    private List<Integer> referenceRange;

    private int localityDensity;
    private int localityChance;
    private List<Integer> localityRange;

    public PageReferenceBuilder(int referencesAmount, int processesAmount) {
        references = referencesAmount;
        processes = processesAmount;

        lastProcessMax = -1;

        referenceRange = new ArrayList<>();
        localityRange = new ArrayList<>();
        for (int i = 0; i < processes; i++) {
            int range = random(6, 10);
            referenceRange.add(range);
            localityRange.add(Math.max((int) (0.25 * range), 3));
        }

        localityDensity = 3;
        localityChance = 70;
    }

    public boolean referenceRange(List<Integer> range) {
        if (range.size() == processes) {
            referenceRange = range;
            return true;
        }
        return false;
    }

    public boolean localityDensity(int density) {
        if (density >= 2) {
            localityDensity = density;
            return true;
        }
        return false;
    }

    public boolean localityChance(int chance) {
        if (chance >= 0 && chance <= 100) {
            localityChance = chance;
            return true;
        }
        return false;
    }

    public boolean localityRange(List<Integer> range) {
        if (range.size() == processes) {
            localityRange = range;
            return true;
        }
        return false;
    }

    public List<Integer> create() {
        List<Integer> referencesList = new LinkedList<>();
        int min = lastProcessMax + 1;
        lastProcessMax = min + referenceRange.get(lastProcess);

        for (int i = 0; i < this.references; i++) {
            int max = lastProcessMax;
            if (i % (localityDensity * 2) >= localityDensity) {
                if (random(0, 101) <= localityChance) {
                    max = min + localityRange.get(lastProcess);
                } else {
                    continue;
                }
            }
            referencesList.add(random(min, max));
        }
        lastProcess++;

        return referencesList;
    }

    private int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public int getProcessesAmount() {
        return processes;
    }

    public int getReferenceRange(int id) {
        return referenceRange.get(id);
    }

    public int getReferenceRange() {
        return referenceRange.stream().mapToInt(o -> o).sum();
    }
}
