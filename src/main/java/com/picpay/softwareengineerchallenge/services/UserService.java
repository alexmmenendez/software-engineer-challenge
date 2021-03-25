package com.picpay.softwareengineerchallenge.services;

import com.picpay.softwareengineerchallenge.controller.response.UserResponse;
import com.picpay.softwareengineerchallenge.domain.User;
import com.picpay.softwareengineerchallenge.repositories.UserRepository;
import com.picpay.softwareengineerchallenge.repositories.UserMongoTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMongoTemplate userMongoTemplate;

    public List<UserResponse> searchUserByKeyword(final String keyword, final Integer page) throws Exception {
        final List<User> users =
                Optional.ofNullable(
                        userMongoTemplate.findByCriteria(keyword, page))
                        .orElseThrow(() -> new Exception("deu ruim"));

        List<UserResponse> userResponses = users.stream()
                .map(user -> UserResponse.builder()
                        .uuid(user.getUuid())
                        .name(user.getName())
                        .username(user.getUsername())
                        .relevanceLevel(user.getRelevanceLevel())
                        .build()
                ).collect(Collectors.toList());

        return userResponses;
    }

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findFirstByUsername(username);
    }
}
