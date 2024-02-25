package machine.process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import machine.StagedExecution;
import machine.process.stage.CpuStage;
import machine.process.stage.IoStage;
import machine.process.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class Process implements StagedExecution<Stage> {
    private final int id;
    private final List<Stage> stages;

    @Override
    public Optional<Stage> schedule(Optional<Stage> value) {
        value.ifPresent(stages::addLast);
        return Optional.empty();
    }

    @Override
    public Optional<Stage> clean() {
        if (!stages.isEmpty() && stages.getFirst().isDone()) {
            return Optional.of(stages.removeFirst());
        }
        return Optional.empty();
    }

    @Override
    public void tick() {
        if (!stages.isEmpty()) stages.getFirst().tick();
    }

    @Override
    public boolean isDone() {
        return stages.isEmpty();
    }

    public static Optional<Process> parse(int pid, String tasks) {
        List<Stage> stages = new ArrayList<>();
        for (String token : tasks.split(";")) {
            int ticks = Integer.parseInt(token.substring(4, token.length() - 1));
            switch (token.substring(0, 4)) {
                case "CPU(" -> stages.addLast(new CpuStage(ticks));
                case "IO1(" -> stages.addLast(new IoStage(0, ticks));
                case "IO2(" -> stages.addLast(new IoStage(1, ticks));
                default -> {
                    return Optional.empty();
                }
            }
        }
        return Optional.of(new Process(pid, stages));
    }
}
