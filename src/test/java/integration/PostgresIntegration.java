package integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@DirtiesContext
public interface PostgresIntegration {

  PostgreSQLContainer<?> PG = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.0-bullseye"));

  @DynamicPropertySource
  static void properties(final DynamicPropertyRegistry reg) {
    reg.add("spring.datasource.url", PostgresIntegration.PG::getJdbcUrl);
    reg.add("spring.liquibase.url", PostgresIntegration.PG::getJdbcUrl);
  }

  @BeforeAll
  static void start() {
    PostgresIntegration.PG.start();
  }

  @AfterAll
  static void stop() {
    PostgresIntegration.PG.stop();
  }
}
