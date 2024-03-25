package com.example.demo.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PostRepository;
import com.example.demo.domain.Post;
import com.example.demo.dto.PostDTO;
import com.example.demo.exception.AppException;
import com.example.demo.mapper.PostDTOToEntityMapper;
import com.example.demo.mapper.PostEntityToDTOMapper;

import jakarta.validation.Valid;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostEntityToDTOMapper postEntityToDTOMapper;
    @Autowired
    private PostDTOToEntityMapper postDTOToEntityMapper;

    // Predicate is true if p.getVisibility() is not R (Removed)
    private Predicate<Post> notRemovedPredicate = p -> !p.getVisibility().equals(Character.valueOf('R'));
    
    private Post getPostByID(long postID){
        /**
         * wrapper method that locate a post by its ID and return a Post entity
         * Also checks if the Optional<Post> object returned is empty & Throw a 404 if it does
         * Might not be Single-Purposed but it has to be done
         *
         * NOTE: Really don't know why findById return an Optional<T> object
         *  in they returned an entity in the example beneath shows it returning an entity
         * https://github.com/lokeshgupta1981/Spring-Boot-RestTemplate/\
         * blob/main/spring-demo-webapp/src/main/java/com/howtodoinjava/app/service/UserService.java
         */
            Optional<Post> result = postRepository.findById(postID).filter(notRemovedPredicate);
            if (result.isEmpty())
                throw new AppException(404,"Post not found or Deleted");
            // result.get() would apparently throw an exception anyway if result is empty.
            return result.get();
        }
    
    public java.util.List<PostDTO>  list(){
        return postRepository.findAll()
        .stream()
        .filter( notRemovedPredicate )
        .map(postEntityToDTOMapper)
        .collect(Collectors.toList());
    }
    
    public List<PostDTO> list(int limit, int offset)
    {
        // TODO Auto-generated method stub
        // Q

        // return postRepository.findAll(null)
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }

    public PostDTO create(PostDTO postDTO){
        Post post = postDTOToEntityMapper.apply(postDTO);

        post.setPostedOnDate(new Date());
        
        return postEntityToDTOMapper
                .apply(postRepository.save(post));
    }

    public PostDTO read(long postID){
        // Optional<Post> result = postRepository.findById(postID);
        // if (result.isEmpty()) {
        //     throw new AppException(404,"User not found");
        // }
        // return postEntityToDTOMapper.apply(result.get());
        return postEntityToDTOMapper.apply(getPostByID(postID));

    }

    @SuppressWarnings("null")
    public PostDTO updatePost(long postID, @Valid PostDTO updatedPostDTO, Boolean partialUpdate) {
        Post post = getPostByID(postID);
        // update everything
        // surely a better method exist?
        
        if (partialUpdate == null || partialUpdate == false) {
            post.setTitleString(updatedPostDTO.titleString());
            post.setBodyString(updatedPostDTO.bodyString());
        } else {
            if (updatedPostDTO.titleString() != null) post.setTitleString(updatedPostDTO.titleString());
            if (updatedPostDTO.bodyString() != null) post.setBodyString(updatedPostDTO.bodyString());
        }

        return postEntityToDTOMapper.apply(postRepository.save(post));
        

        // throw new UnsupportedOperationException("Unimplemented method 'patchPost'");
    }

	public void remove(long id) {
        Post post = getPostByID(id);
        post.setVisibility('R');
        postRepository.save(post);
        return;
		// throw new UnsupportedOperationException("Unimplemented method 'remove'");
	}


}
