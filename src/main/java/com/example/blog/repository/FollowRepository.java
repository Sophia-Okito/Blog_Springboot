package com.example.blog.repository;

import com.example.blog.model.Follow;
import com.example.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowee(User user);

    List<Follow> findAllByFollower(User user);

    Follow findByFolloweeAndFollower(User followee, User follower);
}
