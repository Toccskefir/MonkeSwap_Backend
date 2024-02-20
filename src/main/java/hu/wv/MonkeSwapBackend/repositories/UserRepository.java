package hu.wv.MonkeSwapBackend.repositories;

import hu.wv.MonkeSwapBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
