package machine.process.stage;

import lombok.Getter;

@Getter
public class IoStage extends AbstractStage {
    private final int id;

    public IoStage(int id, int ticks) {
        super(ticks);
        this.id = id;
    }
}
