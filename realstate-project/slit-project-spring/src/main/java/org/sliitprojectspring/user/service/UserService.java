package org.sliitprojectspring.user.service;

import org.springframework.stereotype.Service;
import org.sliitprojectspring.user.dto.UserCreatDto;
import org.sliitprojectspring.user.dto.UserResponseDto;
import org.sliitprojectspring.user.model.User;
import org.sliitprojectspring.user.repository.UserRepository;
import org.sliitprojectspring.common.utils.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username is invalid"));

        if (user != null && PasswordEncoder.checkPassword(password, user.getPassword())) {
            return new UserResponseDto(user.getId(), user.getName(),
                    user.getRole(),
                    user.getEmail(),
                    user.getPhoneNumber());
        }
        return null;
    }

    public UserResponseDto register(UserCreatDto userCreatDto) {
        User user = new User();
        user.setName(userCreatDto.name());
        user.setUsername(userCreatDto.username());
        user.setEmail(userCreatDto.email());
        user.setPhoneNumber(userCreatDto.phoneNumber());
        user.setRole(userCreatDto.role());
        user.setPassword(PasswordEncoder.hashPassword(userCreatDto.password()));

        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getRole(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber()
        );
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user.getId(), user.getName(), user.getRole(), user.getEmail(), user.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getUsersByRole(String role) {
        return userRepository.findByRole(role).stream()
                .map(user -> new UserResponseDto(user.getId(), user.getName(), user.getRole(), user.getEmail(), user.getPhoneNumber()))
                .collect(Collectors.toList());
    }
}
