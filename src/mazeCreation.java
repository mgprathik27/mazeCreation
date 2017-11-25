import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class mazeCreation{

	public static void printMaze(int mazeRows,int mazeColumns, ArrayList<wall> wallsList) {
		for (int j = 0; j < mazeColumns; j++) {
			if (j==0) {
				System.out.print("   ");
			}else
			System.out.print("_ ");
		}
		System.out.println("");
		for (int i = 0; i < mazeRows; i++) {
			for (int j = 0; j < mazeColumns; j++) {
				boolean vbar=false,hbar=false;
				if (j==0 && i!=0)
					System.out.print("|");
				else if(j==0 && i==0)
					System.out.print(" ");
				for (wall k:wallsList) {
					
					if (k.cell1() ==j + i * mazeColumns && k.cell2()==j + (i + 1) * mazeColumns) {
						hbar=true;
					}
					if (k.cell1() ==j + i * mazeColumns && k.cell2()==j + i * mazeColumns + 1) {
						vbar=true;
					}
				}
				
				if (hbar || (i==mazeRows-1 && j!=mazeColumns-1 ))
					System.out.print("_");
				else 
					System.out.print(" ");
				if (vbar )
					System.out.print("|");
				else if(j!=mazeColumns-1)
					System.out.print(" ");

			}
			if(i!=mazeRows-1)
			System.out.println("|");
		}
		System.out.println("");
	}

	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		int mazeRows = 0, mazeColumns = 0;
		Random rand = new Random();
		ArrayList<wall> wallsList = new ArrayList<>();

		while (true) {
			System.out.print("Please Enter the number of Rows of the maze : ");
			mazeRows = in.nextInt();
			System.out.print("Please Enter the number of Columns of the maze: ");
			mazeColumns = in.nextInt();
			if (mazeRows <= 0 || mazeColumns <= 0) {
				System.out.println("Not valid values, Please enter again ");
			} else
				break;
		}
		in.close();

		DisjSets disjet = new DisjSets(mazeRows * mazeColumns);

		for (int i = 0; i < mazeRows * mazeColumns; i++) {

			if ((i + mazeColumns) < (mazeRows * mazeColumns)) {
				wall w = new wall(i, i + mazeColumns);
				wallsList.add(w);
			}
			if ((i + 1) % mazeColumns != 0) {
				wall w = new wall(i, i + 1);
				wallsList.add(w);
			}

		}
		System.out.println("Original Maze : ");
		int numOfIterations=0;
		while (wallsList.size() != 0 ) {
			
			printMaze(mazeRows,mazeColumns, wallsList);
			boolean cont = true;
			System.out.println("");
			int randomWall = rand.nextInt(999999999)%wallsList.size();

			wall w = wallsList.get(randomWall);
			int cell1 = w.cell1();
			int cell2 = w.cell2();
			if (disjet.find(cell1) != disjet.find(cell2)) {
				disjet.union(disjet.find(cell1), disjet.find(cell2));
				wallsList.remove(randomWall);
				numOfIterations++;
			}
			

			for (int i =0;i<mazeRows * mazeColumns ;i++) {
				if(disjet.find(0)!=disjet.find(i)) {
					cont = true;
					break;
				}else if(i==mazeRows * mazeColumns-1) {
					cont = false;
				}
				
			}
			if (cont==false) {
				break;
			}
		}

		System.out.println("Final solved maze : ");
		printMaze(mazeRows,mazeColumns, wallsList);
		System.out.println("Number of Unions :" +numOfIterations);
	}


}
