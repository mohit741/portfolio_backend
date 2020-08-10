package codes.mohitkv.portfolio.data.repositories;

import codes.mohitkv.portfolio.data.models.BlogPost;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface BlogPostRepository extends CrudRepository<BlogPost, String> {
    Optional<BlogPost> findById(String id);
}
