package com.myanimelist.controller;

import com.myanimelist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;

    @PostMapping("/save-image")
    public String saveImage(
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        userService.uploadProfilePicture(multipartFile.getBytes());

        return "redirect:/home?profile";
    }
}
