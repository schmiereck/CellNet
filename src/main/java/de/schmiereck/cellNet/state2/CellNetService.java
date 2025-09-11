package de.schmiereck.cellNet.state2;

public class CellNetService {

    public static void calcGrid(final Grid grid) {
        for (int y = 1; y < grid.sizeY; y++) {
            final Cell[] parentCellArr = grid.rowArr[y - 1].cellArr;
            final Cell[] cellArr = grid.rowArr[y].cellArr;
            for (int x = 0; x < grid.sizeX; x++) {
                final Cell calcCell = cellArr[x];
                // Nachbarn bestimmen (Randbehandlung: Wrap-Around)
                final int leftIndex = (x > 0) ? x - 1 : grid.sizeX - 1;
                final int rightIndex = (x < grid.sizeX - 1) ? x + 1 : 0;

                //final int leftValue = parentCellArr[leftIndex].value;
                //final int middleValue = parentCellArr[x].value;
                //final int rightValue = parentCellArr[rightIndex].value;
                final int leftValue = parentCellArr[leftIndex].value;
                final int rightValue = parentCellArr[x].value;

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
