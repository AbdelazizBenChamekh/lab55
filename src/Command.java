public interface Command {
    void execute(); // For commands without arguments
    default void execute(String arguments) {
        execute(); // Default behavior: call the no-argument version
    }

    public Command get(String commandName);

    void apply(String argument);
}

