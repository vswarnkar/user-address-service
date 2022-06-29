package com.scl.gt.component;

import com.opencsv.processor.RowProcessor;
import org.apache.commons.lang3.StringUtils;

public class CustomRowProcessor implements RowProcessor {

    @Override
    public String processColumnItem(String column) {
        if (StringUtils.isBlank(column)) {
            return null;
        }
        return column.trim();
    }

    @Override
    public void processRow(String[] row) {
        for (int i = 0; i < row.length; i++) {
            row[i] = processColumnItem(row[i]);
        }
    }

}
