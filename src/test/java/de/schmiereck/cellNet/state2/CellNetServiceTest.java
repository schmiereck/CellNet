package de.schmiereck.cellNet.state2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CellNetServiceTest {
    @Test
    public void testCalcGrid_rule3() {
        // Rule 3: 00011 (binär), 3 (dezimal)
        int ruleNr = 3;
        final int[] rowSizeXArr = new int[] { 5, 5, 5 };
        int sizeY = 3;
        Grid grid = new Grid(sizeY);
        grid.rowArr = new Row[sizeY];
        for (int y = 0; y < sizeY; y++) {
            // Input-Layer (y=0) bekommt die Größe der ersten Regel-Zeile
            final int sizeX = rowSizeXArr[y == 0 ? 0 : y - 1];
            grid.rowArr[y] = new Row(sizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeXArr[y]];
            for (int x = 0; x < sizeX; x++) {
                grid.rowArr[y].cellArr[x] = new Cell();
                grid.rowArr[y].cellArr[x].ruleNr = ruleNr;
                grid.rowArr[y].cellArr[x].value = 0;
            }
        }
        // Startwert: nur die mittlere Zelle in der ersten Zeile ist 1
        final int[] inputArr = new int[] {0, 0, 1, 0, 0};
        GridService.submitInput(grid, inputArr);

        CellNetService.calcGrid(grid);

        //S2CellNetMain.printGrid(grid);

        // Erwartete Werte für Rule 3 (binär 0011):
        // Nur für Muster 00 (0) und 01 (1) ergibt sich eine 1, sonst 0.
        // Zeile 0: 0 0 1 0 0
        // Zeile 1:  1 1 0 1 1
        // Zeile 2:   0 0 1 0 0
        int[][] expected = {
            {0,0,1,0,0  },
            { 1,1,0,1,1 },
            {  0,0,1,0,0}
        };
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < rowSizeXArr[y]; x++) {
                assertEquals(expected[y][x], grid.rowArr[y].cellArr[x].value, "Fehler bei Zeile "+ y +", Spalte " + x);
            }
        }
    }

    @Test
    public void testCalcGrid2_rule3() {
        // Rule 3: 00011 (binär), 3 (dezimal)
        int ruleNr = 3;
        final int[] rowSizeXArr = new int[] { 5, 4, 3, 2, 1 };
        int sizeY = rowSizeXArr.length;
        Grid grid = new Grid(sizeY);
        grid.rowArr = new Row[sizeY];
        for (int y = 0; y < sizeY; y++) {
            // Input-Layer (y=0) bekommt die Größe der ersten Regel-Zeile
            //final int sizeX = rowSizeXArr[y == 0 ? 0 : y - 1];
            final int sizeX = rowSizeXArr[y];
            grid.rowArr[y] = new Row(sizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeXArr[y]];
            for (int x = 0; x < sizeX; x++) {
                grid.rowArr[y].cellArr[x] = new Cell();
                grid.rowArr[y].cellArr[x].ruleNr = ruleNr;
                grid.rowArr[y].cellArr[x].value = 0;
            }
        }
        // Startwert: nur die mittlere Zelle in der ersten Zeile ist 1
        final int[] inputArr = new int[] {0, 0, 1, 0, 0};
        GridService.submitInput(grid, inputArr);

        CellNetService.calcGrid(grid);

        S2CellNetMain.printGridValue(grid);

        // Erwartete Werte für Rule 3 (binär 0011):
        // Nur für Muster 00 (0) und 01 (1) ergibt sich eine 1, sonst 0.
        // Zeile 0: 0 0 1 0 0
        // Zeile 1:  1 1 0 1
        // Zeile 2:   0 0 1
        // Zeile 3:    1 1
        // Zeile 4:     0
        int[][] expected = {
            {0,0,1,0,0},
            { 1,1,0,1 },
            {  0,0,1  },
            {   1,1   },
            {    0    }
        };
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < rowSizeXArr[y]; x++) {
                assertEquals(expected[y][x], grid.rowArr[y].cellArr[x].value, "Fehler bei Zeile " + y + ", Spalte " + x);
            }
        }
    }

    @Test
    public void testCreateGridForCombination_ruleNrCoverage() {
        // Grid-Größe: [3, 2, 1] (Input-Layer, 1. Regelzeile, 2. Regelzeile)
        final int[] rowSizeXArr = new int[] { 3, 2, 1 };
        int sizeY = 2; // Anzahl Regelzeilen (ohne Input-Layer)

        // Wir testen einige Kombinationen gezielt:
        // gridNr = 0: alle ruleNr = 0
        // gridNr = 1: erste Zelle (1. Regelzeile) ruleNr = 1, Rest 0
        // gridNr = 16: erste Zelle (1. Regelzeile) ruleNr = 0, zweite Zelle (1. Regelzeile) ruleNr = 1, Rest 0
        // gridNr = 255: alle ruleNr = 15 (maximal)
        java.math.BigInteger[] testGridNrs = new java.math.BigInteger[] {
            java.math.BigInteger.valueOf(0),
            java.math.BigInteger.valueOf(1),
            java.math.BigInteger.valueOf(16),
            java.math.BigInteger.valueOf(255)
        };
        for (java.math.BigInteger gridNr : testGridNrs) {
            Grid grid = GridService.createGridForCombination(rowSizeXArr, sizeY, gridNr);
            // Input-Layer (y=0) muss ruleNr=0 haben
            for (int x = 0; x < rowSizeXArr[0]; x++) {
                assertEquals(0, grid.rowArr[0].cellArr[x].ruleNr, "Input-Layer ruleNr muss 0 sein");
            }
            // Regelzellen prüfen
            java.math.BigInteger nr = gridNr;
            int idx = 0;
            for (int y = 1; y <= sizeY; y++) {
                for (int x = 0; x < rowSizeXArr[y]; x++) {
                    int expectedRuleNr = nr.mod(java.math.BigInteger.valueOf(GridService.RULE_COUNT)).intValue();
                    assertEquals(expectedRuleNr, grid.rowArr[y].cellArr[x].ruleNr,
                        String.format("Fehler bei gridNr=%s, Zeile=%d, Spalte=%d: erwartet %d, war %d", gridNr, y, x, expectedRuleNr, grid.rowArr[y].cellArr[x].ruleNr));
                    nr = nr.divide(java.math.BigInteger.valueOf(GridService.RULE_COUNT));
                    idx++;
                }
            }
        }
    }

    @Test
    public void testFindRulesWithDifferentResults() {
        Map<String, Integer> uniqueResultPatternToRule = findRulesWithDifferentResults();
        int[] rules = uniqueResultPatternToRule.values().stream().mapToInt(Integer::intValue).toArray();
        assertNotNull(rules);
        assertEquals(GridService.RULE_COUNT, uniqueResultPatternToRule.size(), "There are double rules with same result.");
        assertTrue(rules.length > 0, "Es sollten Regeln mit unterschiedlichen Ergebnismustern existieren.");

        System.out.printf("rules (%d): %s%n", rules.length, Arrays.toString(rules));

        for (final String resultPattern : uniqueResultPatternToRule.keySet()) {
            System.out.printf("Pattern: %s => Rule: %d%n", resultPattern, uniqueResultPatternToRule.get(resultPattern));
        }

        java.util.Set<String> resultPatternSet = new java.util.HashSet<>();
        for (int ruleNr : rules) {
            StringBuilder resultPatternBuilder = new StringBuilder();
            for (int left = 0; left <= 1; left++) {
                for (int right = 0; right <= 1; right++) {
                    int val = CellNetService.calcNewValue(left, right, ruleNr);
                    resultPatternBuilder.append(val);
                }
            }
            String resultPattern = resultPatternBuilder.toString();
            // Muster muss eindeutig sein
            assertTrue(resultPatternSet.add(resultPattern), "Doppeltes Ergebnismuster gefunden für Regel " + ruleNr + ": " + resultPattern);
        }
        // Die Anzahl der Regeln muss der Anzahl der unterschiedlichen Muster entsprechen
        assertEquals(resultPatternSet.size(), rules.length, "Jede Regel muss ein einzigartiges Ergebnismuster haben.");
    }

    private static Map<String, Integer> findRulesWithDifferentResults() {
        Map<String, Integer> uniquePatternToRule = new java.util.LinkedHashMap<>();

        for (int ruleNr = 0; ruleNr < de.schmiereck.cellNet.state2.GridService.RULE_COUNT; ruleNr++) {
            StringBuilder patternBuilder = new StringBuilder();
            for (int leftValue = 0; leftValue <= 1; leftValue++) {
                for (int rightValue = 0; rightValue <= 1; rightValue++) {
                    int newValue =
                            CellNetService.calcNewValue(leftValue, rightValue, ruleNr);
                    patternBuilder.append(newValue);
                }
            }
            String pattern = patternBuilder.toString();
            // Nur die erste Regel mit diesem Muster aufnehmen
            if (!uniquePatternToRule.containsKey(pattern)) {
                uniquePatternToRule.put(pattern, ruleNr);
            }
        }
        // Rückgabe als int[]
        return uniquePatternToRule;
    }
}
