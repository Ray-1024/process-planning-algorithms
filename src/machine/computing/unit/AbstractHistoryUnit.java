package machine.computing.unit;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractHistoryUnit extends AbstractUnit implements HistoryUnit {

    protected final List<String> history = new ArrayList<>();

    public AbstractHistoryUnit(int id) {
        super(id);
    }
}
