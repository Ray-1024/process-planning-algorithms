package machine.computing.unit;

import machine.StagedExecution;
import machine.process.Process;

public interface Unit extends StagedExecution<Process> {
    int getId();
}
