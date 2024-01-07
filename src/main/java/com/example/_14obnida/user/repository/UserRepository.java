package com.example._14obnida.user.repository;

import com.example._14obnida.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickName);
}
