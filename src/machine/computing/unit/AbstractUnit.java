package machine.computing.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import machine.process.Process;

import java.util.Optional;

@AllArgsConstructor
@Getter
public abstract class AbstractUnit implements Unit {
    protected final int id;

    protected abstract boolean validateProcess(Optional<Process> process);
}
