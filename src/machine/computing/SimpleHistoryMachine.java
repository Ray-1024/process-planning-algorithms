package machine.computing;

import machine.StagedExecution;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.Unit;
import machine.process.Process;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class SimpleHistoryMachine extends AbstractHistoryMachine {
    protected int currentTick;

    protected SimpleHistoryMachine(List<HistoryUnit> cpu, List<HistoryUnit> io) {
        super(cpu, io);
        makeHeader();
    }

    void makeHeader() {
        StringBuilder builder = new StringBuilder().append("T | ");
        cpu.forEach(unit -> builder.append("CPU").append(unit.getId()).append(" | "));
        io.forEach(unit -> builder.append("IO").append(unit.getId()).append(" | "));
        history.add(builder.append("\n").toString());
    }

    private void scheduleTickStage() {
        BiConsumer<List<Process>, List<HistoryUnit>> schedule = (queue, units) -> {
            while (!queue.isEmpty()) {
                var process = Optional.of(queue.removeFirst());
                for (Unit unit : units) {
                    process = unit.schedule(process);
                    if (process.isEmpty()) break;
                }
                if (process.isPresent()) {
                    queue.addFirst(process.get());
                    break;
                }
            }
        };
        schedule.accept(cpuQueue, cpu);
        schedule.accept(ioQueue, io);
    }

    @Override
    public Optional<Process> schedule(Optional<Process> value) {
        value.ifPresent(cpuQueue::addLast);
        return Optional.empty();
    }

    @Override
    public Optional<Process> clean() {
        cpuQueue.addAll(io.stream().map(Unit::clean).filter(Optional::isPresent).map(Optional::get).filter(process1 -> !process1.isDone()).toList());
        ioQueue.addAll(cpu.stream().map(Unit::clean).filter(Optional::isPresent).map(Optional::get).filter(process1 -> !process1.isDone()).toList());
        return Optional.empty();
    }

    @Override
    public void tick() {
        scheduleTickStage();
        cpu.forEach(Unit::tick);
        io.forEach(Unit::tick);
        makeHistoryPoint();
        ++currentTick;
    }

    @Override
    public boolean isDone() {
        if (cpu.stream().anyMatch(unit -> !unit.isDone())) return false;
        if (io.stream().anyMatch(unit -> !unit.isDone())) return false;
        return cpuQueue.isEmpty() && ioQueue.isEmpty();
    }

    @Override
    public void makeHistoryPoint() {
        StringBuilder builder = new StringBuilder().append(currentTick).append(" ");
        Stream.of(cpu, io).forEach(units -> {
            builder.append("| ");
            units.forEach(unit -> builder.append(unit.getHistory().getLast()).append(" "));
        });
        history.addLast(builder.toString());
    }
}