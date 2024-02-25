package machine.computing;

import lombok.Getter;
import machine.computing.unit.Unit;
import machine.process.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public abstract class AbstractMachine implements Machine {
    @Getter
    protected final List<Unit> cpu;
    @Getter
    protected final List<Unit> io;
    protected final List<Process> cpuQueue = new ArrayList<>();
    protected final List<Process> ioQueue = new ArrayList<>();

    protected int currentTick;
    protected final StringBuilder builder = new StringBuilder();

    protected AbstractMachine(List<Unit> cpu, List<Unit> io) {
        this.cpu = cpu;
        this.io = io;
        builder.append("T | ");
        cpu.forEach(unit -> builder.append("CPU").append(unit.getId()).append(" | "));
        io.forEach(unit -> builder.append("IO").append(unit.getId()).append(" | "));
        builder.append("\n");
    }

    @Override
    public void addProcess(Process process) {
        if (Objects.nonNull(process)) {
            cpuQueue.addLast(process);
        }
    }

    protected void scheduleStage() {
        BiConsumer<List<Process>, List<Unit>> schedule = (queue, units) -> {
            while (!queue.isEmpty()) {
                Process process = queue.removeFirst();
                for (Unit unit : units) {
                    process = unit.scheduleStage(process);
                    if (Objects.isNull(process)) break;
                }
                if (Objects.nonNull(process)) {
                    queue.addFirst(process);
                    break;
                }
            }
        };
        schedule.accept(cpuQueue, cpu);
        schedule.accept(ioQueue, io);
    }

    protected void executeStage() {
        cpu.forEach(Unit::executeStage);
        io.forEach(Unit::executeStage);
    }

    protected void cleanStage() {
        cpuQueue.addAll(io.stream().map(Unit::cleanStage).filter(Objects::nonNull).filter(Process::isValid).toList());
        ioQueue.addAll(cpu.stream().map(Unit::cleanStage).filter(Objects::nonNull).filter(Process::isValid).toList());
    }

    @Override
    public void tick() {
        scheduleStage();
        executeStage();
        cleanStage();
        addNewHistoryPoint();
        ++currentTick;
    }

    private void addNewHistoryPoint() {
        builder.append(currentTick).append(" : ");
        Stream.of(cpu, io).forEach(units -> {
            units.forEach(unit -> {
                builder.append(Objects.nonNull(unit.getHistory().getLast()) ? String.valueOf(unit.getHistory().getLast().getId() + 1) : "-");
                builder.append(" ");
            });
            builder.append("| ");
        });
        builder.append("\n");
    }

    @Override
    public String getHistoryMachineState() {
        return builder.toString();
    }

    @Override
    public boolean isDone() {
        if (cpu.stream().anyMatch(unit -> Objects.nonNull(unit.getCurrentProcess()))) return false;
        if (io.stream().anyMatch(unit -> Objects.nonNull(unit.getCurrentProcess()))) return false;
        if (!cpuQueue.isEmpty()) return false;
        return ioQueue.isEmpty();
    }
}