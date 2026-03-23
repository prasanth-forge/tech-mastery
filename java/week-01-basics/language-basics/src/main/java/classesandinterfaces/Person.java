package classesandinterfaces;

public class Person implements Greetable {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String greet() {
        return "Hello, I'm " + name;
    }
}
