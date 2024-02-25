package machine.computing.unit.io;

import machine.computing.unit.AbstractHistoryUnit;
import machine.process.Process;
import machine.process.stage.IoStage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleHistoryIo extends AbstractHistoryUnit {

    protected List<Process> queue = new ArrayList<>();

    public SimpleHistoryIo(int id) {
        super(id);
    }

    @Override
    protected boolean validateProcess(Optional<Process> process) {
        return process.isPresent() && !process.get().getStages().isEmpty() && (process.get().getStages().getFirst() instanceof IoStage) && process.get().getStages().getFirst().getId() == id;
    }

    @Override
    public Optional<Process> schedule(Optional<Process> process) {
        if (validateProcess(process)) {
            queue.addLast(process.get());
            return Optional.empty();
        }
        return process;
    }

    @Override
    public void tick() {
        makeHistoryPoint();
        if (!queue.isEmpty())
            queue.getFirst().tick();
    }

    @Override
    public Optional<Process> clean() {
        if (!queue.isEmpty()) {
            if (queue.getFirst().isDone() || queue.getFirst().getStages().getFirst().isDone()) {
                queue.getFirst().clean();
                return Optional.of(queue.removeFirst());
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isDone() {
        return queue.isEmpty();
    }

    @Override
    public void makeHistoryPoint() {
        if (!queue.isEmpty()) {
            history.addLast(String.valueOf(queue.getFirst().getId() + 1));
        } else {
            history.addLast("-");
        }
    }
}
