package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState {
    public int [][] field;
    public boolean won;
    public int steps;
    public int maxSteps;
}
