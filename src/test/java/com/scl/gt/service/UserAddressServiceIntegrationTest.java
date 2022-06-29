package com.scl.gt.service;

import com.scl.gt.component.AddressBookProcessor;
import com.scl.gt.component.AddressBookReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserAddressServiceIntegrationTest {

    private UserAddressService userAddressService;

    private AddressBookProcessor addressBookProcessor = new AddressBookReader();

    @BeforeEach
    void setup() {
        userAddressService = new UserAddressService(addressBookProcessor);
    }

    @Test
    void shouldGetMatchingMaleCount() {
        var maleCount = userAddressService.getMaleCount();
        assertThat(maleCount).isEqualTo(3);
    }

    @Test
    void shouldFindOldestPerson() {
        var oldestPersonName = userAddressService.findOldestPerson();
        assertThat(oldestPersonName).isEqualTo("Wes Jackson");
    }

    @Test
    void shouldGetNumberOfDaysBetweenTwoUsersDateOfBirth() {
        var daysBetweenTwoUsersDateOfBirth = userAddressService.getNumberOfDaysBetweenTwoUsersDateOfBirth("Bill", "Paul");
        assertThat(daysBetweenTwoUsersDateOfBirth).isEqualTo(2862);
    }

}
