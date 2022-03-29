package rummikub;

import java.awt.Color;
import java.util.*;

public class Player {

	private ArrayList<Tile> hand;
	private int outCount;
	
	public Player() {
		this.hand = new ArrayList<Tile>();
		setOutCount(0);
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	public void resetHand() {
		this.hand = new ArrayList<Tile>();
	}
	
	public void setHand(ArrayList<Tile> hand) {
		this.hand = hand;
	}
	
	public Tile getPiece(int index) {
		return this.hand.get(index);
	}
	
	public Tile removePiece(int index) {
		return this.hand.remove(index);
	}
	
	public void addPiece(Tile piece) {
		this.hand.add(piece);
	}
	
	public String toString() {
		return hand.toString();
	}

	public int getOutCount() {
		return outCount;
	}

	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}
	
	public void sortHand() {
		
		for (int i = 0; i < getHandSize() - 1; i++) {
			
			int min = i;
			
			for (int j = i + 1; j < getHandSize(); j++) {
				Tile minT = getPiece(min);
				Tile jT = getPiece(j);
				
				if (jT.compareTo(minT) < 0) {
					min = j;
				}
				
			}
			
			Tile temp = hand.get(i);
			hand.set(i, hand.get(min));
			hand.set(min, temp);
			
			/*
			//swap value
			int temp = hand.get(i).getValue();
			hand.get(i).setValue(hand.get(min).getValue());
			hand.get(min).setValue(temp);
			// swaps color
			Color cTemp = hand.get(i).getColor();
			hand.get(i).setColor(hand.get(min).getColor());
			hand.get(min).setColor(cTemp);
			// swaps wild status
			boolean tempB = hand.get(i).isWild();
			hand.get(i).setWild(hand.get(min).isWild());
			hand.get(min).setWild(tempB);
			//dont need to swap played status because all are not played
			*/
		}
	}
}
