package machine;

import java.util.List;

public interface Historied {
    List<String> getHistory();

    void makeHistoryPoint();
}
