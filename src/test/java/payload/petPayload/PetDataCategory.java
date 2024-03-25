package payload.petPayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.testng.ITestResult;

@Setter
@Getter
public class PetDataCategory {
    public PetDataCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;
}
