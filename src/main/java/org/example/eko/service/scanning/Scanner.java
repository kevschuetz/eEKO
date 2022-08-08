package org.example.eko.service.scanning;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class Scanner<T> {
    protected String getString(String data, ScanningRule rule)  {
        String subString = data.substring(rule.getPosition() - 1, rule.getPosition() + rule.getLength() - 1).trim();
        return StringUtils.isNotBlank(subString) ? subString : null;
    }

    public abstract List<T> scan(String data);
}
