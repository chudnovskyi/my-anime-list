package com.myanimelist.service.impl;

import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.repository.RoleRepository;
import com.myanimelist.repository.UserRepository;
import com.myanimelist.security.UserPrincipal;
import com.myanimelist.service.UUIDService;
import com.myanimelist.service.UserService;
import com.myanimelist.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailSenderServiceImpl mailSenderService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UUIDService uuidService;

    @Override
    public User find(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new EntityNotFoundException("User with username " + username + " does not exist"));
    }

    @Override
    public void save(UserView userView) {
        User user = User.builder()
                .username(userView.getUsername())
                .password(passwordEncoder.encode(userView.getPassword()))
                .email(userView.getEmail())
                .roles(List.of(roleRepository.findByName("ROLE_USER").orElseThrow()))
                .activationCode(uuidService.generateRandomUUID())
                .build();

        try {
            userRepository.save(user);
        } catch (RuntimeException e) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists!");
        }

        mailSenderService.send(user.getUsername(), user.getEmail(), user.getActivationCode());
    }

    @Override
    public boolean activateUser(String code) {
        Optional<User> optionalUser = userRepository.findByActivationCode(code);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void uploadProfilePicture(byte[] bytes) {
        userRepository.findByUsername(getAuthUsername())
                .ifPresent(user -> user.setImage(bytes));
    }

    @Override
    public byte[] getProfilePicture() {
        Optional<User> optionalUser = userRepository.findByUsername(getAuthUsername());

        User user = optionalUser.orElseThrow(
                () -> new EntityNotFoundException("User with username " + getAuthUsername() + " does not exist")
        );

        return user.getImage();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserPrincipal(user);
    }

    private String getAuthUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
