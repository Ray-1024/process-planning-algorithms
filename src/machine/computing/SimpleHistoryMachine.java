package machine.computing;

import machine.StagedExecution;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.Unit;
import machine.process.Process;
import machine.process.stage.CpuStage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class SimpleHistoryMachine extends AbstractHistoryMachine {
    protected int currentTick;

    protected SimpleHistoryMachine(List<HistoryUnit> cpu, List<HistoryUnit> io) {
        super(cpu, io);
    }

    protected void scheduleTickStage(Optional<Comparator<Process>> comparator) {
        BiConsumer<List<Process>, List<HistoryUnit>> schedule = (queue, units) -> {
            while (!queue.isEmpty()) {
                comparator.ifPresent(queue::sort);
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
        Stream.concat(io.stream(), cpu.stream())
                .map(Unit::clean)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(process -> !process.isDone())
                .forEachOrdered(unit -> {
                    if (unit.getStages().getFirst() instanceof CpuStage) cpuQueue.addLast(unit);
                    else ioQueue.addLast(unit);
                });
        return Optional.empty();
    }

    @Override
    public void tick() {
        scheduleTickStage(Optional.empty());
        cpu.forEach(Unit::tick);
        cpuQueue.forEach(Process::waitingTick);
        io.forEach(Unit::tick);
        ioQueue.forEach(Process::waitingTick);
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
        StringBuilder builder = new StringBuilder().append(currentTick).append(" : ");
        Stream.of(cpu, io).forEach(units -> {
            units.forEach(unit -> builder.append(unit.getHistory().getLast()).append(" "));
            builder.append("| ");
        });
        history.addLast(builder.toString());
    }
}