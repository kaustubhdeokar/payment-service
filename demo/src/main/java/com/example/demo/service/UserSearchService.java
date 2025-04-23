package com.example.demo.service;

import com.example.demo.dto.UserSkeletonDto;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class UserSearchService {
    private final UserRepo userRepo;

    public UserSearchService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserSkeletonDto> searchUsers(String userSearchText) {
        User user = new User();
        user.setUsername(userSearchText);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", contains());

        Example<User> list = Example.of(user, matcher);
        List<User> users = userRepo.findAll(list);
        return convertToUserSkeleton(users);
    }

    public List<UserSkeletonDto> convertToUserSkeleton(List<User> users){
        List<UserSkeletonDto> list = new ArrayList<>();
        for(User user: users){
            UserSkeletonDto dto = new UserSkeletonDto();
            dto.setName(user.getUsername());
            list.add(dto);
        }
        return list;
    }

}
