package com.example.demo.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostDTO;

@Service
public class PostDTOToEntityMapper implements Function<PostDTO,Post>{
    @Override
    public Post apply(PostDTO postDTO){
        Post post = new Post();
        post.setTitleString(postDTO.titleString());
        post.setBodyString(postDTO.bodyString());
        post.setPosterString(postDTO.posterString());
        // post.setPostedOnDate(postDTO.postedOnDate());
        return post;
    }
}

