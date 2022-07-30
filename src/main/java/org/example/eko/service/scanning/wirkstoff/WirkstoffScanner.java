package org.example.eko.service.scanning.wirkstoff;

import org.example.eko.model.entities.WirkstoffAtcCode;
import org.example.eko.service.scanning.Scanner;
import org.example.eko.service.scanning.ScanningRule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WirkstoffScanner extends Scanner<WirkstoffAtcCode> {
    private static final ScanningRule code = new ScanningRule(1, 7);
    private static final ScanningRule text = new ScanningRule(8, 200);


    @Override
    public List<WirkstoffAtcCode> scan(String data) {
        List<WirkstoffAtcCode> wirkstoffe = new ArrayList<>();
        var entries = data.split("\n");
        for(String entry : entries){
            wirkstoffe.add(scanWirkstoff(entry));
        }

        return  wirkstoffe;
    }

    private WirkstoffAtcCode scanWirkstoff(String entry) {
        String code = getString(entry, WirkstoffScanner.code);
        String text = getString(entry, WirkstoffScanner.text);
        WirkstoffAtcCode wirkstoff = new WirkstoffAtcCode();
        wirkstoff.setAtcCode(code);
        wirkstoff.setText(text);
        return wirkstoff;
    }
}
