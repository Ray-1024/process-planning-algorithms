package machine.process.stage;

import machine.StagedExecution;

public interface Stage extends StagedExecution<Void> {
    int getId();
}
