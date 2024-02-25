import algorithm.fcfs.FcfsMachine;
import machine.computing.SimpleHistoryMachine;
import machine.process.Process;

public class Main {

    private static final String[] tasks = new String[]{
            "CPU(12);IO2(16);CPU(12);IO1(18);CPU(36);IO2(10);CPU(24);IO2(18);CPU(24);IO1(18)",
            "CPU(8);IO2(20);CPU(6);IO1(18);CPU(4);IO2(10);CPU(6);IO2(16);CPU(10);IO2(18);CPU(6);IO2(16)",
            "CPU(24);IO2(14);CPU(12);IO1(18);CPU(12);IO1(18);CPU(60);IO1(20);CPU(36);IO1(14);CPU(48);IO2(14)",
            "CPU(10);IO2(16);CPU(10);IO1(18);CPU(8);IO1(12);CPU(10);IO2(10);CPU(4);IO2(16);CPU(10);IO1(12)",
            "CPU(36);IO1(10);CPU(36);IO1(14);CPU(48);IO1(10);CPU(48);IO2(20);CPU(48);IO2(10);CPU(24);IO2(12)",
            "CPU(6);IO1(16);CPU(6);IO2(18);CPU(2);IO2(16);CPU(2);IO2(18);CPU(4);IO2(10);CPU(4);IO1(20)"
    };


    public static void main(String[] args) {
        SimpleHistoryMachine machine = new FcfsMachine();
        for (int tick = 0; ; ++tick) {
            if (tick < 11 && tick % 2 == 0) machine.schedule(Process.parse(tick >> 1, tasks[tick >> 1]));
            if (machine.isDone()) break;
            machine.tick();
            machine.clean();
        }
        System.out.println(String.join("\n", machine.getHistory()));
    }
}