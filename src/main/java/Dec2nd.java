package adventofcode;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class Dec2nd {

    final Map<Character, Integer> selectedShapeWorth = Map.of(
            'X', 1,
            'Y', 2,
            'Z', 3
    );
    final Map<String, Result> resultMap = Map.of(
            //X = ROCK, Y = PAPER, Z = SCISSORS
            //A = ROCK
            "A X", Result.DRAW,
            "A Y", Result.WIN,
            "A Z", Result.LOSS,
            //B = PAPER
            "B X", Result.LOSS,
            "B Y", Result.DRAW,
            "B Z", Result.WIN,
            //C = SCISSORS
            "C X", Result.WIN,
            "C Y", Result.LOSS,
            "C Z", Result.DRAW
    );

    final Map<Character, Result> charToWantedResultMap = Map.of(
            'X', Result.LOSS,
            'Y', Result.DRAW,
            'Z', Result.WIN
    );

    final Map<Result, Integer> resultWorth = Map.of(
            Result.WIN, 6,
            Result.DRAW, 3,
            Result.LOSS, 0
    );

    enum Result {
        WIN,
        DRAW,
        LOSS
    }

    private int getTotalScore(String input, Function<String, Integer> lineFunction) {
        return Arrays.stream(input.split("\\r\\n"))
                .map(lineFunction)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getTotalScorePt1(String input) {
        return getTotalScore(input, this::getLineWorthPt1);
    }

    public int getTotalScorePt2(String input) {
        return getTotalScore(input, this::getLineWorthPt2);
    }

    private int getLineWorthPt1(String line) {
        char selectedShape = line.charAt(2);
        return selectedShapeWorth.get(selectedShape) + resultWorth.get(resultMap.get(line));
    }

    private int getLineWorthPt2(String line) {
        var wantedResult = charToWantedResultMap.get(line.charAt(2));
        var selectedShape = selectShapeForResult(wantedResult, line.charAt(0));
        return resultWorth.get(wantedResult) + selectedShapeWorth.get(selectedShape);
    }


    private Character selectShapeForResult(Result result, Character opposingShape) {
        return resultMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(opposingShape.toString()))
                .filter(entry -> entry.getValue().equals(result))
                .findFirst().orElseThrow().getKey().charAt(2);
    }


}
