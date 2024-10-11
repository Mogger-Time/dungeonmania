package dungeonmania.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoalConditionDto {
    private String goal;
    private List<GoalConditionDto> subgoals;
}
