
public class DNA
{
    private double xspeed, yspeed;
    private double speed, health, hunger, stamina, staminaRecharge, attack, defense, size;
    private double[] stats = {speed, health, hunger, stamina, staminaRecharge, attack, defense, size};
    /*public DNA (int speed, int health, int hunger, int stamina, int staminaRecharge, int attack, int defense, int size)
    {
    xspeed = Math.random()*3+speed;
    yspeed = Math.random()*3+speed;
    this.health = health;
    this.hunger = hunger;
    this.stamina = stamina;
    this.staminaRecharge = staminaRecharge;
    this.attack = attack;
    this.defense = defense;
    this.size = size;
    }*/

    public DNA (int num)//biter = attack, aqua = ?, gooey = life, diatom = defense, buggy = speed, triangle = stamina
    { //for num: biter = 5, aqua = ?, gooey = 1, diatom = 6, buggy = 0, triangle = 3
        speed = Math.random()*3+3;
        health = (Math.random()*10+100);
        hunger = (Math.random()*2+1);
        stamina = (Math.random()*10+100);
        staminaRecharge = (Math.random()*2+3);
        attack = (Math.random()*2+12);
        defense = (Math.random()*2+5);
        size = 1;
        stats[num] = stats[num] * 1.2;
        //xspeed = Math.random()*3+speed;
        //yspeed = Math.random()*3+speed;
    }

    public double getSpeed ()
    {
        return speed;
    }

    public double getHealth ()
    {
        return health;
    }

    public double getHunger ()
    {
        return hunger;
    }

    public double getStamina ()
    {
        return stamina;
    }

    public double getStaminaRecharge ()
    {
        return staminaRecharge;
    }

    public double getAttack ()
    {
        return attack;
    }

    public double getDefense ()
    {
        return defense;
    }

    public double getSize ()
    {
        return size;
    }

    public void setStat (int num, double amount)
    {
        stats[num] += amount;
    }
    
    public void add (DNA dna)
    {
        speed += dna.getSpeed()/10;
        health += dna.getHealth()/10;
        hunger += dna.getHunger()/10;
        stamina += dna.getStamina()/10;
        staminaRecharge += dna.getStaminaRecharge()/10;
        attack += dna.getAttack()/10;
        defense += dna.getDefense()/10;
        size += dna.getSize()/10;
    }
}
