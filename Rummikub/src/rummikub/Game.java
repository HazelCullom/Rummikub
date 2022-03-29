package rummikub;

import java.awt.Color;
import java.util.*;

public class Game {

	/* TODO
	 * uh
	 * add way to cancel confirmation of turn end
	 * add AI
	 * add switching one of your tiles into a played wild tile
	 * sort tiles in hand add other way to sort and button to switch btwn both
	 * add console display in game window 
	 * yeah
	 * fix sleep
	 * shrink table if it has empty layers
	 * Change all colored numbers to images of tiles / generally make game look better
	 * gl fam
	 */
	private Player[] players;
	private ArrayList<Tile> pile;
	private int turn;
	private ArrayList<ArrayList<Tile>> table;
	private Tile pickedUp;
	private boolean hasMoved;
	private String displayText;
	
	public Game(int players) {
		this.players = new Player[players];
		this.pile = new ArrayList<Tile>();
		this.table = new ArrayList<ArrayList<Tile>>();
		this.turn = randomPlayer();
		this.pickedUp = null;
		this.hasMoved = false;
		this.displayText = "Rummikub";
	}
	
	public void playGame() {
		
		initialize();
		
		boolean gameOver = false;
		
		while (gameOver == false) {
			
			System.out.println("It is player " + this.turn + "'s turn!");
			colorSortHandOfCurrentPlayer();
			processCommands();
			
		}
	}
	
	public void processCommands() {
		
		Scanner con = new Scanner(System.in);
		hasMoved = false;
		int curTurn = this.turn;
		ArrayList<ArrayList<Tile>> tableBackup = new ArrayList<ArrayList<Tile>>();
		ArrayList<Tile> handBackup = new ArrayList<Tile>();
		
		// create a backup of the table, in case the user wants to reset their turn
		for (int i = 0; i < this.table.size(); i++) {
			tableBackup.add(new ArrayList<Tile>());
			for (int j = 0; j < this.table.get(i).size(); j++) {
				tableBackup.get(i).add(this.table.get(i).get(j));
			}
		}
		// create a backup of the player's hand, in case the user wants to reset their turn
		for (int i = 0; i < this.players[this.turn].getHandSize(); i++) {
			handBackup.add(this.players[this.turn].getPiece(i));
		}
		
		// player turn
		setDisplayText("Take your action: ");
		while (this.turn == curTurn) {
			draw();
			setDisplayText("");
			String str = con.nextLine();
			String[] com = str.split(" ");
			
			if (str.equals("help")) {
				
				printHelpText();
				
			}else if (str.equals("end")) {
				
				endTurn(con);
				
			} else if (com[0].equals("place")) {
				
				place(Integer.parseInt(com[1]), Integer.parseInt(com[2]), handBackup);
				
			} else if (com[0].equals("take")) {
				
				take(Integer.parseInt(com[1]), Integer.parseInt(com[2]), con);
				
			} else if (str.equals("reset")) {
				
				resetTable(tableBackup, handBackup);
				
			} else if (str.equals("print")) {
				
				printTiles();
				
			} else if (str.equals("green") || str.equals("red") || str.equals("blue") || str.equals("black")) {
				if (isPickedUp() && this.pickedUp.isWild() && !this.pickedUp.isPlayed()) {
					this.pickedUp.setColor(stringToColor(str));
				} else if (!isPickedUp()) {
					setDisplayText("Pick up a wild peice to change it");
				} else {
					setDisplayText("Can't change that piece");
				}
			} else if (str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9") || str.equals("10") || str.equals("11") || str.equals("12") || str.equals("13")) {
				if (isPickedUp() && this.pickedUp.isWild() && !this.pickedUp.isPlayed()) {
					this.pickedUp.setValue(Integer.parseInt(str));
				} else if (!isPickedUp()) {
					setDisplayText("Pick up a wild peice to change it");
				} else {
					setDisplayText("Can't change that piece");
				}
			}
		}
	}
	
	public void setDisplayText(String text) {
		this.displayText = text;
		System.out.println(this.displayText);
	}
	
	public void resetTable(ArrayList<ArrayList<Tile>> tableBackup, ArrayList<Tile> handBackup) {
		this.table = new ArrayList<ArrayList<Tile>>();
		this.players[this.turn].resetHand();
		
		for (int i = 0; i < tableBackup.size(); i++) {
			this.table.add(new ArrayList<Tile>());
			for (int j = 0; j < tableBackup.get(i).size(); j++) {
				this.table.get(i).add(tableBackup.get(i).get(j));
			}
		}
		for (int i = 0; i < handBackup.size(); i++) {
			if (handBackup.get(i).isWild()) {
				handBackup.get(i).setValue(0);
				handBackup.get(i).setColor(Color.MAGENTA);
			}
			
			this.players[this.turn].addPiece(handBackup.get(i));
		}
		this.pickedUp = null;
		hasMoved = false;
	}
	
