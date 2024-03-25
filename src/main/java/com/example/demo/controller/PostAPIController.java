package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PostDTO;
import com.example.demo.exception.AppException;
import com.example.demo.service.PostService;

import jakarta.validation.Valid;



@RestController
public class PostAPIController {

    @Autowired
    private PostService postService;

    @GetMapping(params = {}, path = "/api/posts")
    public List<PostDTO> getMethodName() {
        return postService.list();
    }

    @GetMapping(params = {"limit", "offset"}, path = "/api/posts") // TODO: add pagination using PageRequest
    public List<PostDTO> getMethodName(@RequestParam int limit, @RequestParam int offset) {
        return postService.list(limit,offset);
    }

    @GetMapping(path = "/api/posts/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        return postService.read(id);
    }

    @PatchMapping(path = "/api/posts/{id}")
    public ResponseEntity<PostDTO> patchPost(@PathVariable long id, @RequestBody /*@Valid*/ PostDTO postPatchDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new AppException(400, "Malformed request");
        return new ResponseEntity<PostDTO>(postService.updatePost(id, postPatchDto,true),HttpStatus.OK);
    }
    // To hard to do partial update without Mapping
    @PostMapping(path = "/api/posts/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable long id, @RequestBody @Valid PostDTO postUpdateDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            {
                System.out.println(bindingResult);
                throw new AppException(400, "Malformed request");
            }
        return new ResponseEntity<PostDTO>(postService.updatePost(id, postUpdateDto, false),HttpStatus.OK);
    }

    // @PostMapping("/api/posts")
    @PutMapping("/api/posts")
    // bindingResult will bind the error, allow the dev to process the error manually
    public ResponseEntity<String> postMethodName(@RequestBody /*@Valid*/ PostDTO postDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new AppException(400, "Malformed request");
        
        postService.create(postDTO);
        
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.remove(id);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
    
}
