public class GunShot {
    private Gun gun;
    private int x, y;

    public GunShot(Gun gun, int x, int y) {
        this.gun = gun;
        this.x = x;
        this.y = y;
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
}
