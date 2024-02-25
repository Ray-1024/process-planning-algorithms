package machine;

import java.util.Optional;

public interface StagedExecution<T> {
    Optional<T> schedule(Optional<T> value);

    void tick();

    Optional<T> clean();

    boolean isDone();
}
