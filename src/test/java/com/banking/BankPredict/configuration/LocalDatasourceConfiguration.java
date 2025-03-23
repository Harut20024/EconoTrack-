package com.banking.BankPredict.configuration;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.TimeZone;

@Profile("!ci")
@Configuration
public class LocalDatasourceConfiguration
{

    @PostConstruct
    public void init()
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean(initMethod = "start")
    public PostgreSQLContainer<?> postgreSQLContainer()
        throws IOException
    {
        try (var socket = new ServerSocket(0))
        {
            return new PostgreSQLContainer<>("postgres:14-alpine")
                .withDatabaseName("app")
                .withUsername("app")
                .withPassword("secret")
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(
                        Ports.Binding.bindPort(socket.getLocalPort()),
                        new ExposedPort(5432)
                    ))
                ));
        }
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgres)
    {
        return new SimpleDriverDataSource(
            postgres.getJdbcDriverInstance(),
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword()
        );
    }
}
