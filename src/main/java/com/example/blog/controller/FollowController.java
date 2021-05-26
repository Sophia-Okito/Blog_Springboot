package com.example.blog.controller;

import com.example.blog.model.Follow;
import com.example.blog.model.User;
import com.example.blog.service.FollowService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/follows")
public class FollowController {
    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @PostMapping("/{followeeId}/{followerId}")
    public ResponseEntity<?> follow(@PathVariable(name = "followeeId") Long followeeId, @PathVariable(name = "followerId") Long followerId) {
        Optional<User> follower = userService.getUserById(followerId);
        if (follower.isPresent()) {
            Optional<User> followee = userService.getUserById(followeeId);
            if (followee.isPresent()) {
                Follow follow = followService.getByFolloweeAndFollower(followee.get(), follower.get());
                if (follow == null) {
                    Follow newFollow = new Follow();
                    newFollow.setFollowee(followee.get());
                    newFollow.setFollower(follower.get());
                    followService.addFollow(newFollow);
                    return new ResponseEntity<>(HttpStatus.OK);
                }

            }
        }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<?> followers(@PathVariable(name = "userId") Long id) {
        Optional<User> followee = userService.getUserById(id);
        if (followee.isPresent()) {
            List<Follow> follows =  followService.getAllByFollowee(followee.get());
//            List<User> followers = new ArrayList<>();
//            for (Follow follow: follows)
//                followers.add(follow.getFollower());
            return new ResponseEntity<>(follows, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/followees")
    public ResponseEntity<?> followees(@PathVariable(name = "userId") Long id) {
        Optional<User> follower = userService.getUserById(id);
        if (follower.isPresent()) {
            List<Follow> follows =  followService.getAllByFollower(follower.get());
//            List<User> followees = new ArrayList<>();
//            for (Follow follow: follows)
//                followees.add(follow.getFollowee());
            return new ResponseEntity<>(follows, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{followeeId}/{followerId}")
    public ResponseEntity<?> unfollow(@PathVariable(name = "followeeId") Long followeeId, @PathVariable(name = "followerId") Long followerId) {
        Optional<User> follower = userService.getUserById(followerId);
        if (follower.isPresent()) {
            Optional<User> followee = userService.getUserById(followeeId);
            if (followee.isPresent()) {
                Follow follow = followService.getByFolloweeAndFollower(followee.get(), follower.get());
                if (follow != null)
                followService.deleteFollow(follow);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
