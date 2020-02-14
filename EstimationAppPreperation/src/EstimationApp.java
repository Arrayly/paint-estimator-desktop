
public class EstimationApp {
public static void main(String[] args) {
		EstimationApp estApp = new EstimationApp();
		
		 //THESE VALUES WILL BE ACCORDING TO USER INPUT
		double roomLength = 4; //USER INPUT
		double roomWidth = 2; //USER INPUT
		double roomHeight = 2; //USER INPUT
		
		
		double roomPerimeter = ((roomLength*2)+(roomWidth*2));
		System.out.println("Room perimetre = " + roomPerimeter);
		
		//The money involed
		double laborCost = 11.5;     
		double profit = 3;
		double vat;
		double totalCost;
		double paintCost;
		double materials;
		double totalHours = 8;
		
		final double areaPerGallonPaint = 32; //m2
		
		int numOfWindows = 1; //USER INPUT
		int numOfDoors = 1; //USER INPUT
		int numOfCoats = 2; //USER INPUT
		
		double standardWindowSize = 1.39355; //sq metres
		double standardLedgeSize = 0.22;
		double totalLedge = 0.22*numOfWindows;
		
		double windowTotal = standardWindowSize * numOfWindows;
		
		double doorWidth = 0.762;
		double doorHeight = 1.981;
		double standardDoorSize = 1.509522; //sq metres
		double doorTotal = standardDoorSize * numOfDoors;
		
		//Only 1 side..
		double doorPerimeter = ((doorWidth + doorHeight + doorHeight)*2); //Calculating the door width and height excluding the bottom and multiply by the number of doors there are
		double totalDoorPerimeter = doorPerimeter * numOfDoors;
		System.out.println("Total door perimeter" + totalDoorPerimeter);
				
		double standardCovingSize = 0.15;
		double standardSkirtSize = 0.12;
		double totalSkirtPerimeter = (roomPerimeter - (doorWidth*numOfDoors));
		double standardArchSize = 0.07;
		
		//THESE ARE CHECK BOXES FOR USER TO SELECT, TRUE = BOX HAS BEEN CLICKED.
		boolean removalWallpaper = true;
		boolean applyWallpaper = true;
		boolean prepWork = true;
		
		double roomArea = (roomLength*roomWidth);
		double roomVolume = (roomLength*roomHeight*roomWidth);
	
		
		//ceiling paint calculator
		double ceilingAreatoCover = (roomLength*roomWidth)*numOfCoats; //Calculate total area if ceiling, multiply them by number of coats
		double ceilingPaint = (ceilingAreatoCover/areaPerGallonPaint); //divide by area per gallons
		System.out.println(String.format("Gallons of paint required to cover ceiling: %.2f", ceilingPaint));
		
		
		//walls paint calculator
		double lengthOfWalls = ((roomLength*2)+(roomWidth*2))*numOfCoats; //Adding the length of all the walls
		double totalWallArea = (lengthOfWalls*roomHeight);//Multiply the length of walls by room height to obtain total wall area
		System.out.println("total wall area = " + totalWallArea);
		double wallPaint = (totalWallArea - (windowTotal+doorTotal))/areaPerGallonPaint; //Take away the doors and windows from the wall area and divide total by area per gallon
		System.out.println(String.format("Gallons of paint required to cover walls: %.2f", wallPaint));//
		
		//Doors paint calculator (times doorsize by 2 because we are painting both sides)
		double doorPaint = ((standardDoorSize*2)*numOfDoors)/areaPerGallonPaint;
		System.out.println(String.format("Gallons of paint required to cover doors: %.2f", doorPaint*numOfCoats));
		
		//Window paint calculator; (give user option if to paint both exterior and interior)
		double windowPaint = (windowTotal)/areaPerGallonPaint;
		System.out.println(String.format("Gallons of paint required to cover windows: %.2f", windowPaint*numOfCoats));
		
		//Woodwork selection..
		//coving
		double covingPaint = (standardCovingSize*roomPerimeter)/areaPerGallonPaint;
		System.out.println(String.format("total gallons of paint required for coving %.2f", covingPaint*numOfCoats));
		//Architrave
		double archPaint = (totalDoorPerimeter*standardArchSize)/areaPerGallonPaint;
		System.out.println(String.format("total gallons of paint required for Architrave %.2f", archPaint*numOfCoats));
		//SkirtingBoard
		double skirtPaint = (totalSkirtPerimeter*standardSkirtSize)/areaPerGallonPaint;
		System.out.println(String.format("total gallons of paint required for skirting %.2f", skirtPaint*numOfCoats));

		//Total paint calculator
		double totalPaint = ceilingPaint + wallPaint + doorPaint*numOfCoats + covingPaint*numOfCoats + windowPaint*numOfCoats + archPaint*numOfCoats + skirtPaint*numOfCoats;
		System.out.println(String.format("total gallons of paint required: %.2f and litres: %.2f", totalPaint, totalPaint* 4.546)); //litres (4.546)

	
		
	}

}
