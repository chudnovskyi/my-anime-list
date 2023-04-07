package com.myanimelist.unit.service;

import com.myanimelist.entity.Role;
import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.repository.RoleRepository;
import com.myanimelist.repository.UserRepository;
import com.myanimelist.security.UserPrincipal;
import com.myanimelist.service.UUIDService;
import com.myanimelist.service.impl.MailSenderServiceImpl;
import com.myanimelist.service.impl.UserServiceImpl;
import com.myanimelist.view.UserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private MailSenderServiceImpl mailSenderService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private UUIDService uuidService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = getUser();
    }

    @Test
    void findSuccess() {
        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

        User actualResult = userService.find(user.getUsername());

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(user);
    }

    @Test
    void findFail() {
        assertAll(
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(null)),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find("dummy")),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(""))
        );
    }

    @Test
    void saveSuccess() {
        UserView userView = getValidUser();

        when(uuidService.generateRandomUUID()).thenReturn("00000000-0000-0000-0000-000000000000");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(getRole()));
        when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword() + "_encoded");

        userService.save(userView);

        User expectedUser = User.builder()
                .username(userView.getUsername())
                .password(passwordEncoder.encode(userView.getPassword()))
                .email(userView.getEmail())
                .roles(List.of(roleRepository.findByName("ROLE_USER").orElseThrow()))
                .activationCode("00000000-0000-0000-0000-000000000000")
                .build();

        verify(mailSenderService).send(expectedUser.getUsername(), expectedUser.getEmail(), "00000000-0000-0000-0000-000000000000");
        verify(userRepository).save(expectedUser);
        verify(roleRepository, times(2)).findByName("ROLE_USER");
        verify(passwordEncoder, times(2)).encode(userView.getPassword());
    }

    @Test
    void saveFail() {
        UserView userView = getValidUser();

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(getRole()));
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Username already exists"));

        var exception = assertThrows(UsernameAlreadyExistsException.class, () -> userService.save(userView));
        String expectedMessage = String.format("Username %s already exists!", user.getUsername());
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void activateUserSuccess() {
        doReturn(Optional.of(user)).when(userRepository).findByActivationCode(user.getActivationCode());

        assertThat(userService.activateUser(user.getActivationCode())).isTrue();
    }

    @Test
    void activateUserFail() {
        doReturn(Optional.empty()).when(userRepository).findByActivationCode("dummy");

        assertThat(userService.activateUser("dummy")).isFalse();
    }

    @Test
    void testUploadProfilePicture() {
        authenticate();

        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

        byte[] bytes = {1, 2, 3, 4, 5};

        userService.uploadProfilePicture(bytes);

        assertThat(user.getImage()).isEqualTo(bytes);

        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void getProfilePictureSuccess() {
        authenticate();

        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

        assertThat(userService).returns(user.getImage(), UserServiceImpl::getProfilePicture);
    }

    @Test
    void getProfilePictureFail() {
        authenticate();

        doReturn(Optional.empty()).when(userRepository).findByUsername(user.getUsername());

        assertThrows(EntityNotFoundException.class, () -> userService.getProfilePicture());
    }

    @Test
    void loadUserByUsernameSuccess() {
        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

        UserDetails result = userService.loadUserByUsername(user.getUsername());

        assertThat(result).isEqualTo(new UserPrincipal(user));
    }

    @Test
    void loadUserByUsernameFail() {
        doReturn(Optional.empty()).when(userRepository).findByUsername(anyString());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("dummy"));
    }

    private void authenticate() {
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private UserView getValidUser() {
        UserView userView = new UserView();
        userView.setUsername(user.getUsername());
        userView.setPassword(user.getPassword());
        userView.setEmail(user.getEmail());
        return userView;
    }

    public User getUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("dummy");
        user.setEmail("user@gmail.com");
        user.setPassword("$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC");
        user.setActivationCode("activate");
        user.setReviews(Collections.emptyList());
        user.setRoles(List.of(getRole()));
        return user;
    }

    private Role getRole() {
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
        return role;
    }
}
