import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Runner{
	
	static FileIO fileReaderAndWriter;
	static HashMap<Integer, String> actors;
	static HashMap<Integer, String> movies;
	static HashMap<Integer, ArrayList<Integer>> moviesToActors;
	static String fileName1 = "actorsTest.txt";
	static String fileName2 = "moviesTest.txt";
	static String fileName3 = "movie-actorsTest.txt";

	static ArrayList<Integer> actorNumbers;
	static ArrayList<Vertex> processedActors;
	static ArrayList<Integer> connectedActors;
	
	public static void main(String[] args) {
		fileReaderAndWriter = new FileIO();
		
		//contains actors corresponding to integer codes
		actors = new HashMap<Integer, String>();
		actors = fileReaderAndWriter.readWords(fileName1);
		
		//contains movies corresponding to movie codes
		movies = new HashMap<Integer, String>();
		movies = fileReaderAndWriter.readWords(fileName2);
		
		//contains movies codes that correspond to arrayLists
		//of actor codes
		moviesToActors = new HashMap<Integer, ArrayList<Integer>>();
		moviesToActors = fileReaderAndWriter.readNums(fileName3);
		
		
		constructGraph();
		
		
		//pre condition for the input functionality: the actor's first
		//letter of their first and last name are capitilized. There is
		//a single space between the first and last names
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		String input = "";
		System.out.println("Enter an actor: ");
		try {
			input = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int b = findBaconNumber(input);
		if(b == 0){
			System.out.println("That actor is not connected to Kevin Bacon");
		}
		else{
			System.out.println("The Bacon Number for "  + input + " is " + b);
		}
		System.out.println("Would you like to see the whole graph?");
		
			try {
				input = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(input.equals("y")){
				display();
			}
			else{
				
			}
		
		
//		display();
//		System.out.println(findBaconNumber(""));
	}
	
	public static void constructGraph(){
		//this arrayList will be used to identify
		//whether or not a vertex exists by determining
		//if it contains a certain number
		actorNumbers = new ArrayList<Integer>();
		//this arrayList allows access to the vertexes
		//themselves, not just their number codes
		processedActors = new ArrayList<Vertex>();
		
		
		//because of the actorNumbers and processedActors arrayLists, 
		//all of the moviesToActors probably will not be accesed by
		//this iteration, instead they will be added through their 
		//conncted actors
		for(Integer i: moviesToActors.keySet()){
			
			//arraylist of actors will equal to the arrayList assigned under a particular movie number
			connectedActors = moviesToActors.get(i);
			
			//for each element in that arrayList
			for(int k = 0; k < connectedActors.size(); k++){
				
				//if the actor is not already present in the system
				if(!actorNumbers.contains((connectedActors.get(k)))){
					//create a new vertex with the proper name and code
					Vertex newVertex = new Vertex(actors.get(moviesToActors.get(i).get(k)), (moviesToActors.get(i).get(k)));
					//add its information to both lists, so the program
					//will know that it is present
					actorNumbers.add(newVertex.getNumber());
					processedActors.add(newVertex);
					
					//this for loop essentially makes sure that all connections
					//are realized. 
					for(int j = 0; j < connectedActors.size(); j++){
						if((connectedActors.get(j) == newVertex.getNumber())){
							break;
						}
						else if(actorNumbers.contains((connectedActors.get(j)))){
							Vertex currentVertex = null;
							for(int l = 0; l < processedActors.size(); l++){
								if(connectedActors.get(j) == processedActors.get(l).getNumber()){
									currentVertex = processedActors.get(l);
								}
							}
							//adds each Vertex to each other's lists
							if(!currentVertex.getActors().contains(newVertex)){
								currentVertex.addActor(newVertex);
							}
							if(!newVertex.getActors().contains(currentVertex)){
								newVertex.addActor(currentVertex);
							}
							//this would mena that there is already a node for this actor
						}
						else{
							//creates a new vertex if the connected actor does not exist
							Vertex newerVertex = new Vertex(actors.get(connectedActors.get(j)), connectedActors.get(j));
							actorNumbers.add(newerVertex.getNumber());
							processedActors.add(newerVertex);
							
							if(!newerVertex.getActors().contains(newVertex)){
								newerVertex.addActor(newVertex);
							}
							if(!newVertex.getActors().contains(newerVertex)){
								newVertex.addActor(newerVertex);
							}
							
						}
					}
				}
				//if the actor is present in the system
				else{
					Vertex v = null; 
					//find the actor
					for(int j = 0; j < processedActors.size(); j++){
						if(processedActors.get(j).getNumber() == connectedActors.get(k)){
							//gets a reference to the actor we want to analyze
							v = processedActors.get(j);
						}
					}
					
					//goes through the list of actors that share the same movie
					//and verifies that they are present in the connected actors list
					//their connections are especially important, since the whole program
					//relies on connection
					for(int j = 0; j < connectedActors.size(); j++){
						if(connectedActors.get(j) != v.getNumber()){
							if(!v.getActors().contains(connectedActors.get(j))){
								if(actorNumbers.contains(connectedActors.get(j))){
									Vertex currentVertex = null;
									for(int l = 0; l < processedActors.size(); l++){
										if(processedActors.get(l).getNumber() == connectedActors.get(j)){
											currentVertex = processedActors.get(l);
										}
									}
									if(!currentVertex.getActors().contains(v)){
										currentVertex.addActor(v);
									}
									if(!v.getActors().contains(currentVertex)){
										v.addActor(currentVertex);
									}
								}
								else{
									Vertex newVertex = new Vertex(actors.get(connectedActors.get(j)), connectedActors.get(j));
									actorNumbers.add(connectedActors.get(j));
									processedActors.add(newVertex);
									if(!v.getActors().contains(newVertex)){
										v.addActor(newVertex);
									}
									if(!newVertex.getActors().contains(v)){
										newVertex.addActor(v);
									}
								}
							}
						}
					}
				}

			}
		}
		
	}
	
	public static void display(){
		for(int i = 0; i < processedActors.size(); i++){
			System.out.print(processedActors.get(i).getName() + " is connected to these: ");
			for(int j = 0; j < processedActors.get(i).getActors().size(); j++){
				System.out.print(processedActors.get(i).getActors().get(j).getName() + " ");
			}
			System.out.println(" ");
			
		}
	}
	
	public static Vertex searchForVertex(String nameOfActor){
		ArrayList<Vertex> queue = new ArrayList<Vertex>();
		ArrayList<Vertex> checked = new ArrayList<Vertex>();
		Integer count = 0; 
		
		
		for(int i = 0; i < processedActors.size(); i++){
			count ++;
			queue.add(processedActors.get(i));
			while(!queue.isEmpty()){
				for(int j = 0; j < queue.get(0).getActors().size(); j++){
					if(!checked.contains(queue.get(0).getActors().get(j))){
						queue.add(queue.get(0).getActors().get(j));
						checked.add(queue.get(0).getActors().get(j));
					}
				}
				if(queue.get(0).getName().equals(nameOfActor)){
					return queue.get(0);
				}
				queue.remove(queue.get(0));
			}
		}
		return null;
		
	}
	
	public static int findBaconNumber(String nameOfActor){
		Vertex starting = searchForVertex("Kevin Bacon");
		ArrayList<Vertex> queue = new ArrayList<Vertex>();
		ArrayList<Vertex> checked = new ArrayList<Vertex>();
		int baconNumber = 0; 
		Vertex dummy = new Vertex();
		
		queue.add(starting);
		checked.add(starting);
		baconNumber = 0;
		while(!queue.isEmpty()){
			int extent = queue.size();
			
			for(int i = 0; i < extent; i++){
				for(int k = 0; k < queue.get(i).getActors().size(); k++){
					if(!checked.contains(queue.get(i).getActors().get(k))){
						queue.add(queue.get(i).getActors().get(k));
						checked.add(queue.get(i).getActors().get(k));
					}
				}
			}
			
			for(int i = 0; i < extent; i++){
				if(queue.get(i).getName().equals(nameOfActor)){
					return baconNumber;
				}
				queue.remove(queue.get(i));
				extent -= 1; 
			}
			baconNumber ++;
		}
		return 0;

	}
}
	
	
	
