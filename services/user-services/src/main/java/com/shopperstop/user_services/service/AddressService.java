package com.shopperstop.user_services.service;

import com.shopperstop.user_services.entity.AddressDTO;
import com.shopperstop.user_services.entity.Addresses;
import com.shopperstop.user_services.entity.User;
import com.shopperstop.user_services.entity.UserDTO;
import com.shopperstop.user_services.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserServices userServices;

    public Addresses addAddress(AddressDTO addressDTO, String username) {
        User user = userServices.getUserByUsername(username);
        Addresses newAddress = new Addresses(addressDTO, user.getUser_id());
        Addresses saved = addressRepository.save(newAddress);
        UserDTO userDTO = new UserDTO();
        userDTO.getAddresses().add(saved);
        userServices.saveExistingUser(userDTO, username);
        return saved;
    }

    public boolean deleteAddress(String label, String username) {
        User user = userServices.getUserByUsername(username);
        List<Addresses> addresses = user.getAddresses();

        Addresses matching = addresses.stream()
                .filter(a -> a.getLabel().equals(label))
                .findFirst()
                .orElse(null);

        if (matching == null) return false;
        user.getAddresses().remove(matching);
        userServices.saveExistingUser1(user);
        addressRepository.delete(matching);
        return true;
    }

    public Addresses changeAddress(String label, String username, AddressDTO updatedAddress) {
        User user = userServices.getUserByUsername(username);
        List<Addresses> addresses = user.getAddresses();

        Addresses matching = addresses.stream()
                .filter(a -> a.getLabel().equals(label))
                .findFirst()
                .orElse(null);

        if (matching == null) return null;

        if ((updatedAddress.getLabel()) != null) matching.setLabel(updatedAddress.getLabel());
        matching.setLine1(updatedAddress.getLine1());
        matching.setLine2(updatedAddress.getLine2());
        matching.setCity(updatedAddress.getCity());
        matching.setState(updatedAddress.getState());
        matching.setPostalCode(updatedAddress.getPostalCode());
        matching.setCountry(updatedAddress.getCountry());
        matching.setDefaultAddress(updatedAddress.isDefaultAddress());


        Addresses updated = addressRepository.save(matching);
        return updated;
    }

    public Addresses getByLabel(String label, String username){
        User user = userServices.getUserByUsername(username);
        List<Addresses> addressesList = user.getAddresses();

        for(Addresses temp : addressesList){
            if(Objects.equals(temp.getLabel(), label)){
                return temp;
            }
        }

        return null;
    }
}
