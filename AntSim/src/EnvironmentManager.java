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
	int numDead;
	public EnvironmentManager(Environment env)
	{
		this.eventQueue = new LinkedList<>();
		this.env = env;
		this.eventQueue.addAll(env.createStartingAnts());
		this.processEvents();
		this.numDead = 0;
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
				System.out.println("QUEEN_DEATH_EVENT");
				return false;
			}
			else if (event.type == AntEvent.ANT_MOVE_EVENT)
			{
				if (event.ant.type == 1)
				{
					if (event.ant.hasFood == true)
						eventQueue.add(new AntEvent(event.source, AntEvent.PHEROMONE_INCREASED));
					if (event.dest.food > 0 && event.ant.hasFood == false && event.dest.isEntrance == false)
					{
						event.ant.hasFood = true;
						event.dest.food--;
						event.ant.mode = 1;
					}
					if (event.dest.isEntrance && event.ant.hasFood == true)
					{
						event.dest.food++;
						event.ant.hasFood = false;
						event.ant.mode = 0;
						Forager f = (Forager)event.ant;
						//System.out.println(f.history.empty());
					}
				}
				if (event.dest.isVisible == false && event.ant.type == 2)
					eventQueue.add(new AntEvent(event.dest, AntEvent.NODE_REVEALED_EVENT));
				//System.out.println("ANT_MOVE_EVENT: " + event.ant.type);
				env.moveAnt(event.ant, event.source, event.dest);
			}
			else if (event.type == AntEvent.ANT_CREATE_EVENT)
			{
				//System.out.println("ANT_CREATE_EVENT: " + event.antType);
				env.antColony.createAnt(event.antType, event.node);
			}
			else if (event.type == AntEvent.ANT_DEATH_EVENT)
			{
				this.numDead++;
				if (event.ant.type == 0)
				{
					System.out.println("Bala killed Queen!!!");
					return false;
				}
				//System.out.println("ANT_DEATH_EVENT: " + event.ant.type);
				env.antColony.destroyAnt(event.ant);
			}
			else if (event.type == AntEvent.NODE_REVEALED_EVENT)
			{
				//System.out.println("NODE_REVEALED_EVENT");
				event.node.isVisible = true;
			}
			else if (event.type == AntEvent.PHEROMONE_DECREASED)
			{
				//System.out.println("PHEROMONE_DECREASED");
				if ((event.node.pheromone /= 2) == 0)
					env.pheromonesList.remove(event.node);
			}
			else if (event.type == AntEvent.PHEROMONE_INCREASED)
			{
				if (!env.pheromonesList.contains(event.node))
					env.pheromonesList.add(event.node);
				//System.out.println("PHEROMONE_INCREASED");
				if (event.node.pheromone < 1000)
					event.node.pheromone += 10;
			}
		}
		return true;
	}
}
