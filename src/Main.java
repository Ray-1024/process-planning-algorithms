import algorithm.fcfs.FcfsMachine;
import algorithm.rr.RoundRobinMachine;
import algorithm.spn.SpnMachine;
import algorithm.srt.SrtMachine;
import machine.computing.AbstractHistoryMachine;
import machine.process.Process;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    private static void lab(Map<Integer, String> tasks, AbstractHistoryMachine machine, PrintStream printer) {
        for (int tick = 0, pid = 0; ; ++tick) {
            if (tasks.containsKey(tick)) machine.schedule(Process.parse(pid++, tasks.get(tick)));
            if (machine.isDone()) break;
            machine.tick();
            machine.clean();
        }
        printer.println(String.join("\n", machine.getHistory()));
    }

    private static Map<Integer, String> getTasks() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(Main.class.getResource("tasks2.txt")).toURI()));
        Map<Integer, String> tasks = new HashMap<>();
        for (int i = 0; i < lines.size(); ++i) {
            tasks.put(2 * i, lines.get(i));
        }
        return tasks;
    }


    public static void main(String[] args) throws URISyntaxException, IOException {
        lab(getTasks(), new RoundRobinMachine(2, 2, 4), System.out);
    }
}