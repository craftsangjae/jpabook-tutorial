package learning.examples.lombok;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LombokGetterSetter {
    private String name;
    private int id;

    public LombokGetterSetter(String name, int id) {
        this.id = id;
        this.name = name;
    }
}
