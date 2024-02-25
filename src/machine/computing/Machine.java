package machine.computing;

import machine.process.Process;

public interface Machine {
    void tick();

    boolean isDone();

    String getHistoryMachineState();

    void addProcess(Process process);
}
