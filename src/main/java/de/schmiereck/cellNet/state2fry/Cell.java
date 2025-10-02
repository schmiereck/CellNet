package de.schmiereck.cellNet.state2fry;

public class Cell {
    public static final int RIGHT_OFF_X = 1;

    public int leftPosX = 0;
    public int rightPosX = RIGHT_OFF_X;
    private int ruleNr;
    public int value;

    public int getRuleNr() {
        return ruleNr;
    }

    public void setRuleNr(int ruleNr) {
        this.ruleNr = ruleNr;
    }
}
