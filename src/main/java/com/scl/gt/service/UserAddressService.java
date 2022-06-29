package com.scl.gt.service;

import java.util.List;

import com.scl.gt.component.AddressBookProcessor;
import com.scl.gt.model.Address;
import com.scl.gt.model.Gender;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@AllArgsConstructor
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

}
