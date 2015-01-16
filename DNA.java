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
    { //for num: buggy = 0, gooey = 1, aqua = 2, biter = 3, diatom = 4, triangle = 8
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

    public void boost ()
    {
	for (int i = 1; i<stats.length; i++) //so speed doesn't get boosted
	    if (i != 2) //so hunger doesn't get boosted
		setStat(i,getStat(i));
    }

    public void add (DNA dna)
    {
	stats[0] += dna.getStat(0)/19.0;
	if (stats[0] >= 12)
	    stats[0] = 12;
	stats[1] += dna.getStat(1)/6.0; 
	stats[2] += dna.getStat(2)/7.0;
	stats[3] += dna.getStat(3)/10.0;
	stats[4] += dna.getStat(4)/10.0;
	stats[5] += dna.getStat(5)/10.0;
	stats[6] += dna.getStat(6)/10.0;
	stats[7] += dna.getStat(7)/10.0;
    }
    
    public void playerAdd (DNA dna)
    {
	stats[0] += dna.getStat(0)/25.0;
	if (stats[0] >= 12)
	    stats[0] = 12;
	stats[1] += dna.getStat(1)/10.0; 
	stats[2] += dna.getStat(2)/10.0;
	stats[3] += dna.getStat(3)/15.0;
	stats[4] += dna.getStat(4)/15.0;
	stats[5] += dna.getStat(5)/15.0;
	stats[6] += dna.getStat(6)/15.0;
	stats[7] += dna.getStat(7)/15.0;
    }
}
