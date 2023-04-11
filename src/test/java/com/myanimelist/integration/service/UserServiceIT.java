package com.myanimelist.integration.service;

import com.myanimelist.entity.User;
import com.myanimelist.integration.IntegrationTestBase;
import com.myanimelist.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    public static final String USERNAME = "dummy";

    private final UserServiceImpl userService;

    @Test
    void findSuccess() {
        User user = userService.find(USERNAME);
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("dummy");
        assertThat(user.getEmail()).isEqualTo("dummy@gmail.com");
    }

    @Test
    void findSuccess2() {
        User user = userService.find(USERNAME);
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("dummy");
        assertThat(user.getEmail()).isEqualTo("dummy@gmail.com");
    }

    @Test
    void findFail() {
        assertAll(
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(null)),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find("non-existent")),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(""))
        );
    }
}
