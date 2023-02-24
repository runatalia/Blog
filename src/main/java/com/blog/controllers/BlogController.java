package com.blog.controllers;

import com.blog.entity.Post;
import com.blog.repository.PostRepository;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)) 
            return "redirect:/blog";
       Optional <Post> post =  postRepository.findById(id);
       ArrayList<Post> result = new ArrayList();
       post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "details";
    }

    @GetMapping("/blog/add")
    public String addPost(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String addBlogPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

}
