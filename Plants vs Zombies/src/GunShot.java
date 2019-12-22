public class GunShot {
    private Gun gun;
    private int x, y;
    private int vx;

    public GunShot(Gun gun, int x, int y,int vx) {
        this.gun = gun;
        this.x = x;
        this.y = y;
        this.vx=vx;
    }

    public int getVx() {
        return vx;
    }
    public Gun getGun() {
        return gun;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void doAction(){

    }
}
