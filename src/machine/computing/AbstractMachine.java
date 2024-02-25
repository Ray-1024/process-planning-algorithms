package machine.computing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.Unit;
import machine.process.Process;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractMachine implements Machine {
    @Getter
    protected final List<HistoryUnit> cpu;
    @Getter
    protected final List<HistoryUnit> io;
    protected final List<Process> cpuQueue = new ArrayList<>();
    protected final List<Process> ioQueue = new ArrayList<>();
}
