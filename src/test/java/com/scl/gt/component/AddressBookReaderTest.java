package com.scl.gt.component;

import java.util.List;

import com.scl.gt.model.Address;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressBookReaderTest {

    private AddressBookProcessor addressBookProcessor = new AddressBookReader();

    @Test
    void shouldProcessCsv() {
        List<Address> addressList = addressBookProcessor.processAddress();
        assertThat(addressList).hasSize(5);
    }

}
