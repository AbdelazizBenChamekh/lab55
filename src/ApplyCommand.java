
import java.util.List;

public interface ApplyCommand extends Command {
    void apply(String argument);
    void apply(List<String> arguments);
}