	public void take(int group, int index, Scanner con) {
		if (isPickedUp()) {
			
			setDisplayText("You already have a piece picked up");
			
		} else {
			
			if (isTile(group, index) == 1) {
				
				this.pickedUp = this.table.get(group).remove(index);
				setDisplayText("Took a " + this.pickedUp + " from the table");
				hasMoved = true;
				
			} else if (isTile(group, index) == 2) {
				
				this.pickedUp = this.players[this.turn].removePiece(group);
				draw();
				if (this.pickedUp.isWild()) {
					
					setDisplayText("Took a wild tile from your hand");
					
					setDisplayText("Choose wild card color");
					String colorS = con.nextLine();
					while (!colorS.equals("red") && !colorS.equals("blue") && !colorS.equals("green") && !colorS.equals("black")) {
						System.out.println("\"" + colorS + "\" is not a valid color\nValid colors are: red, green, blue, or black");
						System.out.print(" Choose wild card color");
						colorS = con.nextLine();
					}
					Color color = stringToColor(colorS);
					
					setDisplayText("Choose wild card value");
					int value = Integer.parseInt(con.nextLine());
					while (value < 1 || value > 13) {
						System.out.println("That is not a valid value\nValid values are 1-13");
						System.out.print("Choose wild card value: ");
						value = Integer.parseInt(con.nextLine());
					}
					
					this.pickedUp.setColor(color);
					this.pickedUp.setValue(value);
					
				} else {
					setDisplayText("Took a " + this.pickedUp + " from your hand");
				}
				hasMoved = true;
			} else {
				
				setDisplayText("There is no tile there");
				
			}
		}
	}
	
	public void place(int group, int index, ArrayList<Tile> handBackup) {
		if (isPickedUp()) {
			//add thing to check if player is putting piece back in hand
			if (index >= Maine.ROWS - 2) {
				
				if (!this.pickedUp.isPlayed()) {
					if (this.pickedUp.isWild()) {
						this.pickedUp.setColor(Color.MAGENTA);
						this.pickedUp.setValue(0);
					}
					this.players[this.turn].addPiece(this.pickedUp);
					setDisplayText("Placed a " + this.pickedUp + " into your hand");
					this.pickedUp = null;
					colorSortHandOfCurrentPlayer();
					if (handBackup.size() == this.players[this.turn].getHandSize()) {
						hasMoved = false;
					}
					
				} else {
					setDisplayText("That piece isn't yours");
				}
				return;
			}
			
			if (group >= this.table.size()) {
				group = this.table.size();
				this.table.add(new ArrayList<Tile>());
			}
			if (index >= this.table.get(group).size()) {
				index = this.table.get(group).size();
			}
			
			this.table.get(group).add(index, this.pickedUp);
			setDisplayText("Placed a " + this.pickedUp + " on the table");
			this.pickedUp = null;
			hasMoved = true;
			if (handBackup.size() == this.players[this.turn].getHandSize()) {
				hasMoved = false;
			}
			
		} else {
			setDisplayText("You don't have any piece selected");
		}
	}

	public void endTurn(Scanner con) {
		if (hasMoved) {
			// checks if board is valid and if there's any floating pieces remaining
			if (isValid() && !isPickedUp()) {
				setDisplayText("The board is valid. \nPress end button again to end your turn");
				con.nextLine();
				nextTurn();
			} else if (isValid()) {
				setDisplayText("You can't be holding a piece, put it down and try again");
			} else if (!isPickedUp()) {
				setDisplayText("The board is not valid, please fix it");
			} else {
				setDisplayText("The board is not valid and you can't be holding a piece, put it down and try again");
			}
		} else {
			if (this.pile.isEmpty()) {
				setDisplayText("The pile is empty. \nPress end button again to end your turn");    // so player " + this.turn + " doesn't pick a piece from the pile and ends their turn");
			} else {
				//setDisplayText("Player " + this.turn + " picks a piece from the pile and ends their turn");
				Tile pick = this.pile.remove(randomPieceIndex());
				this.players[this.turn].addPiece(pick);
				setDisplayText("You picked a " + pick + ". \nPress end button again to end your turn");
				draw();
			}
			con.nextLine();
			nextTurn();
		}
	}
	
