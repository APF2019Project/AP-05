package Main;

public class Shield {
    private String name;
    private int fullHp;
    private String material;

    public Shield(String name, int fullHp, String material) {
        this.name = name;
        this.fullHp = fullHp;
        this.material = material;
    }

    public boolean isMetal() {
        return (material.equals("metal"));
    }
    public String getName() {
        return name;
    }

    public int getFullHp() {
        return fullHp;
    }
}
