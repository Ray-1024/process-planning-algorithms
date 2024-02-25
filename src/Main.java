import algorithm.fcfs.FcfsMachine;
import algorithm.hrrn.HrrnMachine;
import algorithm.rr.RoundRobinMachine;
import algorithm.spn.SpnMachine;
import algorithm.srt.SrtMachine;
import machine.computing.AbstractHistoryMachine;
import machine.process.Process;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    private static void lab(Map<Integer, String> tasks, AbstractHistoryMachine machine, PrintWriter printer) {
        for (int tick = 0, pid = 0; ; ++tick) {
            if (tasks.containsKey(tick)) machine.schedule(Process.parse(pid++, tasks.get(tick)));
            if (machine.isDone()) break;
            machine.tick();
            machine.clean();
        }
        printer.println(String.join("\n", machine.getHistory()));
        printer.close();
    }

    private static Map<Integer, String> getTasks() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(Main.class.getResource("tasks.txt")).toURI()));
        Map<Integer, String> tasks = new HashMap<>();
        for (int i = 0; i < lines.size(); ++i) {
            tasks.put(2 * i, lines.get(i));
        }
        return tasks;
    }


    public static void main(String[] args) throws URISyntaxException, IOException {
        var tasks = getTasks();

        Path.of("report/").toFile().mkdirs();
        for (Map.Entry<String, AbstractHistoryMachine> entry : new HashMap<String, AbstractHistoryMachine>() {{
            put("output/fcfs.txt", new FcfsMachine(4, 2));
            put("output/rr_1.txt", new RoundRobinMachine(4, 2, 1));
            put("output/rr_4.txt", new RoundRobinMachine(4, 2, 4));
            put("output/spn.txt", new SpnMachine(4, 2));
            put("output/srt.txt", new SrtMachine(4, 2));
            put("output/hrrn.txt", new HrrnMachine(4, 2));
        }}.entrySet()) {
            String filename = entry.getKey();
            AbstractHistoryMachine machine = entry.getValue();
            try {
                lab(tasks, machine, new PrintWriter(filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}