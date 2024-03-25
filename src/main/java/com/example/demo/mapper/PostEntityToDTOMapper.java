package com.example.demo.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostDTO;

@Service
public class PostEntityToDTOMapper implements Function<Post,PostDTO>{
    @Override
    public PostDTO apply(Post post){
        boolean isHidden = post.getVisibility() == 'H';
        String hiddenMessage = "[[Post Hidden]]";
        return new PostDTO(
            post.getPostID(),
            (isHidden) ? hiddenMessage : post.getTitleString(),
            (isHidden) ? hiddenMessage : post.getBodyString(),
            post.getPosterString(),
            post.getPostedOnDate());
    }
}
