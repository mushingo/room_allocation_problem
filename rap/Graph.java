package rap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Graph {
	Boolean[][] matrix;
	ArrayList<Vertex> courses;
	int coursesNo;
	int totalRooms;
	ArrayList<ArrayList<Integer>> allLists;
	Integer[] list;
	int totalorders;
	int magnitude;
	int minTotalRooms;
	ArrayList<Integer> bestOrder;
	
	public Graph (String filename) throws Exception {
		Vertex course;
		ArrayList<Vertex> courses = new ArrayList<Vertex> ();
		allLists = new ArrayList<ArrayList<Integer>>();
		magnitude = 1;
		minTotalRooms = Integer.MAX_VALUE;
		this.courses = courses;
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		coursesNo = Integer.parseInt(reader.readLine());
		
		String line = reader.readLine();
		
		while (line != null) {
			course = new Vertex(line);
			courses.add(course);
			line = reader.readLine();
		}
		reader.close();

		
		for (int i = 0; i < coursesNo; i++) {
			course = courses.get(i); 
			for (int j = i + 1; j < coursesNo; j++) {
				course.clash(courses.get(j));
			}
		}		
	}
	
	public void greedy() {
		if (courses.size() == 0)
			return;
		Vertex course;
		totalRooms = 1;		
		courses.get(0).roomNo = totalRooms;
		ArrayList<Integer>bestOrder = new ArrayList<Integer>(); 
		int room;
		
		for (int i = 0; i < coursesNo; i++) {
			bestOrder.add(i);
			course = courses.get(i);
			room = course.smallestRoom(i);
			course.roomNo = room;
			if (room > totalRooms)
				totalRooms = room;
		}
		this.bestOrder = bestOrder;
		
		//Each edge has to be crossed twice, maxium number of edges * 2
	}
	
	public void greedyOrder(ArrayList<Integer> list) {
		//System.out.print("Testing " + list);
		if (courses.size() == 0)
			return;
		Vertex course;
		totalRooms = 1;		
		courses.get(0).roomNo = totalRooms;
		
		int room;
		
		for (int i = 0; i < coursesNo; i++) {
			course = courses.get(list.get(i));
			//System.out.print(course.courseCode);
			room = course.smallestRoom(i);
			course.roomNo = room;
			if (room > totalRooms)
				totalRooms = room;
		}
		//Each edge has to be crossed twice, maxium number of edges * 2
	}
	
	public void clearRooms(){
		for (int i = 0; i < courses.size(); i++) 
			courses.get(i).roomNo = 0;
	}
	
	public void perm(ArrayList<Integer> order, ArrayList<Integer>temp, boolean[] done, int len, int index, long startTime) {
		int i;
		
		if (index == len) {		
			allLists.add(new ArrayList<Integer>(temp));	
			this.clearRooms();
			greedyOrder(temp);			
			totalorders++;			
			if (totalRooms < minTotalRooms) {
				minTotalRooms = totalRooms;
				this.bestOrder = new ArrayList<Integer>(temp);
			}
			if ((totalorders % magnitude) == 0 && totalorders != 0) {
				System.out.printf("in %.2fs tested: %d \nContinuing...", (( System.nanoTime() - startTime) / 1000000000.0), totalorders);
				magnitude = magnitude*10;
			}
		}

		for (i = 0; i < len; i++) {
			if (!done[i]) {
				temp.add(order.get(i));
				done[i] = true;
				perm(order, temp, done, len, index + 1, startTime);
				done[i] = false;
				temp.remove(temp.size() - 1);
			}
		}
		
		greedyOrder(bestOrder);
								
		
	}
		
	
	

}
