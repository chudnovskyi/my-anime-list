package com.myanimelist.integration.service;

import com.myanimelist.entity.User;
import com.myanimelist.integration.annotation.IT;
import com.myanimelist.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class UserServiceIT {

    public static final String USERNAME = "bob";

    private final UserServiceImpl userService;

    @Test
    void findSuccess() {
        User actualResult = userService.find(USERNAME);

        System.out.println(actualResult.getPassword());

        assertNotNull(actualResult);
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
