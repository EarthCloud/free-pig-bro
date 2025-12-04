package cloud.earth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordEntity {
    private String word;
    private int wordCount;
}
