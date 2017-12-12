import java.util.*;

public class Colony
{
	List<Ant> colonyAntList;
	List<Ant> activeAnts;
	int lastId;

	public Colony()
	{
		colonyAntList = new ArrayList<>();
		activeAnts = new ArrayList<>();
		this.lastId = -1;
	}

	public boolean createAnt(int type, Node node)
	{
		if (type == 0)
		{
			Ant newAnt = new Queen(lastId + 1, node);
			this.colonyAntList.add(newAnt);
			this.activeAnts.add(newAnt);
			node.addAnt(newAnt);
		}
		else if (type == 1)
		{
			Ant newAnt = new Forager(lastId + 1, node);
			this.colonyAntList.add(newAnt);
			this.activeAnts.add(newAnt);
			node.addAnt(newAnt);
		}
		else if (type == 2)
		{
			Ant newAnt = new Scout(lastId + 1, node);
			this.colonyAntList.add(newAnt);
			this.activeAnts.add(newAnt);
			node.addAnt(newAnt);
		}
		else if (type == 3)
		{
			Ant newAnt = new Soldier(lastId + 1, node);
			this.colonyAntList.add(newAnt);
			this.activeAnts.add(newAnt);
			node.addAnt(newAnt);
		}
		else if (type == 4)
		{
			Ant newAnt = new Bala(lastId + 1, node);
			this.colonyAntList.add(newAnt);
			this.activeAnts.add(newAnt);
			node.addAnt(newAnt);
		}
		this.lastId++;
		return true;
	}

	public boolean destroyAnt(Ant ant)
	{
		// ant possibly already died from another action
		if (ant.isAlive == false)
			return true;
		ant.isAlive = false;
		ant.currentNode.removeAnt(ant);
		ant.currentNode = null;
		return (this.activeAnts.remove(ant));
	}
}
