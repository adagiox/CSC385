abstract public class Ant
{
	public final static int RESET_TURN = 1;
	public final static int RESET_DAY = 2;

	int id;
	int daysUntilDeath;
	int type;
	boolean canAct;
	boolean isAlive;
	Node currentNode;

	abstract public AntEvent nextEvent();
	abstract public AntEvent reset(int type);
}
