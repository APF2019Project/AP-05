package Objects;

import Main.FieldNames;
import Main.JSONHandler;

import java.util.ArrayList;

public class Shield {
    private static ArrayList<Shield> allShields=new ArrayList<>();
    private String name;
    private int fullHp;
    private String material;
/*
    private Shield(String name, int fullHp, String material) {
        this.name = name;
        this.fullHp = fullHp;
        this.material = material;
        allShields.add(this);
    }
*/
    public boolean isFullBodyShield(){
        return false;
    }
    public Shield(JSONHandler jsonHandler) throws Exception {
        this.name = jsonHandler.getString(FieldNames.name).toLowerCase();
        this.fullHp = jsonHandler.getInt(FieldNames.fullHp);
        this.material = jsonHandler.getString(FieldNames.material);
        allShields.add(this);
    }

    public static Shield getShieldByName(String shieldName){
        for (Shield shield: allShields) {
            if (shield.getName().equals(shieldName)) {
                return shield;
            }
        }
        return null;
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
