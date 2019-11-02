package mk.ukim.finki.av3;

abstract class Alien {
    protected int health; // 0=dead, 100=full strength
    protected String name;

    public Alien(int health, String name) {

        this.health = health;
        this.name = name;
    }

    public abstract int getDamage();


}

class SnakeAlien extends Alien {

    public SnakeAlien(int health, String name) {
        super(health, name);
    }

    @Override
    public int getDamage() {
        return 10;
    }
}

class OgreAlien extends Alien {

    public OgreAlien(int health, String name) {
        super(health, name);
    }

    @Override
    public int getDamage() {
        return 6;
    }
}

class MarshmallowManAlien extends Alien{

    public MarshmallowManAlien(int health, String name) {
        super(health, name);
    }

    @Override
    public int getDamage() {
        return 1;
    }
}


class AlienPack {
    private Alien[] aliens;

    public AlienPack(int numAliens) {
        aliens = new Alien[numAliens];
    }

    public void addAlien(Alien newAlien, int index) {
        aliens[index] = newAlien;
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public int calculateDamage() {
        int damage = 0;
        for (int i = 0; i < aliens.length; i++) {
            damage+=aliens[i].getDamage();
        }
        return damage;
    }
}

public class AlienTester {
}
