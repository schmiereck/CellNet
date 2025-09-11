package de.schmiereck.cellNet.state2;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class S2CellNetMain {
    public static boolean showExtraResults = false;
    public static boolean ShowFoundGridNr = false;

    public static void main(String[] args) {
        System.out.printf("CellNet V1.0.0%n");

        //findTestRuleNumbersI2O1(); // Works.
        //findTestRuleNumbersI2O2(); // Find nothing.
        //findTestRuleNumbersI2O2Deep(); // Works.

        //findBooleanRuleNumbersI2O1(); // Works.
        //findBooleanRuleNumbersI2O2(); // Works.
        findBooleanRuleNumbersI2O1Deep(); // Works.

        //findCountRuleNumbersI2O2Deep(); // Find nothing.
        //findCountRuleNumbersI3O3Deep(); // Find nothing.

        //final Grid grid = GridService.createGrid(3, 4, 0);
        //
        //final int[] inputArr = { 0, 0 };
        //GridService.submitInput(grid, inputArr);
        //CellNetService.calcGrid(grid);
        //final int[] outputArr = GridService.retieveOutput(grid);
    }

    private static void findTestRuleNumbersI2O2Deep() {
        final int maxSearchSize = 256;
        //final int maxSearchSize = 64;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR-Test",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 0 }, { 0, 1 }, { 0, 1 }, { 0, 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
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
        final int maxSearchSize = 64;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findCountRuleNumbersI3O3Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 64;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT",
                new int[][] { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 },    { 1, 0, 0 }, { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 } },
                new int[][] { { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 },    { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 3, 2);
    }

    private static void findBooleanRuleNumbersI2O1() {
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

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanRuleNumbersI2O2() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("AND", new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 1, 0 } }));
        opOutputArr.add(new OpOutput("OR",  new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 0, 0 }, { 1, 0 }, { 1, 0 }, { 1, 0 } }));
        opOutputArr.add(new OpOutput("NAND",new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 1, 0 }, { 1, 0 }, { 1, 0 }, { 0, 0 } }));
        opOutputArr.add(new OpOutput("NOR", new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 1, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }));
        opOutputArr.add(new OpOutput("XOR", new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 0, 0 }, { 1, 0 }, { 1, 0 }, { 0, 0 } }));
        opOutputArr.add(new OpOutput("XNOR",new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, new int[][] { { 1, 0 }, { 0, 0 }, { 0, 0 }, { 1, 0 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanRuleNumbersI2O1Deep() {
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

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findUniversalRuleNr(int maxSearchSize, List<OpOutput> opOutputArr,
                                            final int startSizeX, final int startSizeY) {
        for (int sizeX = startSizeX; sizeX <= maxSearchSize; sizeX++) {
            for (int sizeY = startSizeY; sizeY <= maxSearchSize; sizeY++) {
                final List<Integer>[] matchingRuleNrListArr = findRuleNrList(opOutputArr, sizeX, sizeY);
                final Integer universalRuleNr = findUniversalMatchingRuleNr(matchingRuleNrListArr);
                if (Objects.nonNull(universalRuleNr)) {
                    System.out.printf("Universelle RuleNr (für alle Operationen gültig): %d%n", universalRuleNr);
                    break;
                } else {
                    System.out.println("Keine universelle RuleNr gefunden, die für alle Operationen gültig ist.");
                }
            }
        }
    }

    private static void findUniversalRuleNrDeep(int maxSearchSize, List<OpOutput> opOutputArr,
                                                final int startSizeX, final int startSizeY) {
        for (int sizeX = startSizeX; sizeX <= maxSearchSize; sizeX++) {
            for (int sizeY = startSizeY; sizeY <= maxSearchSize; sizeY++) {
                final List<BigInteger>[] matchingGridNrListArr = findGridNrListDeep(opOutputArr, sizeX, sizeY);
                final BigInteger universalGridNr = findUniversalMatchingGridNrDeep(matchingGridNrListArr);
                if (Objects.nonNull(universalGridNr)) {
                    System.out.printf("Universelle GridNr (für alle Operationen gültig): %d%n", universalGridNr);
                    break;
                } else {
                    System.out.println("Keine universelle GridNr gefunden, die für alle Operationen gültig ist.");
                }
            }
        }
    }

    record OpOutput(String opName, int[][] inputArrArr, int[][] expectedOutputArrArr) {}

    private static List<Integer>[] findRuleNrList(final List<OpOutput> opOutputArr, final int sizeX, final int sizeY) {
        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("size: %d, %d+1%n", sizeX, sizeY);

        @SuppressWarnings("unchecked")
        final List<Integer>[] matchingRuleListArr = new ArrayList[opOutputArr.size()];

        // Parallele Verarbeitung jeder Operation
        IntStream.range(0, opOutputArr.size()).parallel().forEach(pos -> {
            final OpOutput opOutput = opOutputArr.get(pos);
            final String opName = opOutput.opName();
            final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
            final int[][] inputArrArr = opOutput.inputArrArr;

            final List<Integer> matchingRules = new ArrayList<>();
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
                    synchronized (matchingRules) { // Sammelliste schützen
                        matchingRules.add(ruleNr);
                    }
                }
            });
            matchingRuleListArr[pos] = matchingRules;
            System.out.printf("%s: %s\n", opName, matchingRules);
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
        final BigInteger maxGridNr = BigInteger.valueOf(GridService.RULE_COUNT).pow(sizeX * sizeY);
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
        final int numThreads = Math.max(2, Runtime.getRuntime().availableProcessors() - 1);
        final BigInteger blockSize = BigInteger.valueOf(10_000L);

        final ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(numThreads);

        IntStream.range(0, opOutputArr.size()).forEach(pos -> {
            final OpOutput opOutput = opOutputArr.get(pos);
            final String opName = opOutput.opName();
            final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
            final int[][] inputArrArr = opOutput.inputArrArr;
            final List<BigInteger> localMatcheGridNrList = Collections.synchronizedList(new ArrayList<>());
            final List<Future<?>> futureList = new ArrayList<>();
            final Object blockLock = new Object();
            final BigInteger[] nextBlockStart = new BigInteger[] { BigInteger.ZERO };

            for (int t = 0; t < numThreads; t++) {
                futureList.add(executor.submit(() -> {
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
            for (final Future<?> future : futureList) {
                try
                {
                    future.get();
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println();
            matchingGridNrListArr[pos] = localMatcheGridNrList;
            System.out.printf("%s: %s\n", opName, localMatcheGridNrList.size());
        });
        executor.shutdown();

        return matchingGridNrListArr;
    }

    private static Integer findUniversalMatchingRuleNr(List<Integer>[] matchingRuleListArr) {
        Integer universalRuleNr = null;
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

    private static BigInteger findUniversalMatchingGridNrDeep(List<BigInteger>[] matchingGridNrListArr) {
        BigInteger universalGridNr = null;
        if (matchingGridNrListArr.length > 0) {
            // Listen nach Größe sortieren (kleinste zuerst)
            List<List<BigInteger>> matchingGridNrListList = Arrays.asList(matchingGridNrListArr);
            matchingGridNrListList.sort(Comparator.comparingInt(List::size));

            //final Set<BigInteger> intersectionSet = new HashSet<>(matchingGridNrListArr[0]);
            final Set<BigInteger> intersectionSet = new HashSet<>(matchingGridNrListList.get(0));

            //for (int matchingGridNrListArrPos = 1; matchingGridNrListArrPos < matchingGridNrListArr.length; matchingGridNrListArrPos++) {
            for (int matchingGridNrListArrPos = 1; matchingGridNrListArrPos < matchingGridNrListList.size(); matchingGridNrListArrPos++) {
                //intersectionSet.retainAll(matchingGridNrListArr[matchingGridNrListArrPos]);
                final Set<BigInteger> currentSet = new HashSet<>(matchingGridNrListList.get(matchingGridNrListArrPos));
                intersectionSet.retainAll(currentSet);
                if (intersectionSet.isEmpty()) {
                    break;
                }
            }
            if (!intersectionSet.isEmpty()) {
                universalGridNr = intersectionSet.iterator().next();
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
            printGrid(grid);
        }
        System.out.println();
    }

    private static void printGrid(Grid grid) {
        for (int y = 0; y < grid.sizeY; y++) {
            final Row row = grid.rowArr[y];
            for (int x = 0; x < row.sizeX; x++) {
                System.out.print(row.cellArr[x].value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

