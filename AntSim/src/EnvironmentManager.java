import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EnvironmentManager
{
	// manage the creation of bala ants at the edge nodes (AntEvent)
	// on each turn it should query every living ant for new events
	Queue<AntEvent> eventQueue;
	Environment env;
	public EnvironmentManager(Environment env)
	{
		this.eventQueue = new LinkedList<>();
		this.env = env;
		this.eventQueue.addAll(env.createStartingAnts());
		this.processEvents();
	}

	public boolean getEvents(int turn)
	{
		// gets the events for a turn and resets ants
		AntEvent event;
		List<AntEvent> newEvents;
		if ((event = env.createBala()) != null)
			eventQueue.add(event);
		if (turn == 10)
		{
			newEvents = env.checkPheromones();
			if ((newEvents.isEmpty()) == false)
				eventQueue.addAll(newEvents);
		}
		for (Ant ant : env.antColony.activeAnts)
		{
			while ((event = ant.nextEvent()) != null)
			{
				eventQueue.add(event);
				if (event.type == AntEvent.QUEEN_DEATH_EVENT)
					break ;
			}
			if ((event = ant.reset(turn)) != null)
				eventQueue.add(event);
		}
		return true;
	}

	public boolean processEvents()
	{
		// performs the event action and updates gui
		AntEvent event;
		while ((event = eventQueue.poll()) != null)
		{
			if (event.type == AntEvent.QUEEN_DEATH_EVENT)
			{
				//System.out.println("QUEEN_DEATH_EVENT");
				return false;
			}
			else if (event.type == AntEvent.ANT_MOVE_EVENT)
			{
				if (event.dest == null)
					continue;
				if (event.ant.type == 1)
				{
					if (event.ant.hasFood == true)
						eventQueue.add(new AntEvent(event.source, AntEvent.PHEROMONE_INCREASED));
					if (event.dest.food > 0 && event.ant.hasFood == false && event.dest.isEntrance == false)
					{
						event.ant.hasFood = true;
						event.dest.food--;
						event.ant.mode = 1;
						event.dest.nodeView.setFoodAmount(event.dest.food);
					}
					if (event.dest.isEntrance && event.ant.hasFood == true)
					{
						event.dest.food++;
						event.ant.hasFood = false;
						event.ant.mode = 0;
						event.dest.nodeView.setFoodAmount(event.dest.food);
					}
				}
				if (event.dest.isVisible == false && event.ant.type == 2)
					eventQueue.add(new AntEvent(event.dest, AntEvent.NODE_REVEALED_EVENT));
				//System.out.println("ANT_MOVE_EVENT: " + event.ant.type);
				env.moveAnt(event.ant, event.source, event.dest);
				if (event.ant.type == 1)
				{
					event.source.nodeView.setForagerCount(event.source.foragerCount);
					event.dest.nodeView.setForagerCount(event.dest.foragerCount);
				}
				else if (event.ant.type == 2)
				{
					event.source.nodeView.setScoutCount(event.source.scoutCount);
					event.dest.nodeView.setScoutCount(event.dest.scoutCount);
				}
				else if (event.ant.type == 3)
				{
					event.source.nodeView.setSoldierCount(event.source.soldierCount);
					event.dest.nodeView.setSoldierCount(event.dest.soldierCount);
				}
				else if (event.ant.type == 4)
				{
					event.source.nodeView.setBalaCount(event.source.balaCount);
					event.dest.nodeView.setBalaCount(event.dest.balaCount);
				}
			}
			else if (event.type == AntEvent.ANT_CREATE_EVENT)
			{
				env.antColony.createAnt(event.antType, event.node);
				if (event.antType == 1)
				{
					event.node.nodeView.setForagerCount(event.node.foragerCount);
				}
				else if (event.antType == 2)
				{
					event.node.nodeView.setScoutCount(event.node.scoutCount);
				}
				else if (event.antType == 3)
				{
					event.node.nodeView.setSoldierCount(event.node.soldierCount);
				}
				else if (event.antType == 4)
				{
					event.node.nodeView.setBalaCount(event.node.balaCount);
				}
			}
			else if (event.type == AntEvent.ANT_DEATH_EVENT)
			{
				if (event.ant.type == 0)
				{
					//System.out.println("Bala killed Queen!!!");
					return false;
				}
				if (event.ant.type == 1 && event.ant.hasFood)
				{
					event.ant.currentNode.food++;
					event.ant.currentNode.nodeView.setForagerCount(event.ant.currentNode.food);
				}
				env.antColony.destroyAnt(event.ant);
				if (event.antType == 1)
				{
					event.ant.currentNode.nodeView.setForagerCount(event.node.foragerCount);
				}
				else if (event.antType == 2)
				{
					event.ant.currentNode.nodeView.setScoutCount(event.node.scoutCount);
				}
				else if (event.antType == 3)
				{
					event.ant.currentNode.nodeView.setSoldierCount(event.node.soldierCount);
				}
				else if (event.antType == 4)
				{
					event.ant.currentNode.nodeView.setBalaCount(event.node.balaCount);
				}
			}
			else if (event.type == AntEvent.NODE_REVEALED_EVENT)
			{
				//System.out.println("NODE_REVEALED_EVENT");
				event.node.nodeView.showNode();
				event.node.isVisible = true;
			}
			else if (event.type == AntEvent.PHEROMONE_DECREASED)
			{
				//System.out.println("PHEROMONE_DECREASED");
				if ((event.node.pheromone /= 2) == 0)
					env.pheromonesList.remove(event.node);
				event.node.nodeView.setPheromoneLevel(event.node.pheromone);
				//System.out.println(event.node.pheromone);
			}
			else if (event.type == AntEvent.PHEROMONE_INCREASED)
			{
				if (!env.pheromonesList.contains(event.node))
					env.pheromonesList.add(event.node);
				//System.out.println("PHEROMONE_INCREASED");
				if (event.node.pheromone < 1000)
					event.node.pheromone += 10;
				event.node.nodeView.setPheromoneLevel(event.node.pheromone);
			}
		}
		return true;
	}

	public void updateAll()
	{
		for (Node[] a : env.environmentGrid)
		{
			for (Node n : a)
			{
				n.nodeView.showNode();
				n.nodeView.setForagerCount(n.foragerCount);
				n.nodeView.setScoutCount(n.scoutCount);
				n.nodeView.setSoldierCount(n.soldierCount);
				n.nodeView.setBalaCount(n.balaCount);
				n.nodeView.setPheromoneLevel(n.pheromone);
			}
		}
	}
}
