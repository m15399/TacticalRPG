package level_intros;

import input.Button;
import input.Input;
import model.Game;
import model.GameObject;

public abstract class LevelIntro extends GameObject {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308373378020584182L;
	private Button button;
	
	public LevelIntro(){
		
		button = new Button(0,0,Game.WIDTH, Game.HEIGHT){
			

			/**
			 * 
			 */
			private static final long serialVersionUID = 1043605337380741641L;

			public void mouseReleased(){
				Game.getInstance().transitionTo(getNextRoot());
			}
		};
		
		Input.getInstance().addButton(button);

	}
	
	public void onDestroy(){
		Input.getInstance().removeButton(button);
	}
	
	public abstract GameObject getNextRoot();
	
}
