package com.shopperstop.user_services.service;

import com.shopperstop.user_services.entity.AddressDTO;
import com.shopperstop.user_services.entity.User;
import com.shopperstop.user_services.entity.UserDTO;
import com.shopperstop.user_services.entity.UserRole;
import com.shopperstop.user_services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User addNewUser(User user){
        try {
            user.setCreatedAt(new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole(UserRole.CUSTOMER);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the user");
        }

    }
    public User saveExistingUser(UserDTO userDTO, String username){
        User user = userRepository.findByUsername(username);
        if(userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if(userDTO.getPassword() != null) user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if(userDTO.getEmailID() != null) user.setEmailID(userDTO.getEmailID());
        if(!userDTO.getAddresses().isEmpty()){
            user.setAddresses(Stream.concat(user.getAddresses().stream(), userDTO.getAddresses().stream()).distinct().collect(Collectors.toList()));
        }
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public User saveExistingUser1(User user){
        return userRepository.save(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);

    }

    public boolean deleteUserByUsername(String username) {
        User userToBeDeleted = userRepository.findByUsername(username);
        if (userToBeDeleted != null) {
            userRepository.deleteById(userToBeDeleted.getUser_id());
            return true;
        }
        return false;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addNewAdmin(User user){
        try {
            user.setCreatedAt(new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the user");
        }

    }

    private UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getUser_id());
        dto.setUsername(user.getUsername());
        dto.setRole(String.valueOf(user.getUserRole()));
        return dto;
    }

    public UserDTO getByUsername(String username){
        User userOpt = getUserByUsername(username);
        return toDTO(userOpt);
    }

}
