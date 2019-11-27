package game;

import java.awt.*;
import java.util.ArrayList;

public class Menu {
    private Menu lastMenu;
    private ArrayList<Button> button;



    public Menu getLastMenu() {
        return lastMenu;
    }

    public void setLastMenu(Menu lastMenu) {
        this.lastMenu = lastMenu;
    }
}
