package cmpe202.parser;

import java.util.List;
import java.util.Map;

public interface Parser {
    Map<String, String> readRecord();
    void write(List<Map<String, String>> records);
}
