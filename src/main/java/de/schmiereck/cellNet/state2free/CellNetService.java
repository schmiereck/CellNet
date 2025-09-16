package de.schmiereck.cellNet.state2free;

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
                final int cellLeftX = x + calcCell.leftOffX;
                final int parentLeftX = (cellLeftX < parentRow.sizeX) ? cellLeftX : cellLeftX % parentRow.sizeX;
                final int cellRightX = x + calcCell.rightOffX;
                final int parentRightX = (cellRightX < parentRow.sizeX - 1) ? cellRightX : cellRightX % parentRow.sizeX;

                //final int leftValue = parentCellArr[leftIndex].value;
                //final int middleValue = parentCellArr[x].value;
                //final int rightValue = parentCellArr[cellRightX].value;
                final int leftValue = parentCellArr[parentLeftX].value;
                final int rightValue = parentCellArr[parentRightX].value;

                final int ruleNr = calcCell.ruleNr;

                calcCell.value = calcNewValue(leftValue, rightValue, ruleNr);
            }
        }
    }

    static int calcNewValue(final int leftValue, final int rightValue, final int ruleNr) {
        // Nachbarschaft als Bitmuster
        final int pattern = (leftValue << 1) | rightValue;
        // Regel anwenden: das Bit an der Position 'pattern' gibt den neuen Wert
        return (ruleNr >> pattern) & 1;
    }
}
