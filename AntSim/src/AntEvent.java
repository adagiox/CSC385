public class AntEvent
{
	public final static int ANT_MOVE_EVENT = 1;
	public final static int ANT_DEATH_EVENT = 2;
	public final static int ANT_CREATE_EVENT = 3;
	public final static int QUEEN_DEATH_EVENT = 4;
	public final static int NODE_REVEALED_EVENT = 5;
	public final static int PHEROMONE_INCREASED = 6;
	public final static int PHEROMONE_DECREASED = 7;


	int type;
	int antType;
	Ant ant;
	Node node;
	Node source;
	Node dest;

	// queen death
	public AntEvent(int type)
	{
		this.type = type;
	}

	// ant death
	public AntEvent(Ant ant, int type)
	{
		this.ant = ant;
		this.type = type;
	}

	// pheromones
	public AntEvent(Node node, int type)
	{
		this.node = node;
		this.type = type;
	}

	// ant move
	public AntEvent(Ant ant, Node source, Node dest, int type)
	{
		this.ant = ant;
		this.type = type;
		this.source = source;
		this.dest = dest;
	}

	// ant create
	public AntEvent(Node node, int antType, int type)
	{
		this.node = node;
		this.antType = antType;
		this.type = type;
	}
}
