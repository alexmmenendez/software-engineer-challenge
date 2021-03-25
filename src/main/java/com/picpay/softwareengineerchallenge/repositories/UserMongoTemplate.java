package com.picpay.softwareengineerchallenge.repositories;

import com.picpay.softwareengineerchallenge.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserMongoTemplate {

    private final MongoTemplate mongoTemplate;

    public List<User> findByCriteria(final String keyword, final Integer page) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage()
                .matchingPhrase(keyword)
                .caseSensitive(false);

        //Collation collation = Collation.parse("{ locale: 'pt', strength: 4 }");

        Query query = TextQuery.queryText(textCriteria);//.collation(collation);

        Pageable pageable = PageRequest.of(page, 15);

        List<User> users = mongoTemplate.find(
                query.with(Sort.by(Sort.Direction.DESC, "relevanceLevel"))
                        .with(pageable), User.class, "user");

        if (users.isEmpty()) {
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
