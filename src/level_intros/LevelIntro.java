package level_intros;

import utils.Observable;
import utils.Observer;
import actions.TimerAction;
import input.Button;
import input.Input;
import model.Game;
import model.GameObject;

public abstract class LevelIntro extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4346807789304208553L;
	private Game game;
	private Button button;
	
	public LevelIntro(Game newGame){
		this.game = newGame;
		
		button = new Button(0,0,Game.WIDTH, Game.HEIGHT){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1043605337380741641L;

			public void mouseReleased(){
				game.transitionTo(getNextRoot());
			}
		};
		
		Input.getInstance().addButton(button);
		button.disable();
		
		// disable the button for a second at first
		TimerAction timer = new TimerAction(60, new Observer(){
			public void notified(Observable sender){
				button.enable();
			}
		});
		addChild(timer);
		timer.start();
	}
	
	public Game getGame(){
		return game;
	}
	
	public void onDestroy(){
		Input.getInstance().removeButton(button);
	}
	
	public abstract GameObject getNextRoot();
	
}
