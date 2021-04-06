package com.picpay.softwareengineerchallenge.repositories;

import com.picpay.softwareengineerchallenge.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserMongoTemplate {

    private final MongoTemplate mongoTemplate;
    private static final Integer PAGE_SIZE = 15;

    public List<User> findByCriteria(final String keyword, final Integer page) {

        log.info("Finding users by keyword: {}, page: {} with matchingPhrase strategy", keyword, page);
        List<User> users = mongoTemplate.find(
                TextQuery.queryText(
                        TextCriteria.forDefaultLanguage()
                                .matchingPhrase(keyword)
                                .caseSensitive(false))
                        .with(Sort.by(Sort.Direction.DESC, "relevanceLevel"))
                        .with(PageRequest.of(page, PAGE_SIZE)), User.class, "user");

        if (users.isEmpty()) {
            log.info("Finding users by keyword: {}, page: {} with matchingAny strategy", keyword, page);
            users = mongoTemplate.find(
                    TextQuery.queryText(
                            TextCriteria.forDefaultLanguage()
                                    .matchingAny(keyword)
                                    .caseSensitive(false))
                            .with(PageRequest.of(page, 15)), User.class, "user");
        }

        return users;
    }
}
