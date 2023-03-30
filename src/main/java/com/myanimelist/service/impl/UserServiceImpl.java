package com.myanimelist.service.impl;

import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.repository.RoleRepository;
import com.myanimelist.repository.UserRepository;
import com.myanimelist.security.UserPrincipal;
import com.myanimelist.service.UserService;
import com.myanimelist.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailSenderServiceImpl mailSenderService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;

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
                .activationCode(UUID.randomUUID().toString())
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists!");
        }

        String message = """
                Hello, %s!
                Welcome to MyAnimeList. Please, follow link to verify your account:
                %s/register/activate/%s,
                """.formatted(user.getUsername(), env.getProperty("host.domain"), user.getActivationCode());

        mailSenderService.send(user.getEmail(), "Activation code", message);
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
