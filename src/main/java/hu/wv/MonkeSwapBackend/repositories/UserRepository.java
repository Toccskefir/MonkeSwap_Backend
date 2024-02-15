package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
