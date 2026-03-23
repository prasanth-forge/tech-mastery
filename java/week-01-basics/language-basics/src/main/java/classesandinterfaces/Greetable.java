package classesandinterfaces;

public interface Greetable {
    String greet();

    default String greetLoudly() {
        return greet().toUpperCase();
    }
}