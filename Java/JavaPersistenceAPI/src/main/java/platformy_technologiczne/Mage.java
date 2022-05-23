package platformy_technologiczne;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Mage {

    @Id
    private String name;
    private int level;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Tower tower;

    public String toString(){//overriding the toString() method
        return "{name = " + name +", level = "+level+"}";
    }

}
