package adventofcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Dec3rd {


    public int processFilePt1(String file) {
        return Arrays.stream(file.split("\\r\\n"))
                .map(this::processRucksack)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int processFilePt2(String file) {
        var linesArray = file.split("\\r\\n");
        Stack<String> stack = new Stack<>();
        Arrays.stream(linesArray).forEach(stack::push);
        List<String> currentList = new ArrayList<>();
        int result = 0;
        var i = 1;
        do {
            currentList.add(stack.pop());
            if (i % 3 == 0) {
                result += processRucksacks(currentList);
                currentList.clear();
            }
            i++;
        } while (!stack.isEmpty());
        return result;
    }

    private int processRucksack(String line) {
        var rucksack = new Rucksack(line);
        return rucksack.findItemPresentInBothCompartments();
    }

    private int processRucksacks(List<String> lines) {
        Set<Integer> itemsInAllRucksacks = new HashSet<>();

        Set<Set<Integer>> allRucksacks = lines.stream()
                .map(Rucksack::new)
                .map(Rucksack::getAllCompartmentsMerged)
                .map(Map::keySet)
                .collect(Collectors.toSet());

        allRucksacks.forEach(rucksack -> {
            rucksack.forEach(element -> {
                AtomicBoolean elementExistsInOtherRucksacks = new AtomicBoolean(true);
                allRucksacks.forEach(otherRucksack -> {
                    if (otherRucksack != rucksack) {
                        if (!otherRucksack.contains(element)) {
                            elementExistsInOtherRucksacks.set(false);
                        }
                    }
                });
                if (elementExistsInOtherRucksacks.get()) {
                    itemsInAllRucksacks.add(element);
                }
            });
        });


        return Rucksack.convertToPriorityValue(itemsInAllRucksacks.stream().findAny().orElse(38));
    }


    static class Rucksack {
        Map<Integer, Integer> compartmentOne;
        Map<Integer, Integer> compartmentTwo;

        public Rucksack(String rucksack) {
            var length = rucksack.length();
            this.compartmentOne = new HashMap<>();
            this.compartmentTwo = new HashMap<>();
            populateCompartmentMap(rucksack.substring(0, length / 2), compartmentOne);
            populateCompartmentMap(rucksack.substring(length / 2, length), compartmentTwo);
        }

        public static int convertToPriorityValue(int character) {
            if (character > 96) {
                return character - 96;
            } else {
                return character - 38;
            }
        }

        private void populateCompartmentMap(String compartment, Map<Integer, Integer> map) {
            compartment.chars().forEach(c -> {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            });
        }

        public int findItemPresentInBothCompartments() {
            return compartmentOne.keySet().stream()
                    .filter(item -> compartmentTwo.containsKey(item))
                    .map(Rucksack::convertToPriorityValue)
                    .findFirst().orElse(0);
        }

        public Map<Integer, Integer> getAllCompartmentsMerged() {
            var compartmentOneCopy = new HashMap<>(compartmentOne);
            compartmentTwo.keySet()
                    .forEach(key -> {
                        if (compartmentOneCopy.containsKey(key)) {
                            compartmentOneCopy.put(key, compartmentOneCopy.get(key) + compartmentTwo.get(key));
                        } else {
                            compartmentOneCopy.put(key, compartmentTwo.get(key));
                        }
                    });
            return compartmentOneCopy;
        }
    }
}
