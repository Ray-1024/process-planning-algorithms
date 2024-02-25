package machine.process.stage;

import lombok.Getter;

@Getter
public class IO extends AbstractStage {
    private final int id;

    public IO(int id, int ticks) {
        super(ticks);
        this.id = id;
    }
}
