package com.kt.game.goodgame.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableReactiveMongoRepositories(basePackages = {"com.kt.game.goodgame"})
@EnableMongoAuditing
public class MongodbConfig {

//    @Bean
//    public MongoClient getMongoClient() {
//        return MongoClients.create(
//                MongoClientSettings.builder()
//                        .applyConnectionString(new ConnectionString("mongodb://54.180.176.187:27017"))
//                        .build()
//        );
//    }

}
