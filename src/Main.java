import Algorithms.Equal;
import Algorithms.PFF;
import Algorithms.Proportional;
import Algorithms.WSS;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PageReferenceBuilder prb = new PageReferenceBuilder(100, 5);
        prb.localityDensity(3);
        prb.localityChance(100);
        prb.referenceRange(List.of(10, 4, 6, 20, 70));
        prb.localityRange(List.of(3, 2, 2, 3, 5));

        Simulation s = new Simulation(prb, 50);
        s.start(new Equal());
        s.start(new Proportional());
        s.start(new PFF(5, 0.3, 0.4));
        s.start(new WSS(5));
    }
}
