package com.picpay.softwareengineerchallenge.configs;

import com.picpay.softwareengineerchallenge.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@DependsOn("mongoTemplate")
public class CollectionIndexConfig {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name")
                .onField("username")
                .build();

        mongoTemplate.indexOps(User.class).ensureIndex(textIndex);
    }
}