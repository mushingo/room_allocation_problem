package adsa2p1;
import java.util.*;

public class Vertex {
	String courseCode;
	HashSet<Integer> times;
	int roomNo;
	ArrayList<Vertex> clashList;
	
	public Vertex(String line) {
		String[] list = line.split("\\s+");
		courseCode = list[0];
		roomNo = 0;
		
		times = new HashSet<Integer>();
		clashList = new ArrayList<Vertex>();
		int hour;
		
		for (int i = 1; i < list.length; i++) {
			hour = Integer.parseInt(list[i]); 
			while (hour < Integer.parseInt(list[i + 1])) {
				times.add(hour);
				hour++;
			}
			i++;
		}
	}
	
	public boolean clash(Vertex course) {
		if (Collections.disjoint(times, course.times))  
			return false;
		clashList.add(course);
		course.clashList.add(this);
		return true;
	}
	
	public int smallestRoom(int max) {
		int i, room = 1;
		HashSet<Integer> clashRooms = new HashSet<Integer>();
		
		for (i = 0; i < clashList.size(); i++) 
			clashRooms.add(clashList.get(i).roomNo);			
		
		while (clashRooms.contains(room)) 
			room++;
			
		return room;
	}
	
}
