package algorithm.srt;

import algorithm.rr.RoundRobinCpu;
import machine.computing.SimpleHistoryMachine;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.cpu.SimpleHistoryCpu;
import machine.computing.unit.io.SimpleHistoryIo;
import machine.process.Process;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class SrtMachine extends SimpleHistoryMachine {
    public SrtMachine(int cpu, int io) {
        super(IntStream.range(0, cpu).mapToObj(id -> (HistoryUnit) new RoundRobinCpu(id, 1)).toList(),
                IntStream.range(0, io).mapToObj(id -> (HistoryUnit) new SimpleHistoryIo(id)).toList());
    }

    @Override
    protected void scheduleTickStage(Optional<Comparator<Process>> comparator) {
        Comparator<Process> tmp = Comparator.comparingInt(a -> a.getStages().getFirst().getTicks());
        super.scheduleTickStage(Optional.of(tmp));
    }
}
