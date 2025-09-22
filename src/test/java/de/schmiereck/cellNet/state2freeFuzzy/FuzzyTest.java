package de.schmiereck.cellNet.state2freeFuzzy;

import de.schmiereck.cellNet.state2free.CellNetService;
import org.junit.jupiter.api.Test;

public class FuzzyTest {
    @Test
    public void testBinary() {
        final int ruleNr = 0b101; // Regel 5: 00->1, 01->0, 10->1, 11->0

        final int inputValueArrArr[][] = {
                { 0, 0 }, // 0b00
                { 0, 1 }, // 0b01
                { 1, 0 }, // 0b10
                { 1, 1 }  // 0b11
        };
        final int outputValueArr[] = { 1, 0, 1, 0 };

        for (int inputNr = 0; inputNr < inputValueArrArr.length; inputNr++) {
            final int leftValue = inputValueArrArr[inputNr][0];
            final int rightValue = inputValueArrArr[inputNr][1];
            final int value = calcNewValue(leftValue, rightValue, ruleNr);
            final int expectedOutputValue = outputValueArr[inputNr];

            assert value == expectedOutputValue : "inputNr: " + inputNr + ", expectedValue: " + expectedOutputValue + ", value: " + value;
        }
    }

    @Test
    public void testFuzzyRuleNr5() {
        final int maxValue = 7; // 3 Bit -> 0..7
        // Regel 5 - sie produziert asymmetrische Ausgaben für symmetrische Eingaben.
        // Regel 5: 00->1, 01->0, 10->1, 11->0
        final int ruleNr = 0b101;
        // Meine Beobachtung ist, dass
        // Input 11 { 7, 7 } => 0 ergibt,
        // Input 15 { 7, 5 } => 2 ergibt
        // Input 19 { 5, 7 } => 0 ergibt.
        // Das erscheint aus intuitiver Sicht unlogisch.
        //
         // Die Ergebnisse erscheinen zunächst unintuitiv, sind aber mathematisch korrekt für die Fuzzy-Logik-Implementierung.
        // Erwartete Ergebnisse mit dieser Methode:
        // { 7, 7 } → 100% Pattern 11, Output 0 → 0
        // { 7, 5 } → ~71% Pattern 11 (→0),
        //            ~29% Pattern 10 (→1) → ~0.29 → 2
        // { 5, 7 } → ~71% Pattern 11 (→0),
        //            ~29% Pattern 01 (→0) → 0

        final int inputValueArrArr[][] = {
                // A. (0 - 3)
                { 0, 0 }, // 0b00
                { 0, 1 }, // 0b01
                { 1, 0 }, // 0b10
                { 1, 1 }, // 0b11
                // B. (4 - 7)
                { 0, 0 }, // 0b00
                { 0, 2 }, // 0b01
                { 2, 0 }, // 0b10
                { 2, 2 }, // 0b11
                // C. (8 - 11)
                { 0, 0 }, // 0b00
                { 0, 7 }, // 0b01
                { 7, 0 }, // 0b10
                { 7, 7 }, // 0b11
                // D. (12 - 15)
                { 0, 0 }, // 0b00
                { 0, 5 }, // 0b01
                { 5, 0 }, // 0b10
                { 7, 5 }, // 0b11
                // E. (16 - 19)
                { 2, 2 }, // 0b00
                { 2, 7 }, // 0b01
                { 7, 2 }, // 0b10
                { 5, 7 }, // 0b11
        };
        final int outputValueArr[] = {
                // A. (0 - 3)
                7, // 00->1
                6, // 01->0
                7, // 10->1
                6, // 11->0
                // B. (4 - 7)
                7, // 00->1
                5, // 01->0
                7, // 10->1
                5, // 11->0
                // C. (8 - 11)
                7, // 00->1
                0, // 01->0
                7, // 10->1
                0, // 11->0 { 7, 7 }
                // D. (12 - 15)
                7, // 00->1
                2, // 01->0
                7, // 10->1
                2, // 11->0 { 7, 5 }
                // E. (16 - 19)
                5, // 00->1
                0, // 01->0
                5, // 10->1
                0, // 11->0 { 5, 7 }
        };

        assertOutputValues(ruleNr, maxValue, inputValueArrArr, outputValueArr);
    }

