package com.kt.game.goodgame.config;

import com.kt.game.goodgame.outergame.repository.GameHistoryRepository;
import com.kt.game.goodgame.outergame.repository.MemberRepository;
import com.kt.game.goodgame.rating.repository.RatingRepository;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories(basePackages = {"com.kt.game.goodgame"})
@EnableTransactionManagement
public class R2dbcPostgresqlConfig {

    @Bean(name = "postgresqlConnectionFactory")
    @Primary

    public PostgresqlConnectionFactory getPostgresqlConnectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("54.180.176.187")
                        .port(5432)
                        .username("ktgame")
                        .password("ktgame")
                        .database("ktgame_database")
                        .build()
        );
    }

    @Bean
    public ReactiveTransactionManager getReactiveTransactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

}
