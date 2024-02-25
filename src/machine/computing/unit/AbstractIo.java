package machine.computing.unit;

import machine.process.Process;
import machine.process.stage.IO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractIo extends AbstractUnit {

    protected List<Process> queue = new ArrayList<>();

    public AbstractIo(int id) {
        super(id);
    }

    @Override
    public Process getCurrentProcess() {
        return queue.isEmpty() ? null : queue.getFirst();
    }

    @Override
    protected boolean validateProcess(Process process) {
        return Objects.nonNull(process) && !process.getStages().isEmpty() && (process.getStages().getFirst() instanceof IO) && process.getStages().getFirst().getId() == id;
    }

    @Override
    public Process scheduleStage(Process process) {
        if (validateProcess(process)) {
            queue.addLast(process);
            return null;
        }
        return process;
    }

    @Override
    public void executeStage() {
        if (queue.isEmpty()) {
            history.addLast(null);
            return;
        }
        history.addLast(queue.getFirst());
        int ticks = queue.getFirst().getStages().getFirst().getTicks() - 1;
        queue.getFirst().getStages().getFirst().setTicks(ticks);
    }

    @Override
    public Process cleanStage() {
        if (queue.isEmpty()) return null;
        var stages = queue.getFirst().getStages();
        if (stages.isEmpty()) {
            queue.removeFirst();
            return null;
        }
        var stage = stages.getFirst();
        if (stage.getTicks() <= 0) {
            stages.removeFirst();
            return queue.removeFirst();
        }
        return null;
    }
}
