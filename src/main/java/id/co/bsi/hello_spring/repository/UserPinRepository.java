
package id.co.bsi.hello_spring.repository;

import id.co.bsi.hello_spring.model.UserPin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPinRepository extends JpaRepository<UserPin, Long> {
    Optional<UserPin> findByAccountnum(String accountnum);
}
