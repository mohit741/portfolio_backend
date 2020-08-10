package codes.mohitkv.portfolio.data.repositories;

import codes.mohitkv.portfolio.data.models.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
