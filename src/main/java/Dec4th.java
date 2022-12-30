package adventofcode;

import java.util.List;

public class Dec4th {

    public long solvePt1(String file) {
        return numberOfPairsFullyContainingOther(List.of(file.split("\\r\\n")));
    }

    public long solvePt2(String file) {
        return numberOfOverlappingPairs(List.of(file.split("\\r\\n")));
    }

    long numberOfPairsFullyContainingOther(List<String> lines) {
        return lines.stream()
                .map(ElfPair::new)
                .map(this::pairFullyContainsOther)
                .filter(x -> x)
                .count();
    }

    long numberOfOverlappingPairs(List<String> lines) {
        return lines.stream()
                .map(ElfPair::new)
                .map(this::overlapExistsEitherWay)
                .filter(x -> x)
                .count();
    }

    boolean pairFullyContainsOther(ElfPair elfPair) {
        return firstContainsSecond(elfPair.pairOne, elfPair.pairTwo) || firstContainsSecond(elfPair.pairTwo, elfPair.pairOne);
    }

    boolean overlapExistsEitherWay(ElfPair elfPair) {
        return firstOverlapsSecond(elfPair.pairOne, elfPair.pairTwo) || firstOverlapsSecond(elfPair.pairTwo, elfPair.pairOne);
    }

    private boolean firstContainsSecond(List<Integer> pairOne, List<Integer> pairTwo) {
        return pairOne.get(0) <= pairTwo.get(0) && pairOne.get(1) >= pairTwo.get(1);
    }

    private boolean firstOverlapsSecond(List<Integer> pairOne, List<Integer> pairTwo) {
        return pairOne.get(0) >= pairTwo.get(0) && pairOne.get(0) <= pairTwo.get(1);
    }

    public static class ElfPair {
        public final List<Integer> pairOne;
        public final List<Integer> pairTwo;

        public ElfPair(String sectionStr) {
            var pair = sectionStr.split(",");
            var xa = pair[0].split("-");
            var xb = pair[1].split("-");
            pairOne = List.of(Integer.parseInt(xa[0]), Integer.parseInt(xa[1]));
            pairTwo = List.of(Integer.parseInt(xb[0]), Integer.parseInt(xb[1]));
        }
    }
}

