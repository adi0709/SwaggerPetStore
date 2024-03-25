package payload.petPayload;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PetDataTag {

    private Integer id;
    private String name;

    public PetDataTag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
