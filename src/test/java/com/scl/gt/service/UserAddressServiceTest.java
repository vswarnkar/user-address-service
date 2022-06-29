package com.scl.gt.service;

import java.time.LocalDate;
import java.util.List;

import com.scl.gt.component.AddressBookProcessor;
import com.scl.gt.model.Address;
import com.scl.gt.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.scl.gt.model.Gender.FEMALE;
import static com.scl.gt.model.Gender.MALE;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserAddressServiceTest {

    @Mock
    private AddressBookProcessor addressBookProcessor;

    private UserAddressService userAddressService;

    @BeforeEach
    void setup() {
        userAddressService = new UserAddressService(addressBookProcessor);
    }

    @Test
    void shouldGetMatchingMaleCount() {
        given(addressBookProcessor.processAddress()).willReturn(buildAddresses());

        var maleCount = userAddressService.getMaleCount();

        assertThat(maleCount).isEqualTo(2);
        then(addressBookProcessor).should().processAddress();
    }

    @Test
    void shouldReturnZeroValueWhenNoAddressAvailable() {
        given(addressBookProcessor.processAddress()).willReturn(emptyList());

        var maleCount = userAddressService.getMaleCount();

        assertThat(maleCount).isEqualTo(0);
        then(addressBookProcessor).should().processAddress();
    }

    private List<Address> buildAddresses() {
        return List.of(buildAddress("User 1", MALE, "1991-01-11"),
                       buildAddress("User 2", FEMALE, "1992-01-11"),
                       buildAddress("User 3", MALE, "1998-05-25"));
    }

    private Address buildAddress(String userName, Gender gender, String dateOfBirth) {
        return Address.builder()
                      .name(userName)
                      .gender(gender)
                      .dateOfBirth(LocalDate.parse(dateOfBirth))
                      .build();
    }

}
