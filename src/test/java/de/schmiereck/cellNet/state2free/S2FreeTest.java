package de.schmiereck.cellNet.state2free;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

public class S2FreeTest {
    @Test
    public void testFreeInputs() {

    }

    @Test
    public void testCheckGeneratedRuleCombinationsI2O2Visible() {
        final int[] rowSizeXArr = new int[]{ 2, 2, 2 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = false;

        final int maxRuleNr = GridService.calcMaxRuleNr();
        final BigInteger maxGridNr = GridService.calcMaxGridNr(rowSizeXArr);
        final int maxOffNr = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);

        Assertions.assertEquals(255, maxOffNr);

        for (int offNr = 0; offNr <= maxOffNr; offNr++) {
            //System.out.printf("GridNr: %6d ", gridNr);
            final int ruleNr = 0;
            final Grid grid = GridService.createGridByRuleNrAndOffNr(rowSizeXArr, ruleNr, offNr, noCommutative);

            for (int y = 0; y < grid.sizeY; y++) {
                final Row row = grid.rowArr[y];
                for (int x = 0; x < row.sizeX; x++) {
                    final Cell cell = row.cellArr[x];
                    //System.out.printf("%3d ", cell.ruleNr);
                }
            }

            switch (offNr) {
                // 0:
                case 0 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 1 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 2 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 3 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 4 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 5 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 6 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 7 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 8 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 9 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 10 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 11 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 12 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 13 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 14 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                case 15 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 0 }, { 0, 0 } });
                }
                // 1:
                case 16 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 17 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 18 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 19 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 20 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 21 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 22 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 23 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 24 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 25 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 26 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 27 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 1, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 28 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 29 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 1 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 30 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 0 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                case 31 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 0, 1 }, { 0, 0 } });
                }
                // 2:
                case 32 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 0, 0 }, { 0, 0 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 1, 0 }, { 0, 0 } });
                }
                // ...
                case 255 -> {
                    assertEqualsCellRuleNrArr(offNr, grid, 0, new int[][] { { 0, 1 }, { 0, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 1, new int[][] { { 1, 1 }, { 1, 1 } });
                    assertEqualsCellRuleNrArr(offNr, grid, 2, new int[][] { { 1, 1 }, { 1, 1 } });
                }
            }

            //System.out.println();
        }
    }

    @Test
    public void testCheckGeneratedRuleCombinationsI2O2() {
        final int[] rowSizeXArr = new int[]{ 2, 2, 2 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = false;

        final int parentRowSizeX = rowSizeXArr[0];
        final int childRowSizeX = rowSizeXArr[1];
        final int combinationsPerCell = parentRowSizeX * parentRowSizeX;
        final int maxOffNr = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);
        Assertions.assertEquals(255, maxOffNr);
        // Prüfe für jede OffNr, ob die Kombinationen korrekt verteilt werden
        for (int offNr = 0; offNr <= maxOffNr; offNr++) {
            final Grid grid = GridService.createGridByRuleNrAndOffNr(rowSizeXArr, 0, offNr, noCommutative);
            // Prüfe für jede Zelle der Regelzeile
            int tmp = offNr;
            for (int x = 0; x < childRowSizeX; x++) {
                int idx = tmp % combinationsPerCell;
                int[] expected = GridService.calculateOffsetCombinations(parentRowSizeX, noCommutative).get(idx);
                Cell cell = grid.rowArr[1].cellArr[x];
                Assertions.assertEquals(expected[0], cell.leftOffX, "offNr: %d, cell: %d left".formatted(offNr, x));
                Assertions.assertEquals(expected[1], cell.rightOffX, "offNr: %d, cell: %d right".formatted(offNr, x));
                tmp /= combinationsPerCell;
            }
        }
    }

    @Test
    public void testCheckGeneratedRuleCombinationsI2O2NoCommutative() {
        final int[] rowSizeXArr = new int[]{ 2, 2, 2 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = true;

        final int parentRowSizeX = rowSizeXArr[0];
        final int childRowSizeX = rowSizeXArr[1];
        final int combinationsPerCell = GridService.calcCombinationsPerCell(parentRowSizeX, noCommutative);
        final int maxOffNr = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);
        Assertions.assertEquals(80, maxOffNr);
        // Prüfe für jede OffNr, ob die Kombinationen korrekt verteilt werden
        for (int offNr = 0; offNr <= maxOffNr; offNr++) {
            final Grid grid = GridService.createGridByRuleNrAndOffNr(rowSizeXArr, 0, offNr, noCommutative);
            // Prüfe für jede Zelle der Regelzeile
            int tmp = offNr;
            for (int x = 0; x < childRowSizeX; x++) {
                int idx = tmp % combinationsPerCell;
                int[] expected = GridService.calculateOffsetCombinations(parentRowSizeX, noCommutative).get(idx);
                Cell cell = grid.rowArr[1].cellArr[x];
                Assertions.assertEquals(expected[0], cell.leftOffX, "offNr: %d, cell: %d left".formatted(offNr, x));
                Assertions.assertEquals(expected[1], cell.rightOffX, "offNr: %d, cell: %d right".formatted(offNr, x));
                tmp /= combinationsPerCell;
            }
        }
    }

    @Test
    public void testCheckGeneratedRuleCombinationsI3O3() {
        final int[] rowSizeXArr = new int[]{ 3, 3, 3 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = false;

        final int parentRowSizeX = rowSizeXArr[0];
        final int childRowSizeX = rowSizeXArr[1];
        final int combinationsPerCell = GridService.calcCombinationsPerCell(parentRowSizeX, noCommutative);
        //final int maxOffNr = (int)Math.pow(combinationsPerCell, childRowSizeX);
        final int maxOffNr = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);
        Assertions.assertEquals(531_440, maxOffNr);
        for (int offNr = 0; offNr <= maxOffNr; offNr++) {
            final Grid grid = GridService.createGridByRuleNrAndOffNr(rowSizeXArr, 0, offNr, noCommutative);
            int tmp = offNr;
            for (int x = 0; x < childRowSizeX; x++) {
                int idx = tmp % combinationsPerCell;
                int[] expected = GridService.calculateOffsetCombinations(parentRowSizeX, noCommutative).get(idx);
                Cell cell = grid.rowArr[1].cellArr[x];
                Assertions.assertEquals(expected[0], cell.leftOffX, "offNr: %d, cell: %d left".formatted(offNr, x));
                Assertions.assertEquals(expected[1], cell.rightOffX, "offNr: %d, cell: %d right".formatted(offNr, x));
                tmp /= combinationsPerCell;
            }
        }
    }

    @Test
    public void testCheckGeneratedRuleCombinationsI3O1() {
        final int[] rowSizeXArr = new int[]{ 3, 3, 1 }; // Input-Layer und eine Regel-Zeile
        final boolean noCommutative = false;

        final int parentRowSizeX = rowSizeXArr[0];
        final int childRowSizeX = rowSizeXArr[1];
        final int combinationsPerCell = GridService.calcCombinationsPerCell(parentRowSizeX, noCommutative);
        //final int maxOffNr = (int)Math.pow(combinationsPerCell, childRowSizeX);
        final int maxOffNr = GridService.calcMaxOffsetCombinations(rowSizeXArr, noCommutative);
        Assertions.assertEquals(6_560, maxOffNr);
        for (int offNr = 0; offNr <= maxOffNr; offNr++) {
            final Grid grid = GridService.createGridByRuleNrAndOffNr(rowSizeXArr, 0, offNr, noCommutative);
            int tmp = offNr;
            for (int x = 0; x < childRowSizeX; x++) {
                int idx = tmp % combinationsPerCell;
                int[] expected = GridService.calculateOffsetCombinations(parentRowSizeX, noCommutative).get(idx);
                Cell cell = grid.rowArr[1].cellArr[x];
                Assertions.assertEquals(expected[0], cell.leftOffX, "offNr: %d, cell: %d left".formatted(offNr, x));
                Assertions.assertEquals(expected[1], cell.rightOffX, "offNr: %d, cell: %d right".formatted(offNr, x));
                tmp /= combinationsPerCell;
            }
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
