import java.util.Random;

public class Queen extends Ant
{
	boolean hasBirthed;
	boolean hasEaten;
	public Queen(int id, Node currentNode)
	{
		this.id = id;
		this.daysUntilDeath = 7300;
		this.type = 0;
		this.canAct = true;
		this.isAlive = true;
		this.currentNode = currentNode;
		this.hasBirthed = false;
		this.hasEaten = false;
	}

	public boolean eatFood()
	{
		if (currentNode.food > 0)
		{
			hasEaten = true;
			currentNode.food--;
		}
		else
			return (false);
		return true;
	}

	public int birthAnt()
	{
		Random rnd = new Random();
		this.hasBirthed = true;
		if (rnd.nextInt(2) == 1)
			return 1;
		else if (rnd.nextInt(2) == 1)
			return 2;
		else
			return 3;
	}

	public AntEvent nextEvent()
	{
		if (this.hasEaten == false)
		{
			if (this.eatFood() == false)
			{
				System.out.println("Queen died from starvation!");
				return (new AntEvent(AntEvent.QUEEN_DEATH_EVENT));
			}
		}
		if (this.hasBirthed == false)
			return (new AntEvent(currentNode, this.birthAnt(), AntEvent.ANT_CREATE_EVENT));
		return null;
	}

	public AntEvent reset(int turn)
	{
		if (turn != 10)
		{
			this.hasEaten = false;
		}
		else
		{
			this.hasEaten = false;
			this.hasBirthed = false;
			this.daysUntilDeath--;
			if (this.daysUntilDeath <= 0)
			{
				System.out.println("Queen died from old age!");
				return (new AntEvent(this, AntEvent.ANT_DEATH_EVENT));
			}
		}
		return null;
	}
}
