package com.myanimelist.integration.service;

import com.myanimelist.entity.User;
import com.myanimelist.integration.IntegrationTestBase;
import com.myanimelist.integration.annotation.IT;
import com.myanimelist.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    public static final String USERNAME = "admin";

    private final UserServiceImpl userService;

    @Test
    void findSuccess() {
        User user = userService.find(USERNAME);
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("admin");
        assertThat(user.getEmail()).isEqualTo("oldman@gmail.com");
    }

    @Test
    void findFail() {
        assertAll(
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(null)),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find("dummy")),
                () -> assertThrows(EntityNotFoundException.class, () -> userService.find(""))
        );
    }
}
