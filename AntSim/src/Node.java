import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node
{
	boolean isVisible;
	List<Ant> antList;
	int food;
	int pheromone;
	boolean isEntrance;
	List<Node> adjacentNodes;

	public Node(boolean entrance)
	{
		if (entrance == true)
		{
			this.isEntrance = true;
			this.isVisible = true;
			this.food = 1000;
		}
		else
		{
			Random foodAmount = new Random();
			if (foodAmount.nextInt(4) == 1)
				this.food = foodAmount.nextInt(500) + 500;
			else
				this.food = 0;
			this.isEntrance = false;
			this.isVisible = false;
		}
		this.adjacentNodes = null;
		this.pheromone = 0;
		this.antList = new ArrayList();
	}
	public boolean addAnt(Ant ant)
	{
		return (antList.add(ant));
	}

	public boolean removeAnt(Ant ant)
	{
		return (antList.remove(ant));
	}

	public List<Ant> getBala()
	{
		List<Ant> balaList = new ArrayList<>();
		for (Ant ant : antList)
		{
			if (ant.type == 4)
				balaList.add(ant);
		}
		return balaList;
	}

	public List<Node> getVisibleAdjacentBala()
	{
		List<Node> adjacentBalaList = new ArrayList<>();
		for (Node adj : adjacentNodes)
		{
			if (adj.isVisible == true)
			{
				for (Ant ant : adj.antList)
				{
					if (ant.type == 4)
					{
						adjacentBalaList.add(adj);
						break ;
					}
				}
			}
		}
		return adjacentBalaList;
	}

	public List<Ant> getFriendlyAnts()
	{
		List<Ant> friendly = new ArrayList<>();
		for (Ant ant : antList)
		{
			if (ant.type == 0 || ant.type == 1 || ant.type == 2 || ant.type == 3)
				friendly.add(ant);
		}
		return friendly;
	}

	public Node getVisibleRandomAdjacentNode()
	{
		Random rng = new Random();
		List<Node> adj = new ArrayList<>();
		for (Node node : adjacentNodes)
		{
			if (node.isVisible == true)
				adj.add(node);
		}
		if (adj.isEmpty())
			return null;
		return (adj.get(rng.nextInt(adj.size())));
	}

	public Node getRandomAdjacentNode()
	{
		Random rng = new Random();
		return (this.adjacentNodes.get(rng.nextInt(this.adjacentNodes.size())));
	}

	public Node getAdjacentPheromone(Node prev)
	{
		List<Node> max = new ArrayList<>();
		Random rng = new Random();
		int p = 0;
		for (Node n : adjacentNodes)
		{
			if (n == prev)
				continue;
			if (n.pheromone >= p)
			{
				max.add(n);
				p = n.pheromone;
			}
		}
		if (max.isEmpty())
			return null;
		return (max.get(rng.nextInt(max.size())));
	}

	public void setAdjacentNodes(List<Node> adjacentNodes)
	{
		this.adjacentNodes = adjacentNodes;
	}

	public void printNodeContent()
	{
//		System.out.println(isVisible);
//		for (Ant a : antList)
//		{
//			System.out.println("ID:" + a.id + "\nType:" + a.type);
//		}
//		System.out.println(food);
//		System.out.println(pheromone);
//		System.out.println(isEntrance);
		for (Node n : adjacentNodes)
		{
			System.out.println(n.isVisible);
		}
	}
}
