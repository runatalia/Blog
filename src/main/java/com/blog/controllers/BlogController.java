package com.blog.controllers;


import com.blog.entity.Post;
import com.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

    @Controller
public class BlogController {
        
        @Autowired
        private PostRepository postRepository;
        
	@GetMapping("/blog")
	public String blogMain(Model model) {
            Iterable<Post> posts = postRepository.findAll();
            model.addAttribute("posts",posts);
		return "blog-main";
	}
        @GetMapping("/blog/{id}")
	public String blogDetails(@RequestParam Long id, Model model) {
         Post post;
            if(postRepository.findById(id).isPresent()){
            post = postRepository.findById(id).get();}
            else post = null;
            model.addAttribute("post",post);
		return "blog-main";
	}
         @GetMapping("/blog/add")
	public String addPost(Model model) {
      
		return "blog-add";
	}
        

}

