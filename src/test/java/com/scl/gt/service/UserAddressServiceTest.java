package com.scl.gt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import com.scl.gt.component.AddressBookProcessor;
import com.scl.gt.exception.NoUserAddressExist;
import com.scl.gt.exception.UnknownUserException;
import com.scl.gt.model.Address;
import com.scl.gt.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.scl.gt.model.Gender.FEMALE;
import static com.scl.gt.model.Gender.MALE;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserAddressServiceTest {

    private static final String USER_1 = "User_1";
    private static final String USER_2 = "User_2";
    private static final String USER_3 = "User_3";

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

    @Test
    void shouldFindOldestPerson() {
        given(addressBookProcessor.processAddress()).willReturn(buildAddresses());
        var oldestPersonName = userAddressService.findOldestPerson();

        assertThat(oldestPersonName).isEqualTo(USER_2);
        then(addressBookProcessor).should().processAddress();
    }

    @Test
    void shouldReturnNullValueWhenNoAddressAvailable() {
        given(addressBookProcessor.processAddress()).willReturn(emptyList());
        var oldestPersonName = userAddressService.findOldestPerson();

        assertThat(oldestPersonName).isNull();
        then(addressBookProcessor).should().processAddress();
    }

    @ParameterizedTest
    @MethodSource("scenarioToGetNumberOfDaysBetweenTwoUsersDateOfBirth")
    void shouldGetNumberOfDaysBetweenTwoUsersDateOfBirth(String user1, String user2, long expectedDays) {
        given(addressBookProcessor.processAddress()).willReturn(buildAddresses());

        var daysBetweenTwoUsersDateOfBirth = userAddressService.getNumberOfDaysBetweenTwoUsersDateOfBirth(user1, user2);

        assertThat(daysBetweenTwoUsersDateOfBirth).isEqualTo(expectedDays);
        then(addressBookProcessor).should().processAddress();
    }

    @ParameterizedTest
    @MethodSource("scenarioForUnknownUserException")
    void shouldGetExceptionWhenUserIsNotAvailable(String user1, String user2) {
        given(addressBookProcessor.processAddress()).willReturn(buildAddresses());

        assertThatThrownBy(() ->userAddressService.getNumberOfDaysBetweenTwoUsersDateOfBirth(user1, user2)).isInstanceOf(UnknownUserException.class);
    }

    @Test
    void shouldGetNoUserAddressExistWhenNoAddressAvailable() {
        given(addressBookProcessor.processAddress()).willReturn(emptyList());

        assertThatThrownBy(() ->userAddressService.getNumberOfDaysBetweenTwoUsersDateOfBirth("user1", "user2")).isInstanceOf(NoUserAddressExist.class)
                .hasMessage("No user data available to find number of days difference for %s and %s", "user1", "user2");
    }

    private List<Address> buildAddresses() {
        return List.of(buildAddress(USER_1, MALE, "1992-01-11"),
                       buildAddress(USER_2, FEMALE, "1991-01-11"),
                       buildAddress(USER_3, MALE, "1998-05-25"));
    }

    private Address buildAddress(String userName, Gender gender, String dateOfBirth) {
        return Address.builder()
                      .name(userName)
                      .gender(gender)
                      .dateOfBirth(LocalDate.parse(dateOfBirth))
                      .build();
    }

    private static Stream<Arguments> scenarioToGetNumberOfDaysBetweenTwoUsersDateOfBirth() {
        return Stream.of(Arguments.of(USER_1, USER_2, -365),
                         Arguments.of(USER_2, USER_1, 365),
                         Arguments.of(USER_1, USER_3, 2326));
    }

    private static Stream<Arguments> scenarioForUnknownUserException() {
        return Stream.of(Arguments.of(USER_1, "Test"),
                         Arguments.of("Test", USER_1));
    }

}
