package de.schmiereck.cellNet.state3;

public class CellNetService {

    public static void calcGrid(final Grid grid) {
        for (int y = 1; y < grid.sizeY; y++) {
            final Cell[] parentCellArr = grid.cellArrArr[y - 1];
            final Cell[] cellArr = grid.cellArrArr[y];
            for (int x = 0; x < grid.sizeX; x++) {
                final Cell calcCell = cellArr[x];
                // Nachbarn bestimmen (Randbehandlung: Wrap-Around)
                final int leftIndex = (x > 0) ? x - 1 : grid.sizeX - 1;
                final int rightIndex = (x < grid.sizeX - 1) ? x + 1 : 0;
                final int leftValue = parentCellArr[leftIndex].value;
                final int middleValue = parentCellArr[x].value;
                final int rightValue = parentCellArr[rightIndex].value;
                final int ruleNr = calcCell.ruleNr;

                calcCell.value = calcNewValue(leftValue, middleValue, rightValue, ruleNr);
            }
        }
    }

    public static int calcNewValue(final int leftValue, final int middleValue, final int rightValue, final int ruleNr) {
        // Nachbarschaft als Bitmuster
        final int pattern = (leftValue << 2) | (middleValue << 1) | rightValue;
        // Regel anwenden: das Bit an der Position 'pattern' gibt den neuen Wert
        return (ruleNr >> pattern) & 1;
    }

    public static void main(String[] args) {
        testCheckValueForAllCombinations();
    }

    private static void testCheckValueForAllCombinations() {
        for (int leftValue = 0; leftValue <= 1; leftValue++) {
            for (int middleValue = 0; middleValue <= 1; middleValue++) {
                for (int rightValue = 0; rightValue <= 1; rightValue++) {
                    System.out.printf("Left: %d, Middle: %d, Right: %d %n", leftValue, middleValue, rightValue);
                    for (int ruleNr = 0; ruleNr < GridService.RULE_COUNT; ruleNr++) {
                        final int newValue = calcNewValue(leftValue, middleValue, rightValue, ruleNr);
                        System.out.printf("Rule: %3d => New: %d; %n", ruleNr, newValue);
                    }
                    System.out.println();
                }
            }
        }
    }
}
