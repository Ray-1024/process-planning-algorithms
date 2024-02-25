package machine.computing.unit;

import lombok.Getter;
import machine.process.Process;
import machine.process.stage.CPU;

import java.util.Objects;

@Getter
public abstract class AbstractCpu extends AbstractUnit {

    protected Process currentProcess = null;

    public AbstractCpu(int id) {
        super(id);
    }

    @Override
    protected boolean validateProcess(Process process) {
        return Objects.nonNull(process) && process.isValid() && (process.getStages().getFirst() instanceof CPU);
    }

    @Override
    public Process scheduleStage(Process process) {
        if (Objects.isNull(currentProcess) && validateProcess(process)) {
            currentProcess = process;
            return null;
        }
        return process;
    }

    @Override
    public void executeStage() {
        history.addLast(currentProcess);
        if (Objects.isNull(currentProcess)) return;
        if (!currentProcess.isValid()) return;
        int ticks = currentProcess.getStages().getFirst().getTicks() - 1;
        currentProcess.getStages().getFirst().setTicks(ticks);
    }

    @Override
    public Process cleanStage() {
        if (Objects.isNull(currentProcess)) return null;
        var stages = currentProcess.getStages();
        if (stages.isEmpty()) {
            currentProcess = null;
            return null;
        }
        var stage = stages.getFirst();
        if (stage.getTicks() <= 0) {
            stages.removeFirst();
            Process tmp = currentProcess;
            currentProcess = null;
            return tmp;
        }
        return null;
    }
}
