package machine.computing.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import machine.process.Process;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public abstract class AbstractUnit implements Unit {
    protected final int id;
    protected final List<Process> history = new ArrayList<>();

    protected abstract boolean validateProcess(Process process);
}
