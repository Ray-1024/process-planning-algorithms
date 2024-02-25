package algorithm.rr;

import machine.computing.unit.cpu.SimpleHistoryCpu;
import machine.process.Process;

import java.util.Optional;

public class RoundRobinCpu extends SimpleHistoryCpu {

    private int currentTick;
    private final int maximumTicks;

    public RoundRobinCpu(int id, int maximumTicks) {
        super(id);
        currentTick = 0;
        this.maximumTicks = maximumTicks;
    }

    @Override
    public Optional<Process> schedule(Optional<Process> value) {
        var tmp = super.schedule(value);
        if (tmp.isEmpty()) currentTick = 0;
        return tmp;
    }

    @Override
    public void tick() {
        if (currentTick < maximumTicks) {
            ++currentTick;
        }
        super.tick();
    }

    @Override
    public Optional<Process> clean() {
        var tmp = super.clean();
        if (tmp.isPresent()) return tmp;
        else {
            if (currentTick == maximumTicks) {
                tmp = process;
                process = Optional.empty();
                return tmp;
            }
            return Optional.empty();
        }
    }

    @Override
    public boolean isDone() {
        return super.isDone() || currentTick == maximumTicks;
    }
}
