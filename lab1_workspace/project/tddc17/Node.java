package tddc17;


public class Node {
	public Node parent;
	public boolean explored;
	public int type;
	public int x;
	public int y;
	
	
	public Node(int type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "X: "+ this.x +" Y: " + this.y + " P: " + this.parent + " D: " + this.explored + " T: " + this.type;
	}
	
}
