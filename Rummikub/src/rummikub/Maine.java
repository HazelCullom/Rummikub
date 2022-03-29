package rummikub;

import java.awt.Color;

import core.CustomAppearance;

public class Maine {
	
	public static final int ROWS = 15;
	
	public static final int COLS = 29;
	
	public static Game game;
	
	public static void main(String[] args) {
		
		coreRummikubGUI.RummikubPanel.useRummikubControls();
		
		createGameBoardAndPlayGame(ROWS, COLS, 4);
		
		core.API.close();
		
	}
	
	public static void createGameBoardAndPlayGame(int rows, int cols, int players) {
		CustomAppearance ca = new CustomAppearance(rows, cols);
		ca.setBorderColor(Color.BLACK);
		ca.setCellColor(Color.WHITE);
		core.API.initialize(ca);
		
		game = new Game(players);
		game.playGame();
	}
	
	
}
