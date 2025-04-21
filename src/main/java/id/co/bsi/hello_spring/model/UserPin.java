
package id.co.bsi.hello_spring.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_pin")
public class UserPin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountnum;

    @Column(nullable = false)
    private String pinHash;
}