	public void printHelpText() {
		System.out.println("-----------------------COMMANDS-----------------------");
		System.out.println("\"take 0 3\": picks up the piece from the table at group 0, index 3");
		System.out.println("\"place 0 3\": places the currently held piece onto the table at group 0, index 3");
		System.out.println("\"end\": ends your turn. If no piece was played, picks a piece and adds it to your hand");
		System.out.println("\"reset\": resets the table to how it was at the beginning of your turn");
		System.out.println("\"print\": prints the state of the game");
		System.out.println("------------------------------------------------------");
	}
	
	public boolean isPickedUp() {
		
		if (this.pickedUp == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public int isTile(int col, int row) {
		// returns 1 if on table, 2 if in hand, -1 if not there
		if (col < this.table.size() && row < this.table.get(col).size()) {
			return 1;
		} else if (row == Maine.ROWS - 1 && col < this.players[this.turn].getHandSize()) {
			return 2;
		}
		return -1;
	}
	
	public void draw() {
		//clear grid
		for (int r = 0; r < Maine.ROWS; r++) {
			for (int c = 0; c < Maine.COLS; c++) {
				core.API.paintSolidColor(r, c, Color.WHITE);
			}
		}
		
		//draw table
		for (int i = 0; i < this.table.size(); i++) {
			for (int j = 0; j < this.table.get(i).size(); j++) {
				
				if (this.table.get(i).get(j).isWild()) {
					core.API.drawText(j, i, this.table.get(i).get(j).getValue()+ "",
							wildColor(this.table.get(i).get(j).getColor()));
				} else {
					core.API.drawText(j, i, this.table.get(i).get(j).getValue() + "",
							this.table.get(i).get(j).getColor());
				}
				
			}
		}
		//draw hand
		for (int i = 0; i < this.players[this.turn].getHandSize(); i++) {
			
			if (this.players[this.turn].getPiece(i).isWild()) {
				core.API.drawText(Maine.ROWS - 1, i, "W",
						this.players[this.turn].getPiece(i).getColor());
			} else {
				core.API.drawText(Maine.ROWS - 1, i, this.players[this.turn].getPiece(i).getValue() + "",
						this.players[this.turn].getPiece(i).getColor());
			}
		}
		
		//draw separator
		for (int c = 0; c < Maine.COLS; c++) {
			core.API.paintSolidColor(Maine.ROWS - 2, c, Color.BLACK);
		}
		
		//draw picked up piece if there is one
		if (isPickedUp()) {
			if (this.pickedUp.isWild()) {
				core.API.drawText(Maine.ROWS - 2, Maine.COLS / 2, this.pickedUp.getValue() + "",
						wildColor(this.pickedUp.getColor()));
			} else {
				core.API.drawText(Maine.ROWS - 2, Maine.COLS / 2, this.pickedUp.getValue() + "",
						this.pickedUp.getColor());
			}
		}
		
	}
	
	public Color wildColor(Color c) {
		Color wildColor = Color.BLACK;
		
		if (c.equals(Color.BLUE)) {
			wildColor = Color.CYAN;
		} else if (c.equals(Color.GREEN)) {
			wildColor = new Color(43, 161, 13); // dark green
		} else if (c.equals(Color.RED)) {
			wildColor = Color.PINK;
		} else if (c.equals(Color.BLACK)) {
			wildColor = Color.GRAY;
		}
		return wildColor;
	}
	
	public boolean isValid() {
		// is the table valid?
		colorSortTable();
		draw();
		
		for (int i = 0; i < table.size(); i++) {
			
			if ((table.get(i).size() < 3 || table.get(i).size() > 13) && table.get(i).size() != 0) {
				return false;
			}
			
			boolean isRun = false;
			
			for (int j = 1; j < table.get(i).size(); j++) {

				// check if set or run with first two tiles: if colors equal, run, if not, set
				if(j == 1) {
					if (table.get(i).get(j).getColor().equals(table.get(i).get(j - 1).getColor())) {
						isRun = true;
					} else {
						isRun = false;
					}
				}
				
				if (isRun) {
					// if its a run, check if values are not correct or if colors are different
					if (table.get(i).get(j).getValue() != table.get(i).get(j - 1).getValue() + 1 ||
							!table.get(i).get(j).getColor().equals(table.get(i).get(j - 1).getColor())) {
						return false;
					}
				} else {
					// if its a set, check if colors are the same or if values are different
					if (table.get(i).get(j).getValue() != table.get(i).get(j - 1).getValue() ||
							table.get(i).get(j).getColor().equals(table.get(i).get(j - 1).getColor())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void nextTurn() {
		for (int i = 0; i < 100; i++) {
			System.out.println();
		}
		
		//change all unplayed tiles on table to played tiles
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).size(); j++) {
				table.get(i).get(j).setPlayed(true);
			}
		}
		
		turn = (turn + 1) % 4;
	}
	
	public void initialize() {
		fillPile();
		createPlayers();
		shufflePile();
		dealHands();
	}
	
	public int randomPlayer() {
		return (int)(Math.random() * this.players.length);
	}
	
	public void printTiles() {
		//System.out.println("Pile: " + this.pile);
		System.out.println("Picked Up: " + this.pickedUp);
		System.out.println("Hand: " + this.players[this.turn]);
		System.out.println("Table: " + this.table);
	}
	
	public void dealHands() {
		for (int i = 0; i < this.players.length; i++) {
			for (int j = 0; j < 14; j++) {
				this.players[i].addPiece(this.pile.remove(randomPieceIndex()));
			}
		}
	}
	
	public int randomPieceIndex() {
		return (int)(Math.random() * this.pile.size());
	}
	
	public void shufflePile() {
		for (int i = 0; i < this.pile.size(); i++) {
			int r = randomPieceIndex();
			
			Tile temp = this.pile.get(i);
			this.pile.set(i, this.pile.get(r));
			this.pile.set(r, temp);
			
			/*
			//swap value
			int temp = this.pile.get(i).getValue();
			pile.get(i).setValue(pile.get(r).getValue());
			pile.get(r).setValue(temp);
			// swaps color
			Color cTemp = pile.get(i).getColor();
			pile.get(i).setColor(pile.get(r).getColor());
			pile.get(r).setColor(cTemp);
			// swaps wild status
			boolean tempB = pile.get(i).isWild();
			pile.get(i).setWild(pile.get(r).isWild());
			pile.get(r).setWild(tempB);
			//dont need to swap played status because all are not played
			*/
		}
	}
	
	public void createPlayers() {
		this.players[0] = new Player();
		for (int i = 1; i < this.players.length; i++) {
			this.players[i] = new AI();
		}
	}
	
	public Color stringToColor(String str) {
		Color color = null;
		
		if (str.equals("red")) {
			color = Color.RED;
		} else if (str.equals("green")) {
			color = Color.GREEN;
		} else if (str.equals("blue")) {
			color = Color.BLUE;
		} else if (str.equals("black")) {
			color = Color.BLACK;
		} 
		
		return color;
	}
	
	public void fillPile() {
		for (int i = 0; i < 104; i++) {
			
			Color color;
			if (i / 13 < 2) {
				color = Color.GREEN;
			} else if (i / 13 < 4) {
				color = Color.RED;
			} else if (i / 13 < 6) {
				color = Color.BLUE;
			} else {
				color = Color.BLACK;
			}
				
			Tile toAdd = new Tile((i % 13) + 1, color);
			this.pile.add(toAdd);
		}
		// add special pieces
		this.pile.add(new Tile(0, Color.MAGENTA, true));
		this.pile.add(new Tile(0, Color.MAGENTA, true));
	}

	public void colorSortTable() {
		for (int g = 0; g < this.table.size(); g++) {
			for (int i = 0; i < this.table.get(g).size() - 1; i++) {
				
				int min = i;
				
				for (int j = i + 1; j < this.table.get(g).size(); j++) {
					Tile minT = this.table.get(g).get(min);
					Tile jT = this.table.get(g).get(j);
					
					if (jT.compareTo(minT) < 0) {
						min = j;
					}
					
				}
				
				Tile temp = this.table.get(g).get(i);
				this.table.get(g).set(i, this.table.get(g).get(min));
				this.table.get(g).set(min, temp);
				
				/*
				//swap value
				int temp = this.table.get(g).get(i).getValue();
				this.table.get(g).get(i).setValue(this.table.get(g).get(min).getValue());
				this.table.get(g).get(min).setValue(temp);
				// swaps color
				Color cTemp = this.table.get(g).get(i).getColor();
				this.table.get(g).get(i).setColor(this.table.get(g).get(min).getColor());
				this.table.get(g).get(min).setColor(cTemp);
				// swaps wild status
				boolean tempB = this.table.get(g).get(i).isWild();
				this.table.get(g).get(i).setWild(this.table.get(g).get(min).isWild());
				this.table.get(g).get(min).setWild(tempB);
				// swaps played status
				tempB = this.table.get(g).get(i).isPlayed();
				this.table.get(g).get(i).setPlayed(this.table.get(g).get(min).isPlayed());
				this.table.get(g).get(min).setPlayed(tempB);
				*/
			}
		}
	}
	
	public void colorSortHandOfCurrentPlayer() {
		this.players[this.turn].sortHand();
	}
	
	public String getDisplayText() {
		return this.displayText;
	}
	
}
