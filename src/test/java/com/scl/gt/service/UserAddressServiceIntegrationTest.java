package com.scl.gt.service;

import java.util.stream.Stream;

import com.scl.gt.component.AddressBookProcessor;
import com.scl.gt.component.AddressBookReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest
    @MethodSource("scenarioToGetNumberOfDaysBetweenTwoUsersDateOfBirthIncludingIgnoreCase")
    void shouldGetNumberOfDaysBetweenTwoUsersDateOfBirth(String user1, String user2) {
        var daysBetweenTwoUsersDateOfBirth = userAddressService.getNumberOfDaysBetweenTwoUsersDateOfBirth(user1, user2);
        assertThat(daysBetweenTwoUsersDateOfBirth).isEqualTo(2862);
    }

    private static Stream<Arguments> scenarioToGetNumberOfDaysBetweenTwoUsersDateOfBirthIncludingIgnoreCase() {
        return Stream.of(Arguments.of("Bill McKnight", "Paul Robinson"),
                         Arguments.of("BiLL McKnight", "PAUL Robinson"),
                         Arguments.of("BILL McKNIGHT", "Paul ROBINSON"));
    }

}
