package platformy_technologiczne;

import lombok.*;

import javax.persistence.Entity;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@Entity
public class Mage {
    private String name;
    private int level;

}
