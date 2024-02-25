package machine.computing.unit;

import machine.process.Process;

import java.util.List;

public interface Unit {
    int getId();

    Process getCurrentProcess();

    List<Process> getHistory();

    Process scheduleStage(Process process);

    void executeStage();

    Process cleanStage();
}
