package org.example.eko.service.scanning;

public class ScanningRule {
    private int position;
    private int length;

    public ScanningRule(int pos, int length){
        this.position = pos;
        this.length = length;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
