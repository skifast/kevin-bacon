import java.util.ArrayList;

public class Vertex {
	String actor;
	ArrayList<Vertex> actors;
	int number;
	
	public Vertex(){
		actor = "";
		actors = new ArrayList<Vertex>(); 
	}
	
	
	public Vertex(int givenNumber){
		actor = "";
		number = givenNumber;
		actors = new ArrayList<>();
	}
	
	public Vertex(String actorName, int givenNumber){
		actor = actorName;
		number = givenNumber;
		actors = new ArrayList<Vertex>();
	}
	
	public void addActor(Vertex givenActor){
		actors.add(givenActor);
	}
	
	public int getNumber(){
		return number;
	}
	
	public String getName(){
		return actor; 
	}
	
	public ArrayList<Vertex> getActors(){
		return actors;
	}
	
	public void setName(String name){
		actor = name;
	}
	
//	public void visited(){
//		visited = true;
//	}
//	
//	public boolean isvisited(){
//		return visited;
//	}
}
