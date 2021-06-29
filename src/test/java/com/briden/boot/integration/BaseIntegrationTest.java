package com.briden.boot.integration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = BaseIntegrationTest.Initializer.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BaseIntegrationTest {

    private static final Integer POSTGRES_PORT = 5432;
    private static final String POSTGRES_SERVICE = "postgres_1";

    private static final Integer LOCALSTACK_PORT = 4566;
    private static final String LOCALSTACK_SERVICE = "localstack_1";

    private static final String POSTGRES_PASSWORD = "password";
    private static final String POSTGRES_USER = "testUser";
    private static final String POSTGRES_DB = "test";

    private static final DockerComposeContainer<?> dockerComposeContainer = new DockerComposeContainer<>(new File("src/test/resources/docker-compose.yml"))
            .withExposedService(LOCALSTACK_SERVICE, LOCALSTACK_PORT, Wait.forLogMessage(".*\"TableArn\": \"arn:aws:dynamodb:us-east-1:000000000000:table/dynamo-entrys\".*", 1))
            .withExposedService(POSTGRES_SERVICE, POSTGRES_PORT);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext context) {
            final ConfigurableEnvironment environment = context.getEnvironment();

            dockerComposeContainer.start();
            String jdbcUrl = String.format(
                    "jdbc:postgresql://%s:%s/%s",
                    dockerComposeContainer.getServiceHost(POSTGRES_SERVICE, POSTGRES_PORT),
                    dockerComposeContainer.getServicePort(POSTGRES_SERVICE, POSTGRES_PORT),
                    POSTGRES_DB
            );
            String postgresUrl = String.format("spring.datasource.url=%s", jdbcUrl);
            String postgresUser = String.format("spring.datasource.username=%s", POSTGRES_USER);
            String postgresPassword = String.format("spring.datasource.password=%s", POSTGRES_PASSWORD);

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    environment, postgresUrl, postgresUser, postgresPassword
            );
        }
    }
}
