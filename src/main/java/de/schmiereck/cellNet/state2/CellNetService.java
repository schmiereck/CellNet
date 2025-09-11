package de.schmiereck.cellNet.state2;

public class CellNetService {

    public static void calcGrid(final Grid grid) {
        for (int y = 1; y < grid.sizeY; y++) {
            final Cell[] parentCellArr = grid.cellArr[y - 1];
            final Cell[] cellArr = grid.cellArr[y];
            for (int x = 0; x < grid.sizeX; x++) {
                final Cell calcCell = cellArr[x];
                // Nachbarn bestimmen (Randbehandlung: Wrap-Around)
                final int leftIndex = (x > 0) ? x - 1 : grid.sizeX - 1;
                final int rightIndex = (x < grid.sizeX - 1) ? x + 1 : 0;
                final int leftValue = parentCellArr[leftIndex].value;
                final int middleValue = parentCellArr[x].value;
                final int rightValue = parentCellArr[rightIndex].value;
                final int ruleNr = calcCell.ruleNr;

                final int newValue = calcNewValue(leftValue, middleValue, rightValue, ruleNr);

                calcCell.value = newValue;
            }
        }
    }

    private static int calcNewValue(final int leftValue, final int middleValue, final int rightValue, final int ruleNr) {
        // Nachbarschaft als Bitmuster
        final int pattern = (leftValue << 2) | (middleValue << 1) | rightValue;
        // Regel anwenden: das Bit an der Position 'pattern' gibt den neuen Wert
        final int newValue = (ruleNr >> pattern) & 1;
        return newValue;
    }
}
