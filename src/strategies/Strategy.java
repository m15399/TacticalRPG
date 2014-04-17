package strategies;

import model.Level;
import model.Ship;

public interface Strategy {
	public void doNextAction(Ship ship, Level level);
}
