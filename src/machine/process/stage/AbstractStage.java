package machine.process.stage;

import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractStage implements Stage {
    private int ticks;

    @Override
    public void tick() {
        if (ticks > 0) {
            --ticks;
        }
    }

    @Override
    public boolean isDone() {
        return ticks == 0;
    }

    @Override
    public Optional<Void> schedule(Optional<Void> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Void> clean() {
        throw new UnsupportedOperationException();
    }
}
