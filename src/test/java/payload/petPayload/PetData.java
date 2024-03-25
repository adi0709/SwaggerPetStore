package payload.petPayload;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;


@Setter
@Getter
public class PetData {
    private int id;
    private String name;
    private PetDataCategory category;
    private String status;

    private ArrayList<String> photoUrls;
    private ArrayList<PetDataTag> tags;

    public PetData(int id, String name, PetDataCategory category, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = status;
        this.photoUrls = new ArrayList<String>();
        this.tags = new ArrayList<PetDataTag>();
    }

    public void setUrls(String... urls){
        Collections.addAll(this.photoUrls, urls);
    }

    public void setTag(PetDataTag... tag){
        Collections.addAll(this.tags, tag);
    }

}
