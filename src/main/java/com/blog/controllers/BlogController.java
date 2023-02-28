package com.blog.controllers;

import com.blog.entity.Post;
import com.blog.repository.PostRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BlogController {

    @Value("${upload.path}")
    String path;
    
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
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
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
    public String addBlogPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, @RequestParam MultipartFile file, Model model) {
          Post post = new Post(title, anons, full_text);
          try {
            if (!file.isEmpty()) {
                File upLoadDir = new File(path);
                if(!upLoadDir.exists()){
                    upLoadDir.mkdirs();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFile = uuidFile +"."+file.getOriginalFilename();
                 
                file.transferTo(new File(path+"\\"+resultFile));
                post.setFileName(resultFile);
                System.out.println(resultFile);
            } 
        }
          catch(IOException e){
          System.out.println("Ошибка при загрузке файла "+e);
          }

      
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/edit")
    public String editPost(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String editBlogUpdate(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setTitle(title);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String removePost(@PathVariable(value = "id") Long id, Model model) {
        postRepository.deleteById(id);
        return "redirect:/blog";
    }
}
