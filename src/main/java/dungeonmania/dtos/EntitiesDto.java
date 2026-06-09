package dungeonmania.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntitiesDto {
    private int x;
    private int y;
    private String type;
    private String colour;
    private int movement_factor;
    private int key;
    private String logic;

    public EntitiesDto(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
