package com.scl.gt.service;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.scl.gt.component.AddressBookProcessor;
import com.scl.gt.exception.NoUserAddressExist;
import com.scl.gt.exception.UnknownUserException;
import com.scl.gt.model.Address;
import com.scl.gt.model.Gender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@AllArgsConstructor
@Slf4j
public class UserAddressService {

    private static final String UNKNOWN_USER_ERROR_MESSAGE = "There is no user with name:%s";

    private final AddressBookProcessor addressBookProcessor;

    public long getMaleCount() {
        var userAddresses = getUserAddresses();

        if (isEmpty(userAddresses)) {
            return 0;
        }

        return userAddresses.stream()
                            .filter(address -> Gender.MALE.equals(address.getGender()))
                            .count();
    }

    public String findOldestPerson() {
        var userAddresses = getUserAddresses();

        if (isEmpty(userAddresses)) {
            log.info("There are no records for user");
            return null;
        }

        var sortedUserAddresses = userAddresses.stream()
                                               .sorted(Comparator.comparing(Address::getDateOfBirth))
                                               .collect(Collectors.toList());
        return sortedUserAddresses.get(0).getName();
    }

    public long getNumberOfDaysBetweenTwoUsersDateOfBirth(String firstPersonName, String secondPersonName) {
        var userAddresses = getUserAddresses();

        if (isNotEmpty(userAddresses)) {
            Optional<Address> firstContact = userAddresses.stream()
                                                          .filter(address -> address.getName().contains(firstPersonName))
                                                          .findAny();

            if (firstContact.isEmpty()) {
                log.error(format(UNKNOWN_USER_ERROR_MESSAGE, firstContact));
                throw new UnknownUserException(format(UNKNOWN_USER_ERROR_MESSAGE, firstContact));
            }

            Optional<Address> secondContact = userAddresses.stream()
                                                                   .filter(address -> address.getName().contains(secondPersonName))
                                                                   .findAny();

            if (secondContact.isEmpty()) {
                log.error(format(UNKNOWN_USER_ERROR_MESSAGE, secondPersonName));
                throw new UnknownUserException(format(UNKNOWN_USER_ERROR_MESSAGE, secondPersonName));
            }

            return ChronoUnit.DAYS.between(firstContact.get().getDateOfBirth(), secondContact.get().getDateOfBirth());
        }
        throw new NoUserAddressExist(format("No data available to find number of days difference between %s and %s", firstPersonName, secondPersonName));
    }

    private List<Address> getUserAddresses() {
        return addressBookProcessor.processAddress();
    }

}
