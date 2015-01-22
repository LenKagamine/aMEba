public class DNA{
	//private double speed, health, hunger, stamina, staminaRecharge, attack, defense, size;
	//private double[] stats = {speed, health, hunger, stamina, staminaRecharge, attack, defense, size};
	private double[] stats;

	public DNA(int num,int level){//for num: buggy = 0, gooey = 1, aqua = 2, biter = 3, diatom = 4, triangle = 8
		//0 = speed, 1 = health, 2 = stamina, 3 = attack, 4 = defense, 5 = hunger, 6 = staminaRecharge, 7 = size
		stats = new double[8];
		stats[0] = (Math.random()*3+3)*Math.sqrt(level);
		stats[1] = (Math.random()*10+100)*level;
		stats[2] = (Math.random()*10+100)*level;
		stats[3] = (Math.random()*2+12)*Math.sqrt(level);
		stats[4] = (Math.random()*2+5)*level;
		stats[5] = (Math.random()*2+1)*level;
		stats[6] = (Math.random()*2+3)*level;
		stats[7] = level;
		stats[num] *= 1.2;
	}

	public double getSpeed(){
		return stats[0];
	}

	public double getHealth(){
		return stats[1];
	}

	public double getStamina(){
		return stats[2];
	}

	public double getAttack(){
		return stats[3];
	}

	public double getDefense(){
		return stats[4];
	}

	public double getHunger(){
		return stats[5];
	}  

	public double getStaminaRecharge(){
		return stats[6];
	}   

	public double getSize(){
		return stats[7];
	}

	public void add(DNA dna){
		stats[0] += dna.getSpeed()/19;
		if (stats[0] >= 12)
			stats[0] = 12;
		stats[1] += dna.getHealth()/6; 
		stats[2] += dna.getStamina()/7;
		stats[3] += dna.getAttack()/10;
		stats[4] += dna.getDefense()/10;
		stats[5] += dna.getHunger()/10;
		stats[6] += dna.getStaminaRecharge()/10;
		stats[7] += dna.getSize()/10;
	}

	public void playerAdd(DNA dna){
		stats[0] += dna.getSpeed()/25;
		if (stats[0] >= 12)
			stats[0] = 12;
		stats[1] += dna.getHealth()/10; 
		stats[2] += dna.getStamina()/8.5;
		stats[3] += dna.getAttack()/15;
		stats[4] += dna.getDefense()/15;
		stats[5] += dna.getHunger()/7;
		stats[6] += dna.getStaminaRecharge()/15;
		stats[7] += dna.getSize()/15;
	}
}
