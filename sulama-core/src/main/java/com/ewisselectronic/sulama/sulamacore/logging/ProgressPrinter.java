package com.ewisselectronic.sulama.sulamacore.logging;

public class ProgressPrinter {
    final int intermediateCount;
    final int newLineCount;
    private long counter = 0;

    public ProgressPrinter(int intermediateCount, int newLineCount) {
        this.intermediateCount = intermediateCount;
        this.newLineCount = newLineCount;
    }

    public void print() {
        if (counter++ % intermediateCount == 0) {
            System.out.print(".");

        }
        if (counter % newLineCount == 0) {
            System.out.println(counter);
        }
    }

    public void reset() {
        counter = 0;
    }
}
