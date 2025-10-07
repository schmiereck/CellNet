package de.schmiereck.cellNet.state2fry;

import java.util.ArrayList;
import java.util.List;

public abstract class PositionGridUtils {
    private static boolean UseOnlyParentForPosCombinations = true;

    private PositionGridUtils() {
    }

    public static long calcNextPositionsForPosNr(final Cell cell,
                                                 final int[] rowSizeXArr, final int y, final long currentPosNr,
                                                 final boolean noCommutative) {
        final long retPosNr;
        if (y == 0) {
            // Input-Layer: Standard-Positions
            cell.leftPosX = 0;
            cell.rightPosX = 0;//Cell.RIGHT_OFF_X;
            retPosNr = currentPosNr;
        } else {
            // Regel-Zeilen: Positions basierend auf posNr berechnen
            final int parentRowSizeX = rowSizeXArr[y - 1];
            final List<int[]> positionCombinations = PositionGridUtils.calculatePositionCombinations(parentRowSizeX, noCommutative);

            if (!positionCombinations.isEmpty()) {
                final int[] selectedPosition = positionCombinations.get(((int) (currentPosNr % positionCombinations.size())));
                cell.leftPosX = selectedPosition[0];
                cell.rightPosX = selectedPosition[1];
                retPosNr = currentPosNr / positionCombinations.size();
            } else {
                // Fallback auf Standard-Positions
                cell.leftPosX = 0;
                cell.rightPosX = Cell.RIGHT_OFF_X;
                retPosNr = currentPosNr;
            }
        }
        return retPosNr;
    }

    /**
     * Berechnet die maximale Anzahl der Position-Kombinationen für die gegebenen Zeilegrößen.
     * Nutzt die neue calculatePositionCombinations-Logik ([i, i] und nur [1,0] für parentRowSizeX > 1).
     * keine kommutativen Kombinationen:
     * wobei vertauschte Kombinationen (a, b) und (b, a) als identisch gelten (nur a ≤ b).
     */
    public static long calcMaxPositionCombinations(final int[] rowSizeXArr, final boolean noCommutative) {
        long maxCombinations = 1L;
        // Input-Layer ist die erste Regel-Zeile.
        for (int y = 1; y < rowSizeXArr.length; y++) {
            final long posCombinationsPerCell = calcPosCombinationsPerCell(rowSizeXArr, y, noCommutative);
            final int currentRowSizeX = rowSizeXArr[y];
            for (int x = 0; x < currentRowSizeX; x++) {
                maxCombinations *= posCombinationsPerCell;
            }
        }
        //return maxCombinations - 1; // -1, da posNr bei 0 beginnt
        return maxCombinations; // -1, da posNr bei 0 beginnt
    }

    public static long calcPosCombinationsPerCell(final int[] rowSizeXArr, final int cellPosY, final boolean noCommutative) {
        final long combinationsPerCell;
        if (UseOnlyParentForPosCombinations) {
            final int parentRowSizeX = rowSizeXArr[cellPosY - 1];
            if (noCommutative) {
                // Kommutative Kombinationen pro Zelle in der aktuellen Zeile nicht prüfen.
                combinationsPerCell = (parentRowSizeX * (parentRowSizeX + 1L)) / 2L;
            } else {
                combinationsPerCell = ((long) parentRowSizeX) * parentRowSizeX;
            }
        } else {
            throw new RuntimeException("Not implemented yet!");
        }
        return combinationsPerCell;
    }

    /**
     * Berechnet alle möglichen Position-Kombinationen für zwei Eingänge (z.B. leftPosX, rightPosX) und beliebig viele Parents.
     * Gibt alle n*n Kombinationen zurück (auch [0,1] und [1,0] etc.).
     * keine kommutativen Kombinationen:
     * wobei vertauschte Kombinationen (a, b) und (b, a) als identisch gelten (nur a ≤ b).
     * Gibt alle n*(n+1)/2 Kombinationen zurück (z.B. [0,0], [0,1], [1,1] ...).
     */
    public static List<int[]> calculatePositionCombinations(final int parentRowSizeX, final boolean noCommutative) {
        final List<int[]> combinations = new ArrayList<>();
        if (UseOnlyParentForPosCombinations) {
            for (int i = 0; i < parentRowSizeX; i++) {
                if (noCommutative) {
                    for (int j = i; j < parentRowSizeX; j++) {
                        combinations.add(new int[]{i, j});
                    }
                } else {
                    for (int j = 0; j < parentRowSizeX; j++) {
                        combinations.add(new int[]{i, j});
                    }
                }
            }
        } else {
            throw new RuntimeException("Not implemented yet!");
        }
        return combinations;
    }
}
