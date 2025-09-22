package de.schmiereck.cellNet.state2free;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class S2FreeTest {
    @Test
    public void testFreeInputs() {

    }

    @Test
    public void testCheckGeneratedRuleCombinations() {
        final int[] rowSizeXArr = new int[]{2, 2}; // Input-Layer und eine Regel-Zeile

        final int maxRuleNr = GridService.calcMaxRuleNr();
        final BigInteger maxGridNr = GridService.calcMaxGridNr(rowSizeXArr);
        final int maxOffNr = GridService.calcMaxOffsetCombinations(rowSizeXArr);

        // 0 1
        // 2 3
        // 4 5

        // offNr: 0
        // 0:[0,1], 1:[0,1]
        // 2:[0,0], 3:[0,0]
        // 3:[0,0], 4:[0,0]

        // offNr: 2
        // 0:[0,1], 1:[0,1]
        // 2:[1,0], 3:[0,0]
        // 3:[0,0], 4:[0,0]

        // offNr: 3
        // 0:[0,1], 1:[0,1]
        // 2:[1,1], 3:[0,0]
        // 3:[0,0], 4:[0,0]

        // offNr: 4
        // 0:[0,1], 1:[0,1]
        // 2:[0,0], 3:[1,0]
        // 3:[0,0], 4:[0,0]

        // offNr: 5
        // 0:[0,1], 1:[0,1]
        // 2:[1,0], 3:[1,0]
        // 3:[0,0], 4:[0,0]

        // offNr: 6
        // 0:[0,1], 1:[0,1]
        // 2:[1,1], 3:[1,0]
        // 3:[0,0], 4:[0,0]

        // offNr: 7
        // 0:[0,1], 1:[0,1]
        // 2:[1,1], 3:[1,1]
        // 3:[0,0], 4:[0,0]

        // offNr: 8
        // ...

        Assertions.assertEquals(9, maxOffNr);

        for (int offNr = 0; offNr <= maxOffNr; offNr++) {
            //System.out.printf("GridNr: %6d ", gridNr);
            final int ruleNr = 0;
            final Grid grid = GridService.createGridByRuleNrAndOffNr(rowSizeXArr, ruleNr, offNr);

            for (int y = 0; y < grid.sizeY; y++) {
                final Row row = grid.rowArr[y];
                for (int x = 0; x < row.sizeX; x++) {
                    final Cell cell = row.cellArr[x];
                    //System.out.printf("%3d ", cell.ruleNr);
                }
            }

            switch (offNr) {
                case 0 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 1 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 2 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 3 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 4 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 5 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 6 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 7 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 8 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 9 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 1, 1 }, { 0, 0 } });
                }
            }

            //System.out.println();
        }
    }

    private static void assertEqualsCellRuleNrArr(final int offNr, final Grid grid, final int y, final int[][] cellOffArr) {
        final Row row = grid.rowArr[y];
        for (int x = 0; x < row.sizeX; x++) {
            final Cell cell = row.cellArr[x];
            Assertions.assertEquals(cellOffArr[x][0], cell.leftOffX, "off-Nr: %d left (x: %d, y: %d)".formatted(offNr, x, y));
            Assertions.assertEquals(cellOffArr[x][1], cell.rightOffX, "off-Nr: %d right (x: %d, y: %d)".formatted(offNr, x, y));
        }
    }
}
