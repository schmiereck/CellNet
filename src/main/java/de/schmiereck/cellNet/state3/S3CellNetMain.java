package de.schmiereck.cellNet.state3;

import de.schmiereck.cellNet.state2.Row;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class S3CellNetMain {

    public static boolean showExtraResults = false;
    public static boolean ShowFoundGridNr = false;

    public static void main(String[] args) {
        System.out.printf("CellNet V1.0.0%n");

        //findTestRuleNumbersI2O1(); // Works.
        //findTestRuleNumbersI2O2(); // Find nothing.
        //findTestRuleNumbersI2O2Deep(); // Works.

        //findBooleanAndRuleNumbersI2O1(); // AND: size: 2, 2 [37, 39, 45, 47, 53, 55, 61, 63, 101, 103, 109, 111, 117, 119, 125, 127, 128, 130, 136, 138, 144, 146, 152, 154, 192, 194, 200, 202, 208, 210, 216, 218]
        //findBooleanOrRuleNumbersI2O1(); // OR: size: 2, 2 [1, 3, 9, 11, 17, 19, 25, 27, 65, 67, 73, 75, 81, 83, 89, 91, 164, 166, 172, 174, 180, 182, 188, 190, 228, 230, 236, 238, 244, 246, 252, 254]
        //findBooleanNandRuleNumbersI2O1(); // NAND: size: 2, 3 [37, 39, 45, 47, 53, 55, 61, 63, 101, 103, 109, 111, 117, 119, 125, 127]
        //findBooleanNorRuleNumbersI2O1(); // NOR: size: 2, 3 [1, 3, 9, 11, 17, 19, 25, 27, 65, 67, 73, 75, 81, 83, 89, 91]
        //findBooleanXorRuleNumbersI2O1(); // XOR: size: 3, 2 [9, 33, 41, 60, 61, 65, 105, 110, 111, 122, 123, 124, 125, 150, 188, 190]
        //findBooleanXnorRuleNumbersI2O1(); // XNOR: size: 3, 2 [131, 135, 147, 149, 195, 203, 227, 235]
        //findBooleanRuleNumbersI2O1(); // Works. No universal Solution.
        //findSimpleBooleanRuleNumbersI2O1(); // Works. No universal Solution.

        findBooleanAndRuleNumbersI2O1Deep(); //
        findBooleanOrRuleNumbersI2O1Deep(); //
        findBooleanNandRuleNumbersI2O1(); //
        findBooleanNorRuleNumbersI2O1(); //
        findBooleanXorRuleNumbersI2O1(); //
        findBooleanXnorRuleNumbersI2O1(); //
        //findBooleanRuleNumbersI2O1Deep(); //

        //findCountRuleNumbersI2O2Deep(); // Fing GridNr: 11044
        //findCountRuleNumbersI3O3Deep(); // Runs hours...
    }

    private static void findTestRuleNumbersI2O2Deep() {
        final int maxSearchSize = 256;
        //final int maxSearchSize = 64;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR-Test",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 0 }, { 0, 1 }, { 0, 1 }, { 0, 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findTestRuleNumbersI2O1() {
        final int maxSearchSize = 256;
        //final int maxSearchSize = 64;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR-Test",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findTestRuleNumbersI2O2() {
        final int maxSearchSize = 256;
        //final int maxSearchSize = 64;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR-Test",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 0 }, { 0, 1 }, { 0, 1 }, { 0, 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findCountRuleNumbersI2O2Deep() {
        //final int maxSearchSize = 256;
        //final int maxSearchSize = 64;
        final int maxSearchSize = 4;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT-NEXT",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findCountRuleNumbersI3O3Deep() {
        //final int maxSearchSize = 256;
        //final int maxSearchSize = 64;
        final int maxSearchSize = 4;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT-NEXT",
                new int[][] { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 },    { 1, 0, 0 }, { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 } },
                new int[][] { { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 },    { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 3, 1);
    }

    private static void findBooleanRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("000-AND",
                new int[][] { { 0, 0, 0,   0, 0 }, { 0, 0, 0,   0, 1 }, { 0, 0, 0,   1, 0 }, { 0, 0, 0,   1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));
        opOutputArr.add(new OpOutput("001-OR",
                new int[][] { { 0, 0, 1,   0, 0 }, { 0, 0, 1,   0, 1 }, { 0, 0, 1,   1, 0 }, { 0, 0, 1,   1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));
        opOutputArr.add(new OpOutput("010-NAND",
                new int[][] { { 0, 1, 0,   0, 0 }, { 0, 1, 0,   0, 1 }, { 0, 1, 0,   1, 0 }, { 0, 1, 0,   1, 1 } },
                new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));
        opOutputArr.add(new OpOutput("011-NOR",
                new int[][] { { 0, 1, 1,   0, 0 }, { 0, 1, 1,   0, 1 }, { 0, 1, 1,   1, 0 }, { 0, 1, 1,   1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));
        opOutputArr.add(new OpOutput("100-XOR",
                new int[][] { { 1, 0, 0,   0, 0 }, { 1, 0, 0,   0, 1 }, { 1, 0, 0,   1, 0 }, { 1, 0, 0,   1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 0 } }));
        opOutputArr.add(new OpOutput("101-XNOR"
                ,new int[][] { { 1, 0, 1,   0, 0 }, { 1, 0, 1,   0, 1 }, { 1, 0, 1,   1, 0 }, { 1, 0, 1,   1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 3 + 2, 2);
    }

    private static void findBooleanAndRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("AND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanOrRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanNandRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NAND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanNorRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanXorRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 0 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanXnorRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XNOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findSimpleBooleanRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("00-AND",
                new int[][] { { 0, 0,   0, 0 }, { 0, 0,   0, 1 }, { 0, 0,   1, 0 }, { 0, 0,   1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));
        opOutputArr.add(new OpOutput("01-OR",
                new int[][] { { 0, 1,   0, 0 }, { 0, 1,   0, 1 }, { 0, 1,   1, 0 }, { 0, 1,   1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));
        opOutputArr.add(new OpOutput("10-NAND",
                new int[][] { { 1, 0,   0, 0 }, { 1, 0,   0, 1 }, { 1, 0,   1, 0 }, { 1, 0,   1, 1 } },
                new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));
        opOutputArr.add(new OpOutput("11-NOR",
                new int[][] { { 1, 1,   0, 0 }, { 1, 1,   0, 1 }, { 1, 1,   1, 0 }, { 1, 1,   1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2 + 2, 2);
    }

    private static void findBooleanAndRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("AND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanOrRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanNandRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NAND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanNorRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanXorRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanXnorRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XNOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("000-AND",
                new int[][] { { 0, 0, 0,   0, 0 }, { 0, 0, 0,   0, 1 }, { 0, 0, 0,   1, 0 }, { 0, 0, 0,   1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));
        opOutputArr.add(new OpOutput("001-OR",
                new int[][] { { 0, 0, 1,   0, 0 }, { 0, 0, 1,   0, 1 }, { 0, 0, 1,   1, 0 }, { 0, 0, 1,   1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));
        opOutputArr.add(new OpOutput("010-NAND",
                new int[][] { { 0, 1, 0,   0, 0 }, { 0, 1, 0,   0, 1 }, { 0, 1, 0,   1, 0 }, { 0, 1, 0,   1, 1 } },
                new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));
        opOutputArr.add(new OpOutput("011-NOR",
                new int[][] { { 0, 1, 1,   0, 0 }, { 0, 1, 1,   0, 1 }, { 0, 1, 1,   1, 0 }, { 0, 1, 1,   1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));
        opOutputArr.add(new OpOutput("100-XOR",
                new int[][] { { 1, 0, 0,   0, 0 }, { 1, 0, 0,   0, 1 }, { 1, 0, 0,   1, 0 }, { 1, 0, 0,   1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 0 } }));
        opOutputArr.add(new OpOutput("101-XNOR"
                ,new int[][] { { 1, 0, 1,   0, 0 }, { 1, 0, 1,   0, 1 }, { 1, 0, 1,   1, 0 }, { 1, 0, 1,   1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 3 + 2, 1);
    }

    private static void findUniversalRuleNr(int maxSearchSize, List<OpOutput> opOutputArr,
                                            final int startSizeX, final int startSizeY) {
        outerLoop:
        for (int sizeX = startSizeX; sizeX <= maxSearchSize; sizeX++) {
            for (int sizeY = startSizeY; sizeY <= maxSearchSize; sizeY++) {
                final List<Integer>[] matchingRuleNrListArr = findRuleNrList(opOutputArr, sizeX, sizeY);
                final Integer universalRuleNr = findUniversalMatchingRuleNr(matchingRuleNrListArr);
                if (Objects.nonNull(universalRuleNr)) {
                    System.out.printf("Universelle RuleNr (für alle Operationen gültig): %d%n", universalRuleNr);
                    // Create and show the universal grid
                    final Grid universalGrid = GridService.createGrid(sizeX, sizeY, universalRuleNr);
                    printGridRuleNr(universalGrid);
                    break outerLoop;
                } else {
                    System.out.println("Keine universelle RuleNr gefunden, die für alle Operationen gültig ist.");
                }
            }
        }
    }

    private static void findUniversalRuleNrDeep(int maxSearchSize, List<OpOutput> opOutputArr,
                                                final int startSizeX, final int startSizeY) {
        outerLoop:
        for (int sizeX = startSizeX; sizeX <= maxSearchSize; sizeX++) {
            for (int sizeY = startSizeY; sizeY <= maxSearchSize; sizeY++) {
                final List<BigInteger>[] matchingGridNrListArr = findGridNrListDeep(opOutputArr, sizeX, sizeY);
                final BigInteger universalGridNr = findUniversalMatchingGridNrDepp(matchingGridNrListArr);
                if (Objects.nonNull(universalGridNr)) {
                    System.out.printf("Universelle GridNr (für alle Operationen gültig): %d%n", universalGridNr);
                    // Create and show the universal grid
                    final Grid universalGrid = GridService.createGridForCombination(sizeX, sizeY, universalGridNr);
                    printGridRuleNr(universalGrid);
                    break outerLoop;
                } else {
                    System.out.println("Keine universelle GridNr gefunden, die für alle Operationen gültig ist.");
                }
            }
        }
    }

    record OpOutput(String opName, int[][] inputArrArr, int[][] expectedOutputArrArr) {}

    private static  List<Integer>[] findRuleNrList(final List<OpOutput> opOutputArr, final int sizeX, final int sizeY) {
        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("size: %d, %d%n", sizeX, sizeY);

        @SuppressWarnings("unchecked")
        final List<Integer>[] matchingRuleListArr = new ArrayList[opOutputArr.size()];

        // Parallele Verarbeitung jeder Operation
        IntStream.range(0, opOutputArr.size()).parallel().forEach(pos -> {
            final OpOutput opOutput = opOutputArr.get(pos);
            final String opName = opOutput.opName();
            final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
            final int[][] inputArrArr = opOutput.inputArrArr;

            final List<Integer> matchingRuleNrList = new ArrayList<>();
            // Prüfe alle 256 Regeln parallel
            IntStream.rangeClosed(0, GridService.MAX_RULE_NR).parallel().forEach(ruleNr -> {
                boolean allInputsMatch = true;
                for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length && allInputsMatch; inputArrArrPos++) {
                    final int[] inputArr = inputArrArr[inputArrArrPos];
                    final Grid grid = GridService.createGrid(sizeX, sizeY, ruleNr);
                    GridService.submitInput(grid, inputArr);
                    CellNetService.calcGrid(grid);
                    final int[] outputArr = GridService.retieveOutput(grid);
                    final int[] expectedOutputArr = expectedOutputArrArr[inputArrArrPos];
                    for (int outputArrPos = 0; outputArrPos < expectedOutputArr.length; outputArrPos++) {
                        if (outputArr[outputArrPos] != expectedOutputArr[outputArrPos]) {
                            allInputsMatch = false;
                            break;
                        }
                    }
                }
                if (allInputsMatch) {
                    synchronized (matchingRuleNrList) { // Sammelliste schützen
                        matchingRuleNrList.add(ruleNr);
                    }
                }
            });
            matchingRuleNrList.sort(Comparator.comparingInt(ruleNr -> ruleNr));
            matchingRuleListArr[pos] = matchingRuleNrList;
            System.out.printf("%s: %s\n", opName, matchingRuleNrList);
        });

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
        return matchingRuleListArr;
    }

    private static List<BigInteger>[] findGridNrListDeep(final List<OpOutput> opOutputArr, final int sizeX, final int sizeY) {
        System.out.printf("---------------------------------------------------------%n");
        // sizeY = Anzahl Regel-Zeilen (ohne Input-Layer)
        final int totalSizeY = sizeY + 1;
        final BigInteger maxGridNr = GridService.calcMaxGridNr(sizeX, sizeY);
        System.out.printf("size: %d, %d+1 (maxGridNr: %,d)%n", sizeX, sizeY, maxGridNr);

        final BigInteger progressDivisor;
        final BigInteger tmpProgressDivisor = maxGridNr.divide(BigInteger.valueOf(80L));
        if (tmpProgressDivisor.equals(BigInteger.ZERO)) {
            progressDivisor = BigInteger.ONE;
        } else {
            progressDivisor = tmpProgressDivisor;
        }

        @SuppressWarnings("unchecked")
        final List<BigInteger>[] matchingGridNrListArr = new List[opOutputArr.size()];

        System.out.println("|0%----------------|25%----------------|50%----------------|75-----------------|%100%");

        final AtomicLong progressCounter = new AtomicLong(0);
        final int numThreads = Math.max(2, Runtime.getRuntime().availableProcessors());
        final BigInteger blockSize = BigInteger.valueOf(100_000L);

        final ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(numThreads);

        IntStream.range(0, opOutputArr.size()).forEach(pos -> {
            final OpOutput opOutput = opOutputArr.get(pos);
            final String opName = opOutput.opName();
            final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
            final int[][] inputArrArr = opOutput.inputArrArr;
            final List<BigInteger> localMatcheGridNrList = java.util.Collections.synchronizedList(new ArrayList<>());
            final List<Future<?>> futures = new ArrayList<>();
            final Object blockLock = new Object();
            final BigInteger[] nextBlockStart = new BigInteger[] { BigInteger.ZERO };

            for (int t = 0; t < numThreads; t++) {
                futures.add(executor.submit(() -> {
                    while (true) {
                        final BigInteger start, end;
                        synchronized (blockLock) {
                            if (nextBlockStart[0].compareTo(maxGridNr) >= 0) {
                                break;
                            }
                            start = nextBlockStart[0];
                            end = start.add(blockSize).min(maxGridNr);
                            nextBlockStart[0] = end;
                        }
                        for (BigInteger gridNr = start; gridNr.compareTo(end) < 0; gridNr = gridNr.add(BigInteger.ONE)) {
                            final Grid grid = GridService.createGridForCombination(sizeX, sizeY, gridNr);
                            boolean allInputsMatch = true;
                            inputArrArrPosLoop:
                            for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length; inputArrArrPos++) {
                                final int[] inputArr = inputArrArr[inputArrArrPos];
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
                                localMatcheGridNrList.add(gridNr);
                                if (ShowFoundGridNr) {
                                    synchronized (System.out) {
                                        System.out.println(gridNr); // Gefundene Regel-Kombination ausgeben
                                    }
                                }
                            }
                            // Fortschrittsanzeige
                            long current = progressCounter.incrementAndGet();
                            if (progressDivisor.compareTo(BigInteger.valueOf(1)) > 0 && (current % progressDivisor.longValue() == 0)) {
                                synchronized (System.out) {
                                    System.out.print("*");
                                }
                            }
                        }
                    }
                }));
            }
            // Auf alle Blöcke warten
            for (Future<?> f : futures) {
                try { f.get(); } catch (Exception e) { throw new RuntimeException(e); }
            }
            System.out.println();
            matchingGridNrListArr[pos] = localMatcheGridNrList;
            System.out.printf("%s: %s\n", opName, localMatcheGridNrList);
        });
        executor.shutdown();

        return matchingGridNrListArr;
    }

    private static Integer findUniversalMatchingRuleNr(List<Integer>[] matchingRuleNrListArr) {
        Integer universalRuleNr = null;
        if (matchingRuleNrListArr.length > 0) {
            Set<Integer> intersection = new HashSet<>(matchingRuleNrListArr[0]);
            for (int i = 1; i < matchingRuleNrListArr.length; i++) {
                intersection.retainAll(matchingRuleNrListArr[i]);
            }
            if (!intersection.isEmpty()) {
                universalRuleNr = intersection.iterator().next();
            }
        }
        return universalRuleNr;
    }

    private static BigInteger findUniversalMatchingGridNrDepp(List<BigInteger>[] matchingGridNrListArr) {
        BigInteger universalGridNr = null;
        if (matchingGridNrListArr.length > 0) {
            final Set<BigInteger> intersection = new HashSet<>(matchingGridNrListArr[0]);
            for (int matchingGridNrListArrPos = 1; matchingGridNrListArrPos < matchingGridNrListArr.length; matchingGridNrListArrPos++) {
                intersection.retainAll(matchingGridNrListArr[matchingGridNrListArrPos]);
            }
            if (!intersection.isEmpty()) {
                universalGridNr = intersection.iterator().next();
            }
        }
        return universalGridNr;
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
            printGridValueAndGridNr(grid);
        }
        System.out.println();
    }

    private static void printGridValue(final Grid grid) {
        for (int y = 0; y < grid.sizeY; y++) {
            for (int x = 0; x < grid.sizeX; x++) {
                System.out.print(grid.cellArrArr[y][x].value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printGridValueAndGridNr(final Grid grid) {
        for (int y = 0; y < grid.sizeY; y++) {
            for (int x = 0; x < grid.sizeX; x++) {
                System.out.print(grid.cellArrArr[y][x].value + " ");
            }
            System.out.print(" | ");
            for (int x = 0; x < grid.sizeX; x++) {
                System.out.printf("%02d ", grid.cellArrArr[y][x].ruleNr);
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printGridRuleNr(final Grid grid) {
        for (int y = 0; y < grid.sizeY; y++) {
            for (int x = 0; x < grid.sizeX; x++) {
                System.out.printf("%02d ", grid.cellArrArr[y][x].ruleNr);
            }
            System.out.println();
        }
        System.out.println();
    }
}

