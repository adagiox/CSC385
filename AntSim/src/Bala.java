import java.util.List;
import java.util.Random;

public class Bala extends Ant
{

	public Bala(int id, Node currentNode)
	{
		this.id = id;
		this.daysUntilDeath = 365;
		this.type = 4;
		this.canAct = true;
		this.isAlive = true;
		this.currentNode = currentNode;
	}

	public AntEvent nextEvent()
	{
		if (canAct == true)
		{
			this.canAct = false;
			List<Ant> friendly = currentNode.getFriendlyAnts();
			if (friendly.size() > 0)
			{
				// attack random friendly ant
				Random rng = new Random();
				if (rng.nextInt(2) == 1)
					return (new AntEvent(friendly.get(rng.nextInt(friendly.size())), AntEvent.ANT_DEATH_EVENT));
			}
			else
				return (new AntEvent(this, currentNode, currentNode.getRandomAdjacentNode(), AntEvent.ANT_MOVE_EVENT));
		}
		return null;
	}

	public AntEvent reset(int turn)
	{
		if (turn != 10)
		{
			this.canAct = true;
		}
		else
		{
			this.daysUntilDeath--;
			if (this.daysUntilDeath <= 0)
			{
				return (new AntEvent(this, AntEvent.ANT_DEATH_EVENT));
			}
		}
		return null;
	}
}
