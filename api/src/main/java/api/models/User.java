package api.models;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "application", name = "tb_users")
public class User {

    @Id
    @GeneratedValue(generator = "sq_users", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "sq_users",
            schema = "application",
            sequenceName = "sq_users",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
