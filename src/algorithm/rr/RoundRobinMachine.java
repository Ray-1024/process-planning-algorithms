package algorithm.rr;

import machine.computing.SimpleHistoryMachine;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.io.SimpleHistoryIo;

import java.util.stream.IntStream;

public class RoundRobinMachine extends SimpleHistoryMachine {
    public RoundRobinMachine(int cpu, int io, int maximumTicks) {
        super(IntStream.range(0, cpu).mapToObj(id -> (HistoryUnit) new RoundRobinCpu(id, maximumTicks)).toList(),
                IntStream.range(0, io).mapToObj(id -> (HistoryUnit) new SimpleHistoryIo(id)).toList());
    }
}
