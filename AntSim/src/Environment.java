import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Environment
{
	Node[][] environmentGrid;
	List<Node> edgeNodes;
	List<Node> pheromonesList;
	Colony antColony;
	public Environment()
	{
		environmentGrid = new Node[27][27];
		antColony = new Colony();
		edgeNodes = new ArrayList<>();
		pheromonesList = new ArrayList<>();
		for (int i = 0; i < 27; i++)
		{
			for (int j = 0; j < 27; j++)
			{
				if (i == 14 && j == 14)
					environmentGrid[i][j] = new Node(true);
				else
				{
					environmentGrid[i][j] = new Node(false);
					if (i == 0 || j == 0 || i == 26 || j == 26)
						edgeNodes.add(environmentGrid[i][j]);
				}
			}
		}
		for (int i = 0; i < 27; i++)
		{
			for (int j = 0; j < 27; j++)
			{
				List<Node> adj = new ArrayList<>();
				if (i - 1 >= 0 && j - 1 >= 0)
					adj.add(environmentGrid[i-1][j-1]);
				if (i - 1 >= 0)
					adj.add(environmentGrid[i-1][j]);
				if (i - 1 >= 0 && j + 1 <= 26)
					adj.add(environmentGrid[i-1][j+1]);
				if (j - 1 >= 0)
					adj.add(environmentGrid[i][j-1]);
				if (j + 1 <= 26)
					adj.add(environmentGrid[i][j+1]);
				if (i + 1 <= 26 && j - 1 >= 0)
					adj.add(environmentGrid[i+1][j-1]);
				if (i + 1 <= 26)
					adj.add(environmentGrid[i+1][j]);
				if (i + 1 <= 26 && j + 1 <= 26)
					adj.add(environmentGrid[i+1][j+1]);
				environmentGrid[i][j].setAdjacentNodes(adj);
			}
		}
	}

	public List<AntEvent> createStartingAnts()
	{
		List<AntEvent> starting = new ArrayList<>();
		starting.add(new AntEvent(this.environmentGrid[14][14], 0, AntEvent.ANT_CREATE_EVENT));
		for (int num = 0; num < 50; num++)
			starting.add(new AntEvent(this.environmentGrid[14][14], 1, AntEvent.ANT_CREATE_EVENT));
		for (int num = 0; num < 4; num++)
			starting.add(new AntEvent(this.environmentGrid[14][14], 2, AntEvent.ANT_CREATE_EVENT));
		for (int num = 0; num < 10; num++)
			starting.add(new AntEvent(this.environmentGrid[14][14], 3, AntEvent.ANT_CREATE_EVENT));
		return (starting);
	}

	public boolean moveAnt(Ant ant, Node source, Node dest)
	{
		if (dest == null || ant.isAlive == false)
			return false;
		ant.currentNode = dest;
		source.removeAnt(ant);
		dest.addAnt(ant);
		return true;
	}

	public AntEvent createBala()
	{
		Random rng = new Random();
		if (rng.nextInt(100) < 3)
			return (new AntEvent(edgeNodes.get(rng.nextInt(104)), 4, AntEvent.ANT_CREATE_EVENT));
		return null;
	}

	public List<AntEvent> checkPheromones()
	{
		List<AntEvent> pheromones = new ArrayList<>();
		for (Node n : pheromonesList)
		{
			if (n.pheromone > 0)
				pheromones.add(new AntEvent(n, AntEvent.PHEROMONE_DECREASED));
		}
		return pheromones;
	}

	public void printEnv()
	{
		System.out.println("Food Summary: ");
		int numEmpty = 0;
		int numNonempty = 0;
		for (Node[] i : environmentGrid)
		{
			for (Node n : i)
			{
				if (n.food == 0)
					numEmpty++;
				else
					numNonempty++;
			}
		}
		System.out.println("Empty Nodes: " + numEmpty);
		System.out.println("Nonempty Nodes: " + numNonempty);

	}

	public boolean resetEnvironment()
	{
		//reset the entire environment (nodes and colony)
		return true;
	}
}
