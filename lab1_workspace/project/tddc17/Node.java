package tddc17;

import java.util.ArrayList;

public class Node {
	public int x;
	public int y;
	public ArrayList<Node> path;
	public Node(int x, int y){
		this.x = x;
		this.y = y;
		this.path = new ArrayList<Node>();
	}
	
	public String toString(){
		return "x: " + this.x + " y: " + this.y;
		
	}
}
