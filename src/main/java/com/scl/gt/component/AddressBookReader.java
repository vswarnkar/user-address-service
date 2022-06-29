package com.scl.gt.component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.scl.gt.model.Address;

public class AddressBookReader implements AddressBookProcessor {

    private static final String CSV_NAME = "address-book.csv";
    private static final char CSV_SEPARATOR = ',';

    @Override
    public List<Address> processAddress() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CSV_NAME);
        return new CsvToBeanBuilder(getCsvReader(inputStream))
                .withType(Address.class)
                .withIgnoreEmptyLine(true)
                .build()
                .parse();
    }

    private CSVReader getCsvReader(InputStream inputStream) {
        return new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .withCSVParser(getCsvParer())
                .withRowProcessor(new CustomRowProcessor())
                .build();
    }

    private CSVParser getCsvParer() {
        return new CSVParserBuilder()
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(CSV_SEPARATOR)
                .build();
    }

}
