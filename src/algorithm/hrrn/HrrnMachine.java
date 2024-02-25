package algorithm.hrrn;

import machine.computing.SimpleHistoryMachine;
import machine.computing.unit.HistoryUnit;
import machine.computing.unit.cpu.SimpleHistoryCpu;
import machine.computing.unit.io.SimpleHistoryIo;

import java.util.stream.IntStream;

public class HrrnMachine extends SimpleHistoryMachine {
    public HrrnMachine(int cpu, int io) {
        super(IntStream.range(0, cpu).mapToObj(id -> (HistoryUnit) new SimpleHistoryCpu(id)).toList(),
                IntStream.range(0, io).mapToObj(id -> (HistoryUnit) new SimpleHistoryIo(id)).toList());
    }
}
