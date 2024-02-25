package machine.computing.unit.cpu;

import lombok.Getter;
import machine.computing.unit.AbstractHistoryUnit;
import machine.process.Process;
import machine.process.stage.CpuStage;

import java.util.Optional;

@Getter
public class SimpleHistoryCpu extends AbstractHistoryUnit {

    protected Optional<Process> process = Optional.empty();

    public SimpleHistoryCpu(int id) {
        super(id);
    }

    @Override
    public Optional<Process> schedule(Optional<Process> value) {
        if (process.isEmpty() && validateProcess(value)) {
            this.process = value;
            return Optional.empty();
        }
        return value;
    }

    @Override
    public void tick() {
        makeHistoryPoint();
        process.ifPresent(Process::tick);
    }

    @Override
    public Optional<Process> clean() {
        if (process.isPresent()) {
            if (process.get().isDone() || process.get().getStages().getFirst().isDone()) {
                process.get().clean();
                var tmp = process;
                process = Optional.empty();
                return tmp;
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isDone() {
        return process.isEmpty();
    }

    @Override
    public void makeHistoryPoint() {
        process.ifPresentOrElse(process1 -> history.addLast(String.valueOf(process1.getId())), () -> history.addLast("-"));
    }

    @Override
    protected boolean validateProcess(Optional<Process> process) {
        return process.isPresent() && !process.get().isDone() && (process.get().getStages().getFirst() instanceof CpuStage);
    }
}
