package flights.entities;

public class Steward {
    private String name;

    public Steward(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Steward [name=" + name + "]";
    }
    
}
