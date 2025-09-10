package de.schmiereck.cellNet;

import java.util.*;

public class CellNetMain {
    public static boolean showExtraResults = false;

    public static void main(String[] args) {
        System.out.printf("CellNet V1.0.0%n");

        findTestRuleNumbers();
        //findBooleanRuleNumbers();
        //findCountRuleNumbers();

        //final Grid grid = GridService.createGrid(3, 4, 0);
        //
        //final int[] inputArr = { 0, 0 };
        //GridService.submitInput(grid, inputArr);
        //CellNetService.calcGrid(grid);
        //final int[] outputArr = GridService.retieveOutput(grid);
    }

    private static void findTestRuleNumbers() {
        final int maxSearchSize = 256;
        //final int maxSearchSize = 64;

        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR-Test",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                //new int[][] { { 0, 0 }, { 1, 0 }, { 1, 0 }, { 1, 0 } }));
                new int[][] { { 0, 0 }, { 0, 1 }, { 0, 1 }, { 0, 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr);
    }

    private static void findCountRuleNumbers() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 64;

        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, 0 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr);
    }

    private static void findBooleanRuleNumbers() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("AND", new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));
        opOutputArr.add(new OpOutput("OR",  new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));
        opOutputArr.add(new OpOutput("NAND",new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));
        opOutputArr.add(new OpOutput("NOR", new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));
        opOutputArr.add(new OpOutput("XOR", new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 0 }, { 1 }, { 1 }, { 0 } }));
        opOutputArr.add(new OpOutput("XNOR",new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr);
    }

    private static void findUniversalRuleNr(int maxSearchSize, List<OpOutput> opOutputArr) {
        for (int sizeX = 2; sizeX <= maxSearchSize; sizeX++) {
            for (int sizeY = 2; sizeY <= maxSearchSize; sizeY++) {
                final Integer universalRuleNr = findRuleNumbers(opOutputArr, sizeX, sizeY);
                if (Objects.nonNull(universalRuleNr)) {
                    System.out.printf("Universelle RuleNr (für alle Operationen gültig): %d%n", universalRuleNr);
                    break;
                } else {
                    System.out.println("Keine universelle RuleNr gefunden, die für alle Operationen gültig ist.");
                }
            }
        }
    }

    record OpOutput(String opName, int[][] inputArrArr, int[][] expectedOutputArrArr) {}

    private static Integer findRuleNumbers(final List<OpOutput> opOutputArr, final int sizeX, final int sizeY) {
        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("size: %d, %d%n", sizeX, sizeY);

        final List<Integer>[] matchingRuleListArr = new ArrayList[opOutputArr.size()];

        for (int pos = 0; pos < opOutputArr.size(); pos++) {
            final OpOutput opOutput = opOutputArr.get(pos);

            final String opName = opOutput.opName();
            final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
            // Eingabekombinationen
            final int[][] inputArrArr = opOutput.inputArrArr;

            matchingRuleListArr[pos] = new ArrayList<>();

            for (int ruleNr = 0; ruleNr <= 255; ruleNr++) {
                boolean allInputsMatch = true;
                inputArrArrPosLoop:
                for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length; inputArrArrPos++) {
                    final int[] inputArr = inputArrArr[inputArrArrPos];

                    final Grid grid = GridService.createGrid(sizeX, sizeY, ruleNr);

                    GridService.submitInput(grid, inputArr);
                    CellNetService.calcGrid(grid);
                    final int[] outputArr = GridService.retieveOutput(grid);

                    final int[] expectedOutputArr = expectedOutputArrArr[inputArrArrPos];
                    for (int outputArrPos = 0; outputArrPos < expectedOutputArr.length; outputArrPos++) {
                        if (outputArr[outputArrPos] != expectedOutputArr[outputArrPos]) {
                            allInputsMatch = false;
                            break inputArrArrPosLoop;
                        }
                    }
                }
                if (allInputsMatch) {
                    matchingRuleListArr[pos].add(ruleNr);
                }
            }
            System.out.printf("%s: %s\n", opName, matchingRuleListArr[pos]);
        }

        if (showExtraResults) {
            System.out.printf("---------------------------------------------------------%n");
            for (int pos = 0; pos < opOutputArr.size(); pos++) {
                final OpOutput opOutput = opOutputArr.get(pos);

                final String opName = opOutput.opName();
                final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
                final int[][] inputArrArr = opOutput.inputArrArr;

                System.out.printf("%s: %s\n", opName, matchingRuleListArr[pos]);
                if (matchingRuleListArr[pos].size() > 0) {
                    printGridForOperation(opName, matchingRuleListArr[pos].getFirst(), inputArrArr, expectedOutputArrArr, sizeX, sizeY);
                }
            }
        }
        Integer universalRuleNr = null;
        //for (int pos = 0; pos < matchingRuleListArr.length; pos++) {
        //    final List<Integer> matchingRuleList = matchingRuleListArr[pos];
        //}
        if (matchingRuleListArr.length > 0) {
            Set<Integer> intersection = new HashSet<>(matchingRuleListArr[0]);
            for (int i = 1; i < matchingRuleListArr.length; i++) {
                intersection.retainAll(matchingRuleListArr[i]);
            }
            if (!intersection.isEmpty()) {
                universalRuleNr = intersection.iterator().next();
            }
        }
        return universalRuleNr;
    }

    private static void printGridForOperation(String opName, int ruleNr, int[][] inputArrArr, int[][] expectedOutputArrArr, int sizeX, int sizeY) {
        System.out.printf("Operation: %s, Regel: %d\n", opName, ruleNr);
        for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length; inputArrArrPos++) {
            final int[] inputArr = inputArrArr[inputArrArrPos];
            final int[] expectedOutputArr = expectedOutputArrArr[inputArrArrPos];
            final Grid grid = GridService.createGrid(sizeX, sizeY, ruleNr);
            GridService.submitInput(grid, inputArr);
            CellNetService.calcGrid(grid);
            final int[] outputArr = GridService.retieveOutput(grid);
            System.out.printf("Eingabe: %s, Erwartete Ausgabe: %s, Tatsächliche Ausgabe: %s\n", Arrays.toString(inputArr), Arrays.toString(expectedOutputArr), Arrays.toString(outputArr));
            printGrid(grid);
        }
        System.out.println();
    }

    private static void printGrid(Grid grid) {
        for (int y = 0; y < grid.sizeY; y++) {
            for (int x = 0; x < grid.sizeX; x++) {
                System.out.print(grid.cellArr[y][x].value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}