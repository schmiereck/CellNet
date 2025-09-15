package de.schmiereck.cellNet.state3;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CellNetServiceTest {
    @Test
    public void testCalcGrid_rule30() {
        // Rule 30: 00011110 (binär), 30 (dezimal)
        int ruleNr = 30;
        int sizeX = 5;
        int sizeY = 3;
        Grid grid = new Grid(sizeX, sizeY);
        grid.cellArrArr = new Cell[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                grid.cellArrArr[y][x] = new Cell();
                grid.cellArrArr[y][x].ruleNr = ruleNr;
                grid.cellArrArr[y][x].value = 0;
            }
        }
        // Startwert: nur die mittlere Zelle in der ersten Zeile ist 1
        grid.cellArrArr[0][sizeX/2].value = 1;

        CellNetService.calcGrid(grid);

        // Erwartete Werte für Rule 30
        // Zeile 0: 0 0 1 0 0
        // Zeile 1: 0 1 1 1 0
        // Zeile 2: 1 1 0 0 1
        int[][] expected = {
            {0,0,1,0,0},
            {0,1,1,1,0},
            {1,1,0,0,1}
        };
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                assertEquals(expected[y][x], grid.cellArrArr[y][x].value, "Fehler bei Zeile "+y+", Spalte "+x);
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
                for (int mid = 0; mid <= 1; mid++) {
                    for (int right = 0; right <= 1; right++) {
                        int val = CellNetService.calcNewValue(left, mid, right, ruleNr);
                        resultPatternBuilder.append(val);
                    }
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

        for (int ruleNr = 0; ruleNr < GridService.RULE_COUNT; ruleNr++) {
            StringBuilder patternBuilder = new StringBuilder();
            for (int leftValue = 0; leftValue <= 1; leftValue++) {
                for (int middleValue = 0; middleValue <= 1; middleValue++) {
                    for (int rightValue = 0; rightValue <= 1; rightValue++) {
                        int newValue = CellNetService.calcNewValue(leftValue, middleValue, rightValue, ruleNr);
                        patternBuilder.append(newValue);
                    }
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
