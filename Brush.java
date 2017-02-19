package gameview;

import javafx.scene.paint.Color;
//Author: Catherine Yeager
public class Brush {
	Color color;
	int size;
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setColor(String c) {
		this.color= 
				c.toLowerCase()== "red" ? Color.RED:
				c.toLowerCase()=="blue" ? Color.BLUE:
				c.toLowerCase()=="black"? Color.BLACK:
				c.toLowerCase()== "purple" ? Color.PURPLE:
				c.toLowerCase()=="green" ? Color.GREEN:
				c.toLowerCase()=="bright green"? Color.YELLOWGREEN:
				c.toLowerCase()=="white" ? Color.WHITE:
				c.toLowerCase()=="pink" ? Color.PINK:
				c.toLowerCase()=="orange" ? Color.ORANGE:
				c.toLowerCase()=="yellow" ? Color.YELLOW:
					Color.BLACK;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
