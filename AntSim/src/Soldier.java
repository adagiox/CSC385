import java.util.List;
import java.util.Random;

public class Soldier extends Ant
{

	public Soldier(int id, Node currentNode)
	{
		this.id = id;
		this.daysUntilDeath = 365;
		this.type = 3;
		this.canAct = true;
		this.isAlive = true;
		this.currentNode = currentNode;
	}

	public AntEvent nextEvent()
	{
		List<Ant> bala = currentNode.getBala();
		Random rng = new Random();
		if (this.canAct == true)
		{
			this.canAct = false;
			if (bala.size() > 0)
			{
				Ant balaAttack = bala.get(rng.nextInt(bala.size()));
				if (rng.nextInt(2) == 1)
					return (new AntEvent(balaAttack, AntEvent.ANT_DEATH_EVENT));
			}
			else
			{
				// move to a bala in adjacent node or move randomly
				List<Node> adjBala = currentNode.getVisibleAdjacentBala();
				if (adjBala.size() > 0)
					return (new AntEvent(this, currentNode, adjBala.get(rng.nextInt(adjBala.size())), AntEvent.ANT_MOVE_EVENT));
				else
				{
					Node dest = currentNode.getVisibleRandomAdjacentNode();
					if (dest == null)
						return null;
					return (new AntEvent(this, currentNode, dest, AntEvent.ANT_MOVE_EVENT));
				}
			}
		}
		return null;
	}

	public AntEvent reset(int turn)
	{
		if (turn != 10)
			this.canAct = true;
		else
		{
			this.canAct = true;
			this.daysUntilDeath--;
			if (this.daysUntilDeath <= 0)
				return (new AntEvent(this, AntEvent.ANT_DEATH_EVENT));
		}
		return null;
	}
}
