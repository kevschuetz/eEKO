package org.example.eko.service.scanning;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class Scanner<T> {
    public String getString(String entry, ScanningRule rule)  {
        String subString = entry.substring(rule.getPosition() - 1, rule.getPosition() + rule.getLength() - 1).trim();
        return StringUtils.isNotBlank(subString) ? subString : null;
    }

    protected abstract List<T> scan(String data);
}
