package com.github.praimuwka.spring_archive_websystem.model.entities.user;

import com.github.praimuwka.spring_archive_websystem.model.entities.user.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);
}