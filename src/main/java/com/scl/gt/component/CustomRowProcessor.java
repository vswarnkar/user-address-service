package com.scl.gt.component;

import com.opencsv.processor.RowProcessor;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CustomRowProcessor implements RowProcessor {

    @Override
    public String processColumnItem(String column) {
        if (isBlank(column)) {
            return null;
        }
        return column.trim();
    }

    @Override
    public void processRow(String[] row) {
        for (int count = 0; count < row.length; count++) {
            row[count] = processColumnItem(row[count]);
        }
    }

}
