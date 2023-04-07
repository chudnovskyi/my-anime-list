package com.myanimelist.service.impl;

import com.myanimelist.service.UUIDService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDServiceImpl implements UUIDService{

    public String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
