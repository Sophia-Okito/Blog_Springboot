package com.example.blog.service;

import com.example.blog.model.Follow;
import com.example.blog.model.User;
import com.example.blog.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    FollowRepository followRepository;

    public List<Follow> getAllByFollowee(User user) {
        return followRepository.findAllByFollowee(user);
    }

    public List<Follow> getAllByFollower(User user) {
        return followRepository.findAllByFollower(user);
    }

    public Follow getByFolloweeAndFollower(User followee, User follower) {
        return followRepository.findByFolloweeAndFollower(followee, follower);
    }

    public void deleteFollow(Follow follow) {
        followRepository.delete(follow);
    }

    public void addFollow(Follow newFollow) {
        followRepository.save(newFollow);
    }
}
