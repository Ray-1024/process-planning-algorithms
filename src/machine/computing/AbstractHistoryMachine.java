package machine.computing;

import lombok.Getter;
import machine.Historied;
import machine.computing.unit.HistoryUnit;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractHistoryMachine extends AbstractMachine implements Historied {
    protected final List<String> history = new ArrayList<>();

    public AbstractHistoryMachine(List<HistoryUnit> cpu, List<HistoryUnit> io) {
        super(cpu, io);
    }
}
