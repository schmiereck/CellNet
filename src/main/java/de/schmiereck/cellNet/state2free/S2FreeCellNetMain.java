package de.schmiereck.cellNet.state2free;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class S2FreeCellNetMain {
    public static boolean showExtraResults = false;
    public static boolean ShowFoundGridNr = false;

    public static void main(String[] args) {
        System.out.printf("CellNet V1.0.0%n");

        //findTestRuleNumbersI2O1(); // Works.
        //findTestRuleNumbersI2O2(); // No universal Solution.
        //findTestRuleNumbersI2O2Deep(); // Works.

        //findBooleanAndRuleNumbersI2O1();      // AND:     size: 2, 2+1 [7, 8]
        //findBooleanOrRuleNumbersI2O1();       // OR:      size: 2, 2+1 [1, 14]
        //findBooleanNandRuleNumbersI2O1();     // NAND:    size: 2, 3+1 [7]
        //findBooleanNorRuleNumbersI2O1();      // NOR:     size: 2, 3+1 [1]
        //findBooleanXorRuleNumbersI2O1();      // XOR:     size: 3, 4+1 [6]
        //findBooleanXnorRuleNumbersI2O1();     // XNOR:    size: 3, 4+1 [9]

        //findBooleanAndGridOffNumbersI2O1Deep();  // AND:     size: [2, 1], 2+1 GridOffNr: 67601
        //findBooleanAndRuleNumbersI2O1Deep();  // AND:     size: [2], 1+1 GridNr:  8, size: [2, 2], 2+1 GridNr: 263
        //findBooleanOrRuleNumbersI2O1Deep();   // OR:      size: [2], 1+1 GridNr: 14 , size: [2, 2], 2+1 GridNr: 257
        //findBooleanOrGridOffNumbersI2O1Deep();   // OR:     size: [2, 1], 2+1 GridOffNr: 24577
        //findBooleanNandRuleNumbersI2O1Deep(); // NAND:    size: [2], 1+1 GridNr:  7, size: [2, 2], 2+1 GridNr: 264
        //findBooleanNandGridOffNumbersI2O1Deep(); // NAND:   size: [2, 1], 2+1 GridOffNr: 12288
        //findBooleanNorRuleNumbersI2O1Deep();  // NOR:     size: [2], 1+1 GridNr:  1, size: [2, 2], 2+1 GridNr: 270
        //findBooleanNorGridOffNumbersI2O1Deep();  // NOR:     size: [2, 1], 2+1 GridOffNr: 79899
        //findBooleanXorRuleNumbersI2O1Deep();  // XOR:     size: [2], 1+1 GridNr:  6, size: [2, 2], 2+1 GridNr: 20745
        //findBooleanXorGridOffNumbersI2O1Deep();  // XOR:    size: [2, 1], 2+1 GridOffNr: 10240
        //findBooleanXnorRuleNumbersI2O1Deep(); // XNOR:    size: [2], 1+1 GridNr:  9, size: [2, 2], 2+1 GridNr: 20742
        //findBooleanXnorGridOffNumbersI2O1Deep(); // XNOR:   size: [2, 1], 2+1 GridOffNr: 67627
        //findSimpleBooleanRuleNumbersI2O1(); // No universal Solution.
        //findSimpleBooleanRuleNumbersI2O1Deep(); // Runs years...
        //findSimpleBooleanGridOffNumbersI2O1Deep(); // Runs years...
        //findBooleanRuleNumbersI2O1(); // Works. No universal Solution.
        //findBooleanRuleNumbersI2O1xxxDeep(); // Runs years...
        //findBooleanRuleNumbersI2O2(); // No universal Solution.
        //findBooleanRuleNumbersI2O1Deep(); // Runs years...

        //findCountRuleNumbersI2O2(); // No universal Solution.
        //findCountRuleNumbersI2O2Deep(); // Find size: [2, 2], 2+1 GridNr: 14787
        //findCountGridOffNumbersI2O2Deep(); // size: [2], 1+1 GridOffNr: 177, size: [2, 2], 2+1 GridOffNr: 466951
        //findCountRuleNumbersI3O3Deep(); // Runs years...
        findCountGridOffNumbersI3O3Deep(1); // size: [3], 1+1 No universal Solution
        findCountGridOffNumbersI3O3Deep(2); // size: [3, 3], 2+1 Runs years...
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
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NUM-Test",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = false;

        findUniversalRuleOffNr(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findTestRuleNumbersI2O2() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 64;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR-Test",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 0 }, { 0, 1 }, { 0, 1 }, { 0, 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findCountRuleNumbersI2O2() {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT-NEXT",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, 0 } }));

        //final int[] rowSizeXArr = new int[] { 2, 3, 2 }; // Input-Layer und eine Regel-Zeile
        //final int[] rowSizeXArr = new int[] { 2, 3, 3, 2 }; // Input-Layer und eine Regel-Zeile
        //final int[] rowSizeXArr = new int[] { 2, 3, 3, 3, 2 }; // Input-Layer und eine Regel-Zeile
        final int[] rowSizeXArr = new int[] { 2, 3, 4, 3, 2 }; // Input-Layer und eine Regel-Zeile
        //final int[] rowSizeXArr = new int[] { 2, 3, 4, 5, 4, 3, 2 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = false;

        findUniversalRuleOffNr(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findCountRuleNumbersI2O2Deep() {
        //final int maxSearchSize = 256;
        //final int maxSearchSize = 64;
        final int maxSearchSize = 8;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT-NEXT",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findCountGridOffNumbersI2O2Deep() {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT-NEXT",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, 0 } }));

        final int[] rowSizeXArr = new int[] { 2 }; // Input-Layer und eine Regel-Zeile
        //final int[] rowSizeXArr = new int[] { 2, 2 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findCountRuleNumbersI3O3Deep() {
        //final int maxSearchSize = 256;
        //final int maxSearchSize = 64;
        final int maxSearchSize = 4;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT-NEXT",
                new int[][] { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 },    { 1, 0, 0 }, { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 } },
                new int[][] { { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 },    { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 3, 2);
    }

    private static void findCountGridOffNumbersI3O3Deep(final int sizeY) {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("COUNT-NEXT",
                new int[][] { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 },    { 1, 0, 0 }, { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 } },
                new int[][] { { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 },    { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }));

        final int[] rowSizeXArr =
                switch (sizeY) {
                    case 1 -> new int[] { 3 };
                    case 2 -> new int[] { 3, 3 };
                    default -> throw new IllegalStateException("Unexpected sizeY value: " + sizeY);
                };
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findBooleanAndRuleNumbersI2O1() {
        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("AND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final int sizeY = 1;
        final boolean noCommutative = true;

        findUniversalRuleOffNr(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findBooleanOrRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
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
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XNOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2, 2);
    }

    private static void findBooleanAndRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("AND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findBooleanAndGridOffNumbersI2O1Deep() {
        // Definition der booleschen Operationen und deren erwartete Outputs
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("AND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 0 }, { 0 }, { 1 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findBooleanOrRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findBooleanOrGridOffNumbersI2O1Deep() {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("OR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 1 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findBooleanNandRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NAND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findBooleanNandGridOffNumbersI2O1Deep() {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NAND",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 1 }, { 1 }, { 0 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findBooleanNorRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findBooleanNorGridOffNumbersI2O1Deep() {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("NOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 0 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findBooleanXorRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 0 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findBooleanXorGridOffNumbersI2O1Deep() {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 0 }, { 1 }, { 1 }, { 0 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
    }

    private static void findBooleanXnorRuleNumbersI2O1Deep() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 8;

        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XNOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2, 1);
    }

    private static void findBooleanXnorGridOffNumbersI2O1Deep() {
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("XNOR",
                new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                new int[][] { { 1 }, { 0 }, { 0 }, { 1 } }));

        final int[] rowSizeXArr = new int[] { 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
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

    private static void findBooleanRuleNumbersI2O1xxxDeep() {
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

        final int[] rowSizeXArr = new int[] { 3 + 2, 4, 3, 2, 1 };

        findUniversalRuleNrDeep(opOutputArr, rowSizeXArr);
    }

    private static void findBooleanRuleNumbersI2O2() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 16;

        // Definition der booleschen Operationen und deren erwartete Outputs
        // Programmable Logic Array (PLA) https://en.wikipedia.org/wiki/Programmable_logic_array
        final List<OpOutput> opOutputArr = new ArrayList<>();

        opOutputArr.add(new OpOutput("000-AND",
                new int[][] { { 0, 0, 0,   0, 0 }, { 0, 0, 0,   0, 1 }, { 0, 0, 0,   1, 0 }, { 0, 0, 0,   1, 1 } },
                new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 1, 0 } }));
        opOutputArr.add(new OpOutput("001-OR",
                new int[][] { { 0, 0, 1,   0, 0 }, { 0, 0, 1,   0, 1 }, { 0, 0, 1,   1, 0 }, { 0, 0, 1,   1, 1 } },
                new int[][] { { 0, 0 }, { 1, 0 }, { 1, 0 }, { 1, 0 } }));
        opOutputArr.add(new OpOutput("010-NAND",
                new int[][] { { 0, 1, 0,   0, 0 }, { 0, 1, 0,   0, 1 }, { 0, 1, 0,   1, 0 }, { 0, 1, 0,   1, 1 } },
                new int[][] { { 1, 0 }, { 1, 0 }, { 1, 0 }, { 0, 0 } }));
        opOutputArr.add(new OpOutput("011-NOR",
                new int[][] { { 0, 1, 1,   0, 0 }, { 0, 1, 1,   0, 1 }, { 0, 1, 1,   1, 0 }, { 0, 1, 1,   1, 1 } },
                new int[][] { { 1, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }));
        opOutputArr.add(new OpOutput("100-XOR",
                new int[][] { { 1, 0, 0,   0, 0 }, { 1, 0, 0,   0, 1 }, { 1, 0, 0,   1, 0 }, { 1, 0, 0,   1, 1 } },
                new int[][] { { 0, 0 }, { 1, 0 }, { 1, 0 }, { 0, 0 } }));
        opOutputArr.add(new OpOutput("101-XNOR"
                ,new int[][] { { 1, 0, 1,   0, 0 }, { 1, 0, 1,   0, 1 }, { 1, 0, 1,   1, 0 }, { 1, 0, 1,   1, 1 } },
                new int[][] { { 1, 0 }, { 0, 0 }, { 0, 0 }, { 1, 0 } }));

        findUniversalRuleNr(maxSearchSize, opOutputArr,  3 + 2, 2);
    }

    private static void findBooleanRuleNumbersI2O1Deep() {
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

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 3 + 2, 1);
    }

    private static void findSimpleBooleanRuleNumbersI2O1() {
        //final int maxSearchSize = 256;
        final int maxSearchSize = 128;
        //final int maxSearchSize = 64;

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

        findUniversalRuleNr(maxSearchSize, opOutputArr, 2 + 2, 1);
    }

    private static void findSimpleBooleanRuleNumbersI2O1Deep() {
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

        findUniversalRuleNrDeep(maxSearchSize, opOutputArr, 2 + 2, 1);
    }

    private static void findSimpleBooleanGridOffNumbersI2O1Deep() {
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

        final int[] rowSizeXArr = new int[] { 2 + 2, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        findUniversalGridOffNrDeep(opOutputArr, rowSizeXArr, noCommutative);
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
                    final Grid universalGrid = GridService.createGridByRuleNr(sizeX, sizeY, universalRuleNr);
                    printGridRuleNr(universalGrid);
                    break outerLoop;
                } else {
                    System.out.println("Keine universelle RuleNr gefunden, die für alle Operationen gültig ist.");
                }
            }
        }
    }

    private static void findUniversalRuleOffNr(List<OpOutput> opOutputArr,
                                               final int[] rowSizeXArr,
                                               final boolean noCommutative) {
        final List<Integer>[] matchingRuleOffListArr = findRuleOffNrList(opOutputArr, rowSizeXArr, noCommutative);
        final Integer universalRuleOffNr = findUniversalMatchingRuleNr(matchingRuleOffListArr);
        if (Objects.nonNull(universalRuleOffNr)) {
            System.out.printf("Universelle RuleOffNr (für alle Operationen gültig): %d%n", universalRuleOffNr);
            final Grid universalGrid = GridService.createGridByRuleOffNr(rowSizeXArr, universalRuleOffNr, noCommutative);
            printGridRuleNr(universalGrid);
        } else {
            System.out.println("Keine universelle RuleNr gefunden, die für alle Operationen gültig ist.");
        }
    }

    private static void findUniversalRuleNrDeep(final int maxSearchSize, List<OpOutput> opOutputArr,
                                                final int startSizeX, final int startSizeY) {
        outerLoop:
        for (int sizeX = startSizeX; sizeX <= maxSearchSize; sizeX++) {
            for (int sizeY = startSizeY; sizeY <= maxSearchSize; sizeY++) {
                final int[] rowSizeXArr = new int[sizeY];
                for (int rowPos = 0; rowPos < sizeY; rowPos++) {
                    rowSizeXArr[rowPos] = sizeX;
                }
                final List<BigInteger>[] matchingGridNrListArr = findGridNrListDeep(opOutputArr, rowSizeXArr);
                final BigInteger universalGridNr = findUniversalMatchingGridNrDeep(matchingGridNrListArr);
                if (Objects.nonNull(universalGridNr)) {
                    System.out.printf("Universelle GridNr (für alle Operationen gültig): %d%n", universalGridNr);
                    // Create and show the universal grid
                    final Grid universalGrid = GridService.createGridForCombination(rowSizeXArr, universalGridNr);
                    printGridRuleNr(universalGrid);
                    break outerLoop;
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

            final List<Integer> matchingRuleNrList = new ArrayList<>();
            // Prüfe alle 256 Regeln parallel
            IntStream.rangeClosed(0, GridService.MAX_RULE_NR).parallel().forEach(ruleNr -> {
                boolean allInputsMatch = true;
                for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length && allInputsMatch; inputArrArrPos++) {
                    final int[] inputArr = inputArrArr[inputArrArrPos];
                    final Grid grid = GridService.createGridByRuleNr(sizeX, sizeY, ruleNr);
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

    private static List<Integer>[] findRuleOffNrList(final List<OpOutput> opOutputArr,
                                                     final int[] rowSizeXArr,
                                                     final boolean noCommutative) {
        System.out.printf("---------------------------------------------------------%n");
        final int cellCount = GridService.calcCellCount(rowSizeXArr);
        final int maxRuleNr = GridService.calcMaxRuleNr();
        final int maxOffsetCombinations = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);
        System.out.printf("size: %s, %d+1 (cellCount: %d, maxRuleNr: %,d, maxOff: %,d)%n",
                Arrays.toString(rowSizeXArr), rowSizeXArr.length, cellCount, maxRuleNr, maxOffsetCombinations);

        @SuppressWarnings("unchecked")
        final List<Integer>[] matchingRuleOffListArr = new ArrayList[opOutputArr.size()];

        // Parallele Verarbeitung jeder Operation
        IntStream.range(0, opOutputArr.size()).parallel().forEach(pos -> {
            final OpOutput opOutput = opOutputArr.get(pos);
            final String opName = opOutput.opName();
            final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
            final int[][] inputArrArr = opOutput.inputArrArr;

            final List<Integer> matchingRuleOffNrList = new ArrayList<>();
            // Prüfe alle 256 Regeln parallel
            IntStream.rangeClosed(0, GridService.MAX_RULE_NR).parallel().forEach(ruleNr -> {
                for (int offNr = 0; offNr < maxOffsetCombinations; offNr++) {
                    final int ruleOffNr = ruleNr * maxOffsetCombinations + offNr;
                    boolean allInputsMatch = true;
                    for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length && allInputsMatch; inputArrArrPos++) {
                        final int[] inputArr = inputArrArr[inputArrArrPos];
                        final Grid grid = GridService.createGridByRuleNrAndOffNr(rowSizeXArr, ruleNr, offNr, noCommutative);
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
                        synchronized (matchingRuleOffNrList) { // Sammelliste schützen
                            matchingRuleOffNrList.add(ruleOffNr);
                        }
                    }
                }
            });
            matchingRuleOffNrList.sort(Comparator.comparingInt(ruleNr -> ruleNr));
            matchingRuleOffListArr[pos] = matchingRuleOffNrList;
            System.out.printf("%s: %s\n", opName, matchingRuleOffNrList);
        });

        if (showExtraResults) {
            System.out.printf("---------------------------------------------------------%n");
            for (int pos = 0; pos < opOutputArr.size(); pos++) {
                final OpOutput opOutput = opOutputArr.get(pos);
                final String opName = opOutput.opName();
                final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
                final int[][] inputArrArr = opOutput.inputArrArr;
                System.out.printf("%s: %s\n", opName, matchingRuleOffListArr[pos]);
                if (matchingRuleOffListArr[pos].size() > 0) {
                    printGridForOperation(opName, matchingRuleOffListArr[pos].getFirst(),
                            inputArrArr, expectedOutputArrArr, rowSizeXArr,
                            noCommutative);
                }
            }
        }
        return matchingRuleOffListArr;
    }

    private static List<BigInteger>[] findGridOffNrListDeep(final List<OpOutput> opOutputArr,
                                                         final int[] rowSizeXArr,
                                                         final boolean noCommutative) {
        System.out.printf("---------------------------------------------------------%n");
        // sizeY = Anzahl Regel-Zeilen (ohne Input-Layer)
        final int sizeY = rowSizeXArr.length;
        final int maxRuleNr = GridService.calcMaxRuleNr();
        final BigInteger maxGridNr = GridService.calcMaxGridNr(rowSizeXArr);
        final int maxOffsetCombinations = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);
        final BigInteger maxGridOffNr = maxGridNr.multiply(BigInteger.valueOf(maxOffsetCombinations));
        System.out.printf("size: %s, %d+1 (maxGridNr: %,d, maxRuleNr: : %,d, maxOff: %,d, maxGridOffNr: %s)%n",
                Arrays.toString(rowSizeXArr), sizeY, maxGridNr, maxRuleNr, maxOffsetCombinations, maxGridOffNr);

        final BigInteger progressDivisor;
        final BigInteger tmpProgressDivisor = maxGridOffNr.divide(BigInteger.valueOf(80L));
        if (tmpProgressDivisor.equals(BigInteger.ZERO)) {
            progressDivisor = BigInteger.ONE;
        } else {
            progressDivisor = tmpProgressDivisor;
        }

        @SuppressWarnings("unchecked")
        final List<BigInteger>[] matchingGridNrListArr = new List[opOutputArr.size()];

        System.out.println("|0%----------------|25%----------------|50%----------------|75-----------------|%100%");

        final AtomicLong progressCounter = new AtomicLong(0);
        final int numThreads = Math.max(2, Runtime.getRuntime().availableProcessors() - 2);
        final BigInteger blockSize = BigInteger.valueOf(10_000L);

        final ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(numThreads);

        IntStream.range(0, opOutputArr.size()).forEach(pos -> {
            final OpOutput opOutput = opOutputArr.get(pos);
            final String opName = opOutput.opName();
            final int[][] expectedOutputArrArr = opOutput.expectedOutputArrArr();
            final int[][] inputArrArr = opOutput.inputArrArr;
            final List<BigInteger> localMatcheGridOffNrList = Collections.synchronizedList(new ArrayList<>());
            final List<Future<?>> futureList = new ArrayList<>();
            final Object blockLock = new Object();
            final BigInteger[] nextBlockStart = new BigInteger[] { BigInteger.ZERO };

            for (int t = 0; t < numThreads; t++) {
                futureList.add(executor.submit(() -> {
                    while (true) {
                        final BigInteger startGridNr, endGridNr;
                        synchronized (blockLock) {
                            if (nextBlockStart[0].compareTo(maxGridNr) >= 0) {
                                break;
                            }
                            startGridNr = nextBlockStart[0];
                            endGridNr = startGridNr.add(blockSize).min(maxGridNr);
                            nextBlockStart[0] = endGridNr;
                        }
                        for (BigInteger gridNr = startGridNr; gridNr.compareTo(endGridNr) < 0; gridNr = gridNr.add(BigInteger.ONE)) {
                            for (int offNr = 0; offNr < maxOffsetCombinations; offNr++) {
                                final BigInteger gridOffNr = gridNr.multiply(BigInteger.valueOf(maxOffsetCombinations)).add(BigInteger.valueOf(offNr));
                                final Grid grid = GridService.createGridForCombination(rowSizeXArr, gridNr, offNr, noCommutative);
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
                                    localMatcheGridOffNrList.add(gridOffNr);
                                    if (ShowFoundGridNr) {
                                        synchronized (System.out) {
                                            System.out.println(gridOffNr); // Gefundene Regel-Kombination ausgeben
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
            matchingGridNrListArr[pos] = localMatcheGridOffNrList;
            System.out.printf("%s: %s\n", opName, localMatcheGridOffNrList.size());
        });
        executor.shutdown();

        return matchingGridNrListArr;
    }

    private static List<BigInteger>[] findGridNrListDeep(final List<OpOutput> opOutputArr,
                                                         final int[] rowSizeXArr) {
        System.out.printf("---------------------------------------------------------%n");
        // sizeY = Anzahl Regel-Zeilen (ohne Input-Layer)
        final int sizeY = rowSizeXArr.length;
        final BigInteger maxGridNr = GridService.calcMaxGridNr(rowSizeXArr);
        System.out.printf("size: %s, %d+1 (maxGridNr: %,d)%n", Arrays.toString(rowSizeXArr), sizeY, maxGridNr);

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
                        final BigInteger startGridNr, endGridNr;
                        synchronized (blockLock) {
                            if (nextBlockStart[0].compareTo(maxGridNr) >= 0) {
                                break;
                            }
                            startGridNr = nextBlockStart[0];
                            endGridNr = startGridNr.add(blockSize).min(maxGridNr);
                            nextBlockStart[0] = endGridNr;
                        }
                        for (BigInteger gridNr = startGridNr; gridNr.compareTo(endGridNr) < 0; gridNr = gridNr.add(BigInteger.ONE)) {
                            final Grid grid = GridService.createGridForCombination(rowSizeXArr, gridNr);
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

    private static void findUniversalGridOffNrDeep(List<OpOutput> opOutputArr,
                                                   final int[] rowSizeXArr,
                                                   final boolean noCommutative) {
        final List<BigInteger>[] matchingGridOffNrListArr = findGridOffNrListDeep(opOutputArr, rowSizeXArr, noCommutative);
        final BigInteger universalGridOffNr = findUniversalMatchingGridOffNrDeep(matchingGridOffNrListArr);
        if (Objects.nonNull(universalGridOffNr)) {
            System.out.printf("Universelle GridOffNr (für alle Operationen gültig): %d%n", universalGridOffNr);
            // Create and show the universal grid
            final int maxOffsetCombinations = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);
            final BigInteger gridNr = universalGridOffNr.divide(BigInteger.valueOf(maxOffsetCombinations));
            final int offNr = universalGridOffNr.mod(BigInteger.valueOf(maxOffsetCombinations)).intValue();
            final Grid universalGrid = GridService.createGridForCombination(rowSizeXArr, gridNr, offNr, noCommutative);
            printGridRuleNr(universalGrid);
        } else {
            System.out.println("Keine universelle GridOffNr gefunden, die für alle Operationen gültig ist.");
        }
    }

    private static void findUniversalRuleNrDeep(List<OpOutput> opOutputArr,
                                                final int[] rowSizeXArr) {
        final List<BigInteger>[] matchingGridNrListArr = findGridNrListDeep(opOutputArr, rowSizeXArr);
        final BigInteger universalGridNr = findUniversalMatchingGridNrDeep(matchingGridNrListArr);
        if (Objects.nonNull(universalGridNr)) {
            System.out.printf("Universelle GridNr (für alle Operationen gültig): %d%n", universalGridNr);
            // Create and show the universal grid
            final Grid universalGrid = GridService.createGridForCombination(rowSizeXArr, universalGridNr);
            printGridRuleNr(universalGrid);
        } else {
            System.out.println("Keine universelle GridNr gefunden, die für alle Operationen gültig ist.");
        }
    }

    private static Integer findUniversalMatchingRuleNr(List<Integer>[] matchingRuleOffListArr) {
        Integer universalRuleOffNr = null;
        if (matchingRuleOffListArr.length > 0) {
            Set<Integer> intersection = new HashSet<>(matchingRuleOffListArr[0]);
            for (int i = 1; i < matchingRuleOffListArr.length; i++) {
                intersection.retainAll(matchingRuleOffListArr[i]);
            }
            if (!intersection.isEmpty()) {
                universalRuleOffNr = intersection.iterator().next();
            }
        }
        return universalRuleOffNr;
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

    private static BigInteger findUniversalMatchingGridOffNrDeep(List<BigInteger>[] matchingGridOffNrListArr) {
        BigInteger universalGridOffNr = null;
        if (matchingGridOffNrListArr.length > 0) {
            // Listen nach Größe sortieren (kleinste zuerst)
            List<List<BigInteger>> matchingGridNrListList = Arrays.asList(matchingGridOffNrListArr);
            matchingGridNrListList.sort(Comparator.comparingInt(List::size));

            //final Set<BigInteger> intersectionSet = new HashSet<>(matchingGridOffNrListArr[0]);
            final Set<BigInteger> intersectionSet = new HashSet<>(matchingGridNrListList.get(0));

            //for (int matchingGridNrListArrPos = 1; matchingGridNrListArrPos < matchingGridOffNrListArr.length; matchingGridNrListArrPos++) {
            for (int matchingGridNrListArrPos = 1; matchingGridNrListArrPos < matchingGridNrListList.size(); matchingGridNrListArrPos++) {
                //intersectionSet.retainAll(matchingGridOffNrListArr[matchingGridNrListArrPos]);
                final Set<BigInteger> currentSet = new HashSet<>(matchingGridNrListList.get(matchingGridNrListArrPos));
                intersectionSet.retainAll(currentSet);
                if (intersectionSet.isEmpty()) {
                    break;
                }
            }
            if (!intersectionSet.isEmpty()) {
                universalGridOffNr = intersectionSet.iterator().next();
            }
        }
        return universalGridOffNr;
    }

    private static void printGridForOperation(final String opName, final int ruleNr, final int[][] inputArrArr, final int[][] expectedOutputArrArr, final int sizeX, final int sizeY) {
        System.out.printf("Operation: %s, Regel: %d\n", opName, ruleNr);
        for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length; inputArrArrPos++) {
            final int[] inputArr = inputArrArr[inputArrArrPos];
            final int[] expectedOutputArr = expectedOutputArrArr[inputArrArrPos];
            final Grid grid = GridService.createGridByRuleNr(sizeX, sizeY, ruleNr);
            GridService.submitInput(grid, inputArr);
            CellNetService.calcGrid(grid);
            final int[] outputArr = GridService.retieveOutput(grid);
            System.out.printf("Eingabe: %s, Erwartete Ausgabe: %s, Tatsächliche Ausgabe: %s\n", Arrays.toString(inputArr), Arrays.toString(expectedOutputArr), Arrays.toString(outputArr));
            printGridValue(grid);
        }
        System.out.println();
    }

    private static void printGridForOperation(final String opName, final int ruleNr,
                                              final int[][] inputArrArr,
                                              final int[][] expectedOutputArrArr,
                                              final int[] rowSizeXArr, final boolean noCommutative) {
        System.out.printf("Operation: %s, Regel: %d\n", opName, ruleNr);
        for (int inputArrArrPos = 0; inputArrArrPos < inputArrArr.length; inputArrArrPos++) {
            final int[] inputArr = inputArrArr[inputArrArrPos];
            final int[] expectedOutputArr = expectedOutputArrArr[inputArrArrPos];
            final Grid grid = GridService.createGridByRuleNr(rowSizeXArr, ruleNr, noCommutative);
            GridService.submitInput(grid, inputArr);
            CellNetService.calcGrid(grid);
            final int[] outputArr = GridService.retieveOutput(grid);
            System.out.printf("Eingabe: %s, Erwartete Ausgabe: %s, Tatsächliche Ausgabe: %s\n", Arrays.toString(inputArr), Arrays.toString(expectedOutputArr), Arrays.toString(outputArr));
            printGridValue(grid);
        }
        System.out.println();
    }

    static void printGridValue(final Grid grid) {
        int lastShift = 0;
        for (int y = 0; y < grid.sizeY; y++) {
            final Row row = grid.rowArr[y];
            if (y > 0) {
                final Row parenRow = grid.rowArr[y - 1];
                if (parenRow.sizeX > row.sizeX) {
                    lastShift += parenRow.sizeX - row.sizeX;
                    System.out.print(" ".repeat(lastShift));
                }
            }
            for (int x = 0; x < row.sizeX; x++) {
                System.out.print(row.cellArr[x].value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printGridValueAndGridNr(final Grid grid) {
        int lastShift = 0;
        for (int y = 0; y < grid.sizeY; y++) {
            final Row row = grid.rowArr[y];
            if (y > 0) {
                final Row parenRow = grid.rowArr[y - 1];
                if (parenRow.sizeX > row.sizeX) {
                    lastShift += parenRow.sizeX - row.sizeX;
                    System.out.print(" ".repeat(lastShift));
                }
            }
            for (int x = 0; x < row.sizeX; x++) {
                System.out.print(row.cellArr[x].value + " ");
            }
            System.out.print(" | ");
            for (int x = 0; x < row.sizeX; x++) {
                System.out.printf("%02d ", row.cellArr[x].ruleNr);
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printGridRuleNr(final Grid grid) {
        int lastShift = 0;
        for (int y = 0; y < grid.sizeY; y++) {
            final Row row = grid.rowArr[y];
            if (y > 0) {
                final Row parenRow = grid.rowArr[y - 1];
                if (parenRow.sizeX > row.sizeX) {
                    lastShift += parenRow.sizeX - row.sizeX;
                    System.out.print(" ".repeat(lastShift));
                }
            }
            for (int x = 0; x < row.sizeX; x++) {
                System.out.printf("%02d ", row.cellArr[x].ruleNr);
            }
            System.out.println();
        }
        System.out.println();
    }
}

