package com.myanimelist.unit.service;

import com.myanimelist.config.JikanApiProperties;
import com.myanimelist.service.impl.JikanApiServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
public class JikanApiServiceTest {

    @Mock
    private WebClient webClient;
    @Mock
    private JikanApiProperties properties;

    @InjectMocks
    private JikanApiServiceImpl jikanApiService;

    // todo
}
