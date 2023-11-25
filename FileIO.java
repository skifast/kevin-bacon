import io.github.pixee.security.BoundedLineReader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class FileIO {
	//creates a hashmap of actors or movies to their integer codes
	public HashMap<Integer, String> readWords(String fileName){
		
		BufferedReader br = null;
		
		HashMap<Integer, String> stringAndNum = new HashMap<Integer, String>();

		try{
			File file = new File(fileName);
			
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			
			String line;
			
			while((line = BoundedLineReader.readLine(br, 5_000_000)) != null){
				String[] currentLine = line.split("\\|");
				Integer it = Integer.valueOf(currentLine[0]);
				String str = currentLine[1];
				stringAndNum.put(it, str);
			}

		} catch (IOException ioe){
			//the method calling this method has a condition
			//where if it recieves a null value, it knows that
			//the file given does not exist
			return null;
		}
		finally
		{
			try{
				if(br!=null)
					br.close();
			}catch(Exception ex){
				System.out.println("Error in closing the BufferedReader text");
			}
		}
		return stringAndNum;
	

	}
	
	
	//creates a hashmap for movies to the actors that 
	//are connected by them
	public HashMap<Integer, ArrayList<Integer>> readNums(String fileName){
		
		BufferedReader br = null;
		
		HashMap<Integer, ArrayList<Integer>> numAndNum = new HashMap<Integer, ArrayList<Integer>>();

		try{
			File file = new File(fileName);
			
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String line;
			
			while((line= BoundedLineReader.readLine(br, 5_000_000)) != null){
				String[] currentLine = line.split("\\|");
				Integer it1 = Integer.valueOf(currentLine[0]);
				Integer it2 = Integer.valueOf(currentLine[1]);
				if(numAndNum.containsKey(it1)){
					numAndNum.get(it1).add(it2);
				}
				else{
					ArrayList<Integer> x = new ArrayList<Integer>();
					x.add(it2);
					numAndNum.put(it1, x);
				}
			}

		} catch (IOException ioe){
			//the method calling this method has a condition
			//where if it recieves a null value, it knows that
			//the file given does not exist
			return null;
		}
		finally
		{
			try{
				if(br!=null)
					br.close();
			}catch(Exception ex){
				System.out.println("Error in closing the BufferedReader text");
			}
		}
		return numAndNum;

	}
	

	
}
