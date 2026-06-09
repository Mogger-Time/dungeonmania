package dungeonmania.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DungeonsDto {
    private List<EntitiesDto> entities;
    @JsonProperty("goal-condition")
    private GoalConditionDto goalCondition;
}
