package payload.petPayload;
import enums.PetStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

public class PetData {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetDataCategory getCategory() {
        return category;
    }

    public void setCategory(PetDataCategory category) {
        this.category = category;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(ArrayList<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public ArrayList<PetDataTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<PetDataTag> tags) {
        this.tags = tags;
    }

    private PetDataCategory category;
    private PetStatus status;

    private ArrayList<String> photoUrls;
    private ArrayList<PetDataTag> tags;

    public PetData(int id, String name, PetDataCategory category, PetStatus status) {
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
