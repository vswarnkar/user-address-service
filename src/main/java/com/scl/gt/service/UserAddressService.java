package com.scl.gt.service;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.scl.gt.component.AddressBookProcessor;
import com.scl.gt.model.Address;
import com.scl.gt.model.Gender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@AllArgsConstructor
@Slf4j
public class UserAddressService {

    private final AddressBookProcessor addressBookProcessor;

    public long getMaleCount() {
        var userAddresses = addressBookProcessor.processAddress();

        if (isEmpty(userAddresses)) {
            return 0;
        }

        return userAddresses.stream()
                            .filter(address -> Gender.MALE.equals(address.getGender()))
                            .count();
    }

    public String findOldestPerson() {
        var userAddresses = addressBookProcessor.processAddress();

        if (isEmpty(userAddresses)) {
            log.info("There are no records for user");
            return null;
        }

        var sortedUserAddresses = userAddresses.stream()
                                               .sorted(Comparator.comparing(Address::getDateOfBirth))
                                               .collect(Collectors.toList());
        return sortedUserAddresses.get(0).getName();
    }

}
