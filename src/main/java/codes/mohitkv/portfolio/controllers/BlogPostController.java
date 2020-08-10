package codes.mohitkv.portfolio.controllers;

import codes.mohitkv.portfolio.data.models.BlogPost;
import codes.mohitkv.portfolio.data.repositories.BlogPostRepository;
import codes.mohitkv.portfolio.exceptions.BlogPostNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts/")
public class BlogPostController {
    private final BlogPostRepository repository;

    BlogPostController(BlogPostRepository repository){
        this.repository = repository;
    }

    // REST end points
    // List all
    @GetMapping
    List<BlogPost> all() {
        return (List<BlogPost>) repository.findAll();
    }

    // Get by id
    @GetMapping("{id}")
    BlogPost one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new BlogPostNotFoundException(id));
    }

    // New post
    @PostMapping
    BlogPost newPost(@RequestBody BlogPost blogPost){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        blogPost.setDateTimeString(dtf.format(now));
        return repository.save(blogPost);
    }

    // Update a post
    @PutMapping("{id}")
    BlogPost updatePost(@RequestBody BlogPost blogPost, @PathVariable String id){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return repository.findById(id)
                .map(blogPost1 -> {
                    blogPost1.setTitle(blogPost.getTitle());
                    blogPost1.setContent(blogPost.getContent());
                    blogPost1.setDateTimeString(dtf.format(now));
                    return repository.save(blogPost1);
                })
                .orElseGet(()->{
                    return repository.save(blogPost);
                });
    }

    // Delete by id
    @DeleteMapping("{id}")
    void deletePost(@PathVariable String id){
        repository.deleteById(id);
    }
}
