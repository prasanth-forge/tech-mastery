import java.util.List;
import java.util.stream.Collectors;

public class streams {
    List<String> names = List.of("Alice", "Bob", "Charlie");

    List<String> result = names.stream()
            .filter(n -> n.length() > 3)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
}
