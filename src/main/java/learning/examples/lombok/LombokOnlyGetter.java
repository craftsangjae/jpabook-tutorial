package learning.examples.lombok;

import lombok.Getter;

@Getter
public class LombokOnlyGetter{
    private String name;
    private int id;

    public LombokOnlyGetter(String name, int id) {
        this.id = id;
        this.name = name;
    }
}
