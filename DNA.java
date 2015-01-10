public class DNA{
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
    { //for num: biter = 5, aqua = 3, gooey = 1, diatom = 6, buggy = 0, triangle = 3
	stats[0] = (Math.random()*3)+3;
	stats[1] = (Math.random()*10+100);
	stats[2] = (Math.random()*10+100);
	stats[3] = (Math.random()*2+12);
	stats[4] = (Math.random()*2+5);
	stats[5] = (Math.random()*2+1);
	stats[6] = (Math.random()*2+3);
	stats[7] = 1;
	stats[num] *= 1.2;
    }

    public int getSpeed ()
    {
	return (int) stats[0];
    }

    public int getHealth ()
    {
	return (int)stats[1];
    }
    public int getStamina ()
    {
	return (int)stats[2];
    }
    public int getAttack ()
    {
	return (int)stats[3];
    }

    public int getDefense ()
    {
	return (int)stats[4];
    }
    public int getHunger ()
    {
	return (int)stats[5];
    }  

    public int getStaminaRecharge ()
    {
	return (int)stats[6];
    }   

    public int getSize ()
    {
	return (int)stats[7];
    }

    public double getStat (int num)
    {
	return (double) stats[num];
    }

    public void setStat (int num, double amount)
    {
	stats[num] += amount;
    }

    public void add (DNA dna)
    {
	stats[0] += dna.getSpeed()/15.0;
	if (stats[0] >= 12)
	    stats[0] = 12;
	stats[1] += dna.getHealth()/6.0; 
	stats[2] += dna.getHunger()/6.0;
	stats[3] += dna.getStamina()/10.0;
	stats[4] += dna.getStaminaRecharge()/10.0;
	stats[5] += dna.getAttack()/10.0;
	stats[6] += dna.getDefense()/10.0;
	stats[7] += dna.getSize()/10.0;
    }
}
