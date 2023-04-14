package com.myanimelist.integration.service;

import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.integration.IntegrationTestBase;
import com.myanimelist.service.impl.UserServiceImpl;
import com.myanimelist.view.UserView;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    public static final String USERNAME = "dummy";
    public static final String ACTIVATION_CODE = "code";

    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Test
    void findSuccess() {
        User result = userService.find(USERNAME);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("dummy");
        assertThat(result.getEmail()).isEqualTo("dummy@gmail.com");
    }

    @Test
    void findFail() {
        assertAll(
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(null)),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find("non-existent")),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(""))
        );
    }

    @Test
    @WithMockUser
    void saveSuccess() {
        UserView userView = new UserView(
                "test",
                "test",
                "test",
                "test@gmail.com"
        );

        userService.save(userView);
        User actualResult = userService.find("test");

        assertThat(actualResult.getUsername()).isEqualTo(userView.getUsername());
        assertThat(passwordEncoder.matches(userView.getPassword(), actualResult.getPassword())).isTrue();
        assertThat(actualResult.getEmail()).isEqualTo(userView.getEmail());
    }

    @Test
    @WithMockUser
    void saveFail() {
        UserView userView = new UserView(
                USERNAME,
                "test",
                "test",
                "test@gmail.com"
        );

        assertThatThrownBy(() -> userService.save(userView))
                .isInstanceOf(UsernameAlreadyExistsException.class);
    }

    @Test
    @WithMockUser
    void activateUserSuccess() {
        boolean result = userService.activateUser(ACTIVATION_CODE);

        assertThat(result).isTrue();
        assertThat(userService.find(USERNAME).getActivationCode()).isNull();
    }

    @Test
    void activateUserFail() {
        boolean result = userService.activateUser("dummy_code");

        assertThat(result).isFalse();
        assertThat(userService.find(USERNAME).getActivationCode()).isNotNull();
    }

    @Test
    @WithMockUser(username = USERNAME)
    void uploadProfilePicture() {
        byte[] bytes = {1, 2, 3};

        userService.uploadProfilePicture(bytes);

        assertThat(userService.getProfilePicture()).isEqualTo(bytes);
    }
}
