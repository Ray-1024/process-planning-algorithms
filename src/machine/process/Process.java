package machine.process;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import machine.process.stage.CPU;
import machine.process.stage.IO;
import machine.process.stage.Stage;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Process {
    private final int id;
    private final List<Stage> stages;

    public static Process parse(int pid, String tasks) {
        List<Stage> stages = new ArrayList<>();
        for (String token : tasks.split(";")) {
            int ticks = Integer.parseInt(token.substring(4, token.length() - 1));
            stages.addLast(
                    switch (token.substring(0, 4)) {
                        case "CPU(" -> new CPU(ticks);
                        case "IO1(" -> new IO(0, ticks);
                        case "IO2(" -> new IO(1, ticks);
                        default -> throw new IllegalArgumentException("Wrong token");
                    }
            );
        }
        return new Process(pid, stages);
    }

    public boolean isValid() {
        return !stages.isEmpty();
    }
}