    @Test
    public void testFuzzyRuleNr6() {
        // Regel 6 (0b0110) oder Regel 9 (0b1001):
        // Regel 6 (XOR): 00->0, 01->1, 10->1, 11->0
        // Regel 9 (XNOR): 00->1, 01->0, 10->0, 11->1

        final int maxValue = 7; // 3 Bit -> 0..7
        // Regel 6 - Diese liefern für {7,5} und {5,7} identische Ergebnisse, da beide Eingaben die gleiche "Verschiedenheit" repräsentieren.
        // Regel 6: (XOR): 00->0, 01->1, 10->1, 11->0
        final int ruleNr = 0b110;

        final int inputValueArrArr[][] = {
                // A. (0 - 3)
                { 0, 0 }, // 0b00
                { 0, 1 }, // 0b01
                { 1, 0 }, // 0b10
                { 1, 1 }, // 0b11
                // B. (4 - 7)
                { 0, 0 }, // 0b00
                { 0, 2 }, // 0b01
                { 2, 0 }, // 0b10
                { 2, 2 }, // 0b11
                // C. (8 - 11)
                { 0, 0 }, // 0b00
                { 0, 7 }, // 0b01
                { 7, 0 }, // 0b10
                { 7, 7 }, // 0b11
                // D. (12 - 15)
                { 0, 0 }, // 0b00
                { 0, 5 }, // 0b01
                { 5, 0 }, // 0b10
                { 7, 5 }, // 0b11
                // E. (16 - 19)
                { 2, 2 }, // 0b00
                { 2, 7 }, // 0b01
                { 7, 2 }, // 0b10
                { 5, 7 }, // 0b11
        };
        final int outputValueArr[] = { // Regel 6: (XOR): 00->0, 01->1, 10->1, 11->0
                // A. (0 - 3)
                0, // 00->0
                1, // 01->1
                1, // 10->1
                2, // 11->0
                // B. (4 - 7)
                0, // 00->0
                2, // 01->1
                2, // 10->1
                3, // 11->0
                // C. (8 - 11)
                0, // 00->0
                7, // 01->1
                7, // 10->1
                0, // 11->0 { 7, 7 }
                // D. (12 - 15)
                0, // 00->0
                5, // 01->1
                5, // 10->1
                2, // 11->0 { 7, 5 }
                // E. (16 - 19)
                3, // 00->0
                5, // 01->1
                5, // 10->1
                2, // 11->0 { 5, 7 }
        };

        assertOutputValues(ruleNr, maxValue, inputValueArrArr, outputValueArr);
    }

    private static void assertOutputValues(int ruleNr, int maxValue, int[][] inputValueArrArr, int[] outputValueArr) {
        for (int inputNr = 0; inputNr < inputValueArrArr.length; inputNr++) {
            final int leftValue = inputValueArrArr[inputNr][0];
                        final int rightValue = inputValueArrArr[inputNr][1];
            // Of the three available fuzzy calculation methods (V1, Weighted, Interpolated), we use calcNewFuzzyValue_V1 here
            // because it provides the baseline implementation and matches the expected output for these tests.
            final int value = calcNewFuzzyValue_V1(leftValue, rightValue, ruleNr, maxValue);
            //final int value = calcNewFuzzyValue_Weighted(leftValue, rightValue, ruleNr, maxValue);
            //final int value = calcNewFuzzyValue_Interpolated(leftValue, rightValue, ruleNr, maxValue);
            final int expectedOutputValue = outputValueArr[inputNr];

            assert value == expectedOutputValue : "inputNr: " + inputNr + ", expectedValue: " + expectedOutputValue + ", value: " + value;
        }
    }

