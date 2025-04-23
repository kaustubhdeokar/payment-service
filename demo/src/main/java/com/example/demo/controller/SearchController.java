package com.example.demo.controller;

import com.example.demo.dto.UserSkeletonDto;
import com.example.demo.service.UserSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final UserSearchService userSearchService;

    public SearchController(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @GetMapping("/{text}")
    public ResponseEntity<List<UserSkeletonDto>> getAllUserList(@PathVariable String text){
        List<UserSkeletonDto> userSkeletonDtos = userSearchService.searchUsers(text);
        return new ResponseEntity<>(userSkeletonDtos, HttpStatus.OK);
    }

}
