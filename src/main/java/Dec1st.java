package adventofcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Dec1st {


    public Long getTotalOfTopNElves(int n) throws IOException {
        var totals = getAllTotals();
        var floor = totals.last();
        Long result = 0L;

        for (int i = 0; i < n; i++) {
            floor = totals.floor(floor);
            result += floor;
            totals.remove(floor);
        }
        return result;
    }

    private TreeSet<Long> getAllTotals() throws IOException {
        String str = Files.readString(new File("C:/Code/testhest/src/adventofcode/Dec1stInput").toPath());

        return Arrays.stream(str.split("\\r\\n\\r\\n"))
                .map(lines -> Arrays.stream(lines.split("\\r\\n"))
                        .mapToLong(Long::parseLong)
                        .sum())
                .collect(Collectors.toCollection(TreeSet::new));
    }

}
