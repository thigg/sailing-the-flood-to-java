package Game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewGameParams {
    public int size;
    public int numColors;
}
