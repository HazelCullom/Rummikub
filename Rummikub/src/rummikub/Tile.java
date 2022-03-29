package rummikub;

import java.awt.Color;

public class Tile {

	private int value;
	private Color color;
	private boolean wild;
	private boolean played;
	
	public Tile(int value, Color color) {
		this.setValue(value);
		this.setColor(color);
		this.setWild(false);
		this.setPlayed(false);
	}
	public Tile(int value, Color color, boolean wild) {
		this(value, color);
		this.setWild(wild);
		this.setPlayed(false);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public String toString() {
		String colorName = "";
		if (wild) {	
			return "Wild";
		}else if (this.color.equals(Color.GREEN)) {
			colorName = "Green";
		} else if (this.color.equals(Color.RED)) {
			colorName = "Red";
		} else if (this.color.equals(Color.BLUE)) {
			colorName = "Blue";
		} else if (this.color.equals(Color.BLACK)) {
			colorName = "Black";
		}
		
		return colorName + " " + value;
	}

	public int compareTo(Tile other) {
		
		int t = this.getValue();
		int o = other.getValue();
		
		if (this.getColor() == Color.BLUE) {
			t += 13;
		} else if (this.getColor() == Color.GREEN) {
			t += 26;
		} else if (this.getColor() == Color.RED) {
			t += 39;
		} else if (this.getColor() == Color.BLACK) {
			t += 52;
		}
		if (other.getColor() == Color.BLUE) {
			o += 13;
		} else if (other.getColor() == Color.GREEN) {
			o += 26;
		} else if (other.getColor() == Color.RED) {
			o += 39;
		} else if (other.getColor() == Color.BLACK) {
			o += 52;
		}
		
		return t - o;
	}

	public boolean isWild() {
		return wild;
	}

	public void setWild(boolean wild) {
		this.wild = wild;
	}
	public boolean isPlayed() {
		return played;
	}
	public void setPlayed(boolean played) {
		this.played = played;
	}
	
	
}
