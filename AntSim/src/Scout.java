public class Scout extends Ant
{

	public Scout(int id, Node currentNode)
	{
		this.id = id;
		this.daysUntilDeath = 365;
		this.type = 2;
		this.canAct = true;
		this.isAlive = true;
		this.currentNode = currentNode;
	}

	public AntEvent nextEvent()
	{
		if (this.canAct == true)
		{
			this.canAct = false;
			return (new AntEvent(this, this.currentNode, currentNode.getRandomAdjacentNode(), AntEvent.ANT_MOVE_EVENT));
		}
		return null;
	}

	public AntEvent reset(int turn)
	{
		if (turn != 10)
			this.canAct = true;
		else
		{
			this.daysUntilDeath--;
			if (this.daysUntilDeath <= 0)
				return (new AntEvent(this, AntEvent.ANT_DEATH_EVENT));
		}
		return null;
	}
}
