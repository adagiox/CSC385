import java.util.ArrayList;
import java.util.List;

public class Forager extends Ant
{
	int mode;
	List<Node> history;

	public Forager(int id, Node currentNode)
	{
		this.id = id;
		this.daysUntilDeath = 365;
		this.type = 1;
		this.canAct = true;
		this.isAlive = true;
		this.currentNode = currentNode;
		this.hasFood = false;
		this.history = new ArrayList();
		this.mode = 0;
	}

	public AntEvent nextEvent()
	{
		if (this.canAct == true)
		{
			this.canAct = false;
			if (this.mode == 0)
			{
				Node dest = this.currentNode.getAdjacentPheromone();
				return (new AntEvent(this, this.currentNode, dest, AntEvent.ANT_MOVE_EVENT));
			}
			else
			{

			}
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
			this.canAct = true;
			this.daysUntilDeath--;
			if (this.daysUntilDeath <= 0)
			{
				return (new AntEvent(this, AntEvent.ANT_DEATH_EVENT));
			}
		}
		return null;
	}
}
