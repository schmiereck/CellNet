package de.schmiereck.cellNet.state2;

public class CellNetService {

    public static void calcGrid(final Grid grid) {
        for (int y = 1; y < grid.sizeY; y++) {
            final Row parentRow = grid.rowArr[y - 1];
            final Cell[] parentCellArr = parentRow.cellArr;
            final Row actRow = grid.rowArr[y];
            final Cell[] actCellArr = actRow.cellArr;
            for (int x = 0; x < actRow.sizeX; x++) {
                final Cell calcCell = actCellArr[x];
                // Nachbarn bestimmen (Randbehandlung: Wrap-Around)
                final int parentLeftX = (x < parentRow.sizeX) ? x : x % parentRow.sizeX;
                final int rightX = x + 1;
                final int parentRightX = (rightX < parentRow.sizeX - 1) ? rightX : rightX % parentRow.sizeX;

                //final int leftValue = parentCellArr[leftIndex].value;
                //final int middleValue = parentCellArr[x].value;
                //final int rightValue = parentCellArr[rightX].value;
                final int leftValue = parentCellArr[parentLeftX].value;
                final int rightValue = parentCellArr[parentRightX].value;

                final int ruleNr = calcCell.ruleNr;

                calcCell.value = calcNewValue(leftValue, rightValue, ruleNr);
            }
        }
    }

    private static int calcNewValue(final int leftValue, final int rightValue, final int ruleNr) {
        // Nachbarschaft als Bitmuster
        final int pattern = (leftValue << 1) | rightValue;
        // Regel anwenden: das Bit an der Position 'pattern' gibt den neuen Wert
        return (ruleNr >> pattern) & 1;
    }
}