    static int calcNewFuzzyValue_Interpolated(final int leftValue, final int rightValue, final int ruleNr, final int maxValue) {
        final double leftNorm = (double) leftValue / maxValue;
        final double rightNorm = (double) rightValue / maxValue;

        // Gewichtungen für jedes Pattern
        final double weight00 = (1.0 - leftNorm) * (1.0 - rightNorm);
        final double weight01 = (1.0 - leftNorm) * rightNorm;
        final double weight10 = leftNorm * (1.0 - rightNorm);
        final double weight11 = leftNorm * rightNorm;

        // Regel-Ausgaben als Zielwerte (nicht Multiplikatoren)
        final int output00 = (ruleNr >> 0) & 1;
        final int output01 = (ruleNr >> 1) & 1;
        final int output10 = (ruleNr >> 2) & 1;
        final int output11 = (ruleNr >> 3) & 1;

        // Gewichtete Interpolation zwischen den Zielwerten
        final double interpolatedResult =
                output00 * weight00 +
                        output01 * weight01 +
                        output10 * weight10 +
                        output11 * weight11;

        return (int) Math.round(interpolatedResult * maxValue);
    }

    // Alternative 1: Gewichtete Durchschnittsbildung
    static int calcNewFuzzyValue_Weighted(final int leftValue, final int rightValue, final int ruleNr, final int maxValue) {
        final double leftNorm = (double) leftValue / maxValue;
        final double rightNorm = (double) rightValue / maxValue;

        // Gewichtungen für jedes Pattern
        final double weight00 = (1.0 - leftNorm) * (1.0 - rightNorm);
        final double weight01 = (1.0 - leftNorm) * rightNorm;
        final double weight10 = leftNorm * (1.0 - rightNorm);
        final double weight11 = leftNorm * rightNorm;

        // Gewichteter Durchschnitt der Regel-Ausgaben
        final double weightedSum =
                ((ruleNr >> 0) & 1) * weight00 +
                        ((ruleNr >> 1) & 1) * weight01 +
                        ((ruleNr >> 2) & 1) * weight10 +
                        ((ruleNr >> 3) & 1) * weight11;

        final double totalWeight = weight00 + weight01 + weight10 + weight11;

        return (int) Math.round((weightedSum / totalWeight) * maxValue);
    }

    static int calcNewFuzzyValue_V1(final int leftValue, final int rightValue, final int ruleNr, final int maxValue) {
        // Normalisierung auf [0,1] Bereich
        final double leftNorm = (double) leftValue / maxValue;
        final double rightNorm = (double) rightValue / maxValue;

        // Fuzzy-Operationen für alle 4 möglichen Nachbarschaftsmuster
        final double result00 = ((ruleNr >> 0) & 1) * (1.0 - leftNorm) * (1.0 - rightNorm); // 00
        final double result01 = ((ruleNr >> 1) & 1) * (1.0 - leftNorm) * rightNorm;         // 01
        final double result10 = ((ruleNr >> 2) & 1) * leftNorm * (1.0 - rightNorm);         // 10
        final double result11 = ((ruleNr >> 3) & 1) * leftNorm * rightNorm;                 // 11

        // Summe aller gewichteten Ergebnisse
        final double fuzzyResult = result00 + result01 + result10 + result11;

        // Rückskalierung auf [0, maxValue]
        return (int) Math.round(fuzzyResult * maxValue);
    }

    /**
     * @see CellNetService#calcNewValue(int, int, int)
     */
    static int calcNewValue(final int leftValue, final int rightValue, final int ruleNr) {
        // Nachbarschaft als Bitmuster
        final int pattern = (leftValue << 1) | rightValue;
        // Regel anwenden: das Bit an der Position 'pattern' gibt den neuen Wert
        return (ruleNr >> pattern) & 1;
    }
}
