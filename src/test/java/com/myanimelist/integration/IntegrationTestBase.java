package com.myanimelist.integration;

import com.myanimelist.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;

@IT
@Sql({
        "classpath:sql/data.sql"
})
public class IntegrationTestBase {

    private static final MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0.26");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
