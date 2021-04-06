package com.picpay.softwareengineerchallenge.services;

import com.picpay.softwareengineerchallenge.controller.response.UserResponse;
import com.picpay.softwareengineerchallenge.domain.User;
import com.picpay.softwareengineerchallenge.repositories.UserMongoRepository;
import com.picpay.softwareengineerchallenge.repositories.UserMongoTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMongoRepository userMongoRepository;
    private final UserMongoTemplate userMongoTemplate;

    public List<UserResponse> searchUserByKeyword(final String keyword, final Integer page) {
        log.info("Searching users by keyword: {}, page: {}", keyword, Math.subtractExact(page, 1));
        final List<User> users = userMongoTemplate.findByCriteria(keyword, Math.subtractExact(page, 1));

        return users.stream()
                .map(user -> UserResponse.builder()
                        .uuid(user.getUuid())
                        .name(user.getName())
                        .username(user.getUsername())
                        .relevanceLevel(user.getRelevanceLevel())
                        .build()
                ).collect(Collectors.toList());
    }

    @Cacheable(cacheManager = "userCacheManager", cacheNames = "user", unless = "#result == null")
    public Optional<User> findUserByUsername(final String username) {
        log.info("Finding user by username: {}", username);
        final Optional<User> user = userMongoRepository.findFirstByUsername(username);
        log.info("Caching user: {}", user);
        return user;
    }
}
