package com.partnerd.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;


@RequiredArgsConstructor
@EnableR2dbcRepositories(
        basePackages = "com.partnerd.r2dbcRepository"
)
@EnableR2dbcAuditing
@EnableConfigurationProperties
@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {


    @Value("${spring.r2dbc.url}")
    private String dbUrl;

    @Value("${spring.r2dbc.username}")
    private String dbUsername;

    @Value("${spring.r2dbc.password}")
    private String dbPassword;
    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, "mysql")
                .option(PROTOCOL, "mysql")  // "mysql"로 명시
                .option(HOST, "partnerddb.creiqyoakhm2.ap-northeast-2.rds.amazonaws.com")
                .option(PORT, 3306)
                .option(USER, dbUsername)
                .option(PASSWORD, dbPassword)
                .option(DATABASE, "partnerd")
                .option(SSL, false)  // SSL 비활성화
                .build());
        return connectionFactory;
    }


    // ✅ R2DBC 트랜잭션 매니저 명시 등록
    @Bean(name = "connectionFactoryTransactionManager")
    public ReactiveTransactionManager reactiveTransactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}
