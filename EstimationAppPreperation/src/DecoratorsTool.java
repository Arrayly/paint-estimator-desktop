import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JCheckBox;
import java.util.ArrayList;

public class DecoratorsTool{
    //INSTANCES FOR THE COMPONENTS
	private JFrame frame;
	private JTextField answerField_paintQuantity;
	private JTextField roomLength_textField;
	private JTextField roomWidth_textField;
	private JTextField roomHeight_textField;
	private JTextField answerField_paintCost;
	
	//USER INPUT TEXT FIELDS
	private double roomLength; 
	private double roomWidth; 
	private double roomHeight; 
	private int numOfWindows; 
	private int numOfDoors; 
	
	//LOCAL CONSTANT VARIABLES DEFINED HERE... ALL VALUES IN METRES!
	private final double areaPerGallonPaint = 37; //(4.   
	private final double standardWindowSize = 1.39355; //sq metres (Length * wdith of standard UK window size)
	private final double standardLedgeSize = 0.23; //sq metres 0.15*1.55
	private final double doorWidth = 0.762; //Standard door width
	private final double doorHeight = 1.981; //Standard door height
	private final double standardDoorSize = 1.509522;//sq metres
	private final double standardCovingSize = 0.15; //Standard coving surface width
	private final double standardSkirtSize = 0.12; //Standard skirting height
	private final double standardArchSize = 0.07; //Standard architrave width
	private final double paintWastage = 0.10; // Percentage% of paint wasted
	
	//USER SELECTION FIELDS (CHECKBOX)
	//true/false... default = false
	private boolean paintWindows_Checked;
	private boolean paintDoors_Checked;
	private boolean paintWalls_Checked;
	private boolean paintCeiling_Checked;
	private boolean paintWoodwork_Checked;
	
	//PAINT COST VARIABLES - VALUES CHANGE DEPENDING ON USER SELECTION
	//£ per litre
	private double paintCost_windows; //(£4.25,£11,£17)
	private double paintCost_doors; //(£4.25,£11,£17) 
	private double paintCost_walls; //(£2,£3,£4)
	private double paintCost_ceiling; //(£2,£3,£4)
	private double paintCost_woodwork; //(£4.25,£11,£17)
	
	//NUMBER OF COATS - VALUES CHANGE DEPENDING ON USER SELECTION
	//1-3 coats
	private int numOfCoats_windows;
	private int numOfCoats_doors;
	private int numOfCoats_walls;
	private int numOfCoats_ceiling;
	private int numOfCoats_woodwork;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DecoratorsTool window = new DecoratorsTool();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DecoratorsTool() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
					
		frame = new JFrame();
		frame.setBounds(100, 100, 776, 776);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRoomLength = new JLabel("Room Length");
		lblRoomLength.setBounds(15, 16, 100, 20);
		frame.getContentPane().add(lblRoomLength);
		
		JLabel lblRoomWidth = new JLabel("Room Width");
		lblRoomWidth.setBounds(15, 52, 100, 20);
		frame.getContentPane().add(lblRoomWidth);
		
		JLabel lblRoomHeight = new JLabel("Room Height");
		lblRoomHeight.setBounds(15, 88, 100, 20);
		frame.getContentPane().add(lblRoomHeight);
		
		JLabel lblDoors = new JLabel("Doors");
		lblDoors.setBounds(15, 124, 69, 20);
		frame.getContentPane().add(lblDoors);
		
		JLabel lblWindows = new JLabel("Windows");
		lblWindows.setBounds(15, 160, 69, 20);
		frame.getContentPane().add(lblWindows);
		
		JButton btnCalculate = new JButton("Calculate"); //Below are the actions perfromed when the "Calculate" button is pressed.
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Double> totalPaint = new ArrayList<Double>(); //ArrayList to store total gallons
				ArrayList<Double> paintCost = new ArrayList<Double>(); // ArrayList to store total cost of paint
				
				double sum_paintAmount = 0; //Store total value when all elements in ArrayList have been added together
				double sum_paintCost = 0;
				
				//DECORATION TOOL FORMULAS...
				double roomPerimeter = ((roomLength*2)+(roomWidth*2)); //Calculate perimeter of simple room
				double roomArea = (roomLength*roomWidth); //Calculate room area
				double roomVolume = (roomLength*roomHeight*roomWidth); //Calculate room volume
				
				double totalLedge = 0.22*numOfWindows; //Calculates the total ledge surface area multiplied by the number of windows
				double windowTotal = standardWindowSize * numOfWindows; //Takes the standard final value of window size and multiplies by the number of windows (total area)
				
				double doorTotal = standardDoorSize * numOfDoors; //same as above
				double doorPerimeter = ((doorWidth + doorHeight + doorHeight)*2); //(*2 for both sides)Calculating the door width and height excluding the bottom and multiply by the number of doors there are
				double totalDoorPerimeter = doorPerimeter * numOfDoors; //Takes final value of doorPerimeter and multiplies by the number of doors there are
				
				double totalSkirtPerimeter = (roomPerimeter - (doorWidth*numOfDoors)); //Calculate the total skirting length by taking doorwdith(*numofdoors) away from room perimeter
				
				//1. CEILING CALCULATOR
				double ceilingAreatoCover = (roomLength*roomWidth)*numOfCoats_ceiling; //Calculate total area of ceiling, multiply them by number of coats
				double ceilingPaint = (ceilingAreatoCover/areaPerGallonPaint); //divide by area per gallons
				ceilingPaint += ceilingPaint*paintWastage; //Add 20% wastage on top
				double totalPaintCost_ceiling = (ceilingPaint*4.546)*paintCost_ceiling; //Convert to litres multiply by paint cost per litres
				
				//2. WALL CALCULATOR
				double lengthOfWalls = ((roomLength*2)+(roomWidth*2))*numOfCoats_walls; //Adding the length of all the walls
				double totalWallArea = (lengthOfWalls*roomHeight);//Multiply the length of walls by room height to obtain total wall area
				double wallPaint = (totalWallArea - (windowTotal+doorTotal))/areaPerGallonPaint; //Take away the doors and windows from the wall area and divide total by area per gallon
				wallPaint += wallPaint*paintWastage; //Add 20% wastage on top
				double totalPaintCost_walls = (wallPaint*4.546)*paintCost_walls; //Convert to litres multiply by paint cost per litres
				
				System.out.println(totalPaintCost_walls);

				//3. DOOR CALCULATOR
				double doorPaint = ((standardDoorSize*2)*numOfDoors)/areaPerGallonPaint;
				double doorPaintTotal = (doorPaint*numOfCoats_doors);
				doorPaintTotal += doorPaintTotal*paintWastage; //Add 20% wastage on top
				double totalPaintCost_door = (doorPaintTotal*4.546)*paintCost_doors; //Convert to litres multiply by paint cost per litres
				
				//4. Window paint calculator; (give user option if to paint both exterior and interior)
				double windowPaint = (windowTotal)/areaPerGallonPaint;
				double windowPaintTotal = (windowPaint*numOfCoats_windows);
				windowPaintTotal += windowPaintTotal*paintWastage; //Add 20% wastage on top
				double totalPaintCost_window = (windowPaintTotal*4.546)*paintCost_windows; //Convert to litres multiply by paint cost per litres
		
				//5. WOODWORK CALCULATOR
				//coving
				double covingPaint = (standardCovingSize*roomPerimeter)/areaPerGallonPaint;
				double covingPaintTotal = (covingPaint*numOfCoats_woodwork);
				covingPaintTotal += covingPaintTotal*paintWastage; //Add 20% wastage on top
				double totalPaintCost_coving = (covingPaintTotal*4.546)*paintCost_woodwork; //Convert to litres multiply by paint cost per litres
						
				//Architrave
				double archPaint = (totalDoorPerimeter*standardArchSize)/areaPerGallonPaint;
				double archPaintTotal = (archPaint*numOfCoats_woodwork);
				archPaintTotal += archPaintTotal*paintWastage; //Add 20% wastage on top
				double totalPaintCost_arch = (archPaintTotal*4.546)*paintCost_woodwork; //Convert to litres multiply by paint cost per litres
						
				//Skirting Board
				double skirtPaint = (totalSkirtPerimeter*standardSkirtSize)/areaPerGallonPaint;
				double skirtPaintTotal = (skirtPaint*numOfCoats_woodwork);
				skirtPaintTotal += skirtPaintTotal*paintWastage; //Add 20% wastage on top
				double totalPaintCost_skirt = (skirtPaintTotal*4.546)*paintCost_woodwork; //Convert to litres multiply by paint cost per litres
						
						
						
				//IF CHECKBOX IS CHECKED... ADD VALUE TO ARRAYLIST... ELSE... REMOVE VALUE FROM ARRAYLIST... REFRESHING THE RESULT IN TEXTFIELD
			    if(paintCeiling_Checked) { //1.CEILING
					totalPaint.add(ceilingPaint);
					paintCost.add(totalPaintCost_ceiling);
			    }
			    
				if(paintWalls_Checked) { //2.WALL
					totalPaint.add(wallPaint);
					paintCost.add(totalPaintCost_walls);
					}
				
				if(paintDoors_Checked) { //3.DOORS
					totalPaint.add(doorPaintTotal);
					paintCost.add(totalPaintCost_door);
					}
				
				if(paintWindows_Checked) { //4.WINDOWS
					totalPaint.add(windowPaintTotal);
					paintCost.add(totalPaintCost_window);
					}
				
				if(paintWoodwork_Checked) { //5.WOODWOORK
					totalPaint.add(covingPaintTotal);
					totalPaint.add(archPaintTotal);
					totalPaint.add(skirtPaintTotal);
					
					paintCost.add(totalPaintCost_coving);
					paintCost.add(totalPaintCost_arch);
					paintCost.add(totalPaintCost_skirt);
					
					}
				
				//LOOP THROUGH ARRAY LIST ELEMENTS AND ADD THEM UP TOGETHER, STORE IN "SUM".
						for(int i = 0; i<totalPaint.size(); i++) { 
							sum_paintAmount += totalPaint.get(i); 
							}
						
						for(int i = 0; i<paintCost.size(); i++) {
							sum_paintCost += paintCost.get(i);
						}
			   //DISPLAY TEXT...FORMAT... GALLONS AND LITRES(4.546)	
					answerField_paintQuantity.setText((String.format("total gallons of paint required: %.2f and litres: %.2f", sum_paintAmount, sum_paintAmount* 4.546)));
					answerField_paintCost.setText(String.format("total paint cost: £%.2f", sum_paintCost));
								
			}
			
		});
		btnCalculate.setBounds(15, 445, 115, 29);
		frame.getContentPane().add(btnCalculate);
		
		answerField_paintQuantity = new JTextField();	
		answerField_paintQuantity.setBounds(15, 387, 278, 42);
		frame.getContentPane().add(answerField_paintQuantity);
		answerField_paintQuantity.setColumns(10);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- roomLength_textField
		roomLength_textField = new JTextField(); 
		roomLength_textField.addFocusListener(new FocusAdapter() { //This method saves the input of user once the cursor moves away from the textfield, You dont have to press "enter"
			@Override
			public void focusLost(FocusEvent e) {
				try {
					roomLength = Double.parseDouble(roomLength_textField.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please enter a valid input " + e2);
				}
				
			}
		});
		
		roomLength_textField.setText("0"); 
		roomLength_textField.setBounds(130, 13, 61, 26);
		frame.getContentPane().add(roomLength_textField);
		roomLength_textField.setColumns(10);
	
		
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- roomWidth_textField
		roomWidth_textField = new JTextField();
		roomWidth_textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					roomWidth = Double.parseDouble(roomWidth_textField.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please enter a valid input " + e2);
				}
			}
		});
		roomWidth_textField.setText("0");
		roomWidth_textField.setBounds(130, 49, 61, 26);
		frame.getContentPane().add(roomWidth_textField);
		roomWidth_textField.setColumns(10);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- roomHeight_textField
		roomHeight_textField = new JTextField();
		roomHeight_textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					roomHeight = Double.parseDouble(roomHeight_textField.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please enter a valid input " + e2);
				}
			}
		});
		roomHeight_textField.setText("0");
		roomHeight_textField.setBounds(130, 85, 61, 26);
		frame.getContentPane().add(roomHeight_textField);
		roomHeight_textField.setColumns(10);
		
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- numOfDoors_cBox
		JComboBox numOfDoors_cBox = new JComboBox(); //userInput
		numOfDoors_cBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfDoors = Integer.parseInt(numOfDoors_cBox.getSelectedItem().toString());
				//Takes selected item (1,2,3..) converts the "text" into an int primitive value...
			}
		});
		numOfDoors_cBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		numOfDoors_cBox.setSelectedIndex(0);
		numOfDoors_cBox.setBounds(130, 121, 36, 26);
		frame.getContentPane().add(numOfDoors_cBox);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- numOfWindows_cBox
		JComboBox numOfWindows_cBox = new JComboBox(); 
		numOfWindows_cBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOfWindows = Integer.parseInt(numOfWindows_cBox.getSelectedItem().toString());
				//Takes selected item (1,2,3..) converts the "text" into an int primitive value...
				
			}
		});
		numOfWindows_cBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		numOfWindows_cBox.setSelectedIndex(0);
		numOfWindows_cBox.setBounds(130, 157, 36, 26);
		frame.getContentPane().add(numOfWindows_cBox);
		
		JLabel lblNewLabel = new JLabel("Paint Windows?");
		lblNewLabel.setBounds(337, 16, 127, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPaintDoors = new JLabel("Paint Doors?");
		lblPaintDoors.setBounds(337, 160, 127, 20);
		frame.getContentPane().add(lblPaintDoors);
		 
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- checkBox_paintWindows
		JCheckBox checkBox_paintWindows = new JCheckBox("");
		checkBox_paintWindows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ACTION = TICKING/UNTICKING THE BOX... ALTERNATIVELY SETTING PRIVATE BOOLEAN VALUE TO "FALSE" BUT IF SELECTED... SET TO "TRUE"
				paintWindows_Checked = false; 
				if(checkBox_paintWindows.isSelected()) {
					paintWindows_Checked = true;
				}
			}
		});
		checkBox_paintWindows.setBounds(575, 16, 29, 29);
		frame.getContentPane().add(checkBox_paintWindows);
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_paintTypeWindows
		JComboBox comboBox_paintTypeWindows = new JComboBox();
		comboBox_paintTypeWindows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_paintTypeWindows.getSelectedIndex()== 1) {
					paintCost_windows = 11.0;
				}else if (comboBox_paintTypeWindows.getSelectedIndex()== 2) {
					paintCost_windows = 4.25;
				}else if(comboBox_paintTypeWindows.getSelectedIndex()== 3) {
					paintCost_windows = 17.0;
				}else {
					paintCost_windows = 0;
				}
				
			}
		});
		comboBox_paintTypeWindows.setModel(new DefaultComboBoxModel(new String[] {"Please Select", "Branded Non-drip Gloss - \u00A311 per litre", "Branded Liquid Gloss - \u00A34.25 per litre", "Branded Varnish - \u00A317 per litre"}));
		comboBox_paintTypeWindows.setSelectedIndex(0);
		comboBox_paintTypeWindows.setBounds(565, 49, 138, 26);
		frame.getContentPane().add(comboBox_paintTypeWindows);
		
		JLabel lblNewLabel_1 = new JLabel("Paint Type");
		lblNewLabel_1.setBounds(337, 52, 127, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JComboBox comboBox_coatWindows = new JComboBox();
		comboBox_coatWindows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_coatWindows.getSelectedIndex()== 1) {
					numOfCoats_windows = 2;
				}else if (comboBox_coatWindows.getSelectedIndex()== 2) {
					numOfCoats_windows = 3;
				}else {
					numOfCoats_windows = 1;
				}
			}
		});
		comboBox_coatWindows.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox_coatWindows.setSelectedIndex(0);
		comboBox_coatWindows.setBounds(568, 85, 36, 26);
		frame.getContentPane().add(comboBox_coatWindows);
		
		JLabel lblNewLabel_2 = new JLabel("Number of Coats");
		lblNewLabel_2.setBounds(337, 88, 138, 20);
		frame.getContentPane().add(lblNewLabel_2);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- checkBox_paintDoors
		JCheckBox checkBox_paintDoors = new JCheckBox("");
		checkBox_paintDoors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ACTION = TICKING/UNTICKING THE BOX... ALTERNATIVELY SETTING PRIVATE BOOLEAN VALUE TO "FALSE" BUT IF SELECTED... SET TO "TRUE"
				paintDoors_Checked = false;
				if(checkBox_paintDoors.isSelected()) {
					paintDoors_Checked = true;
				}
			}
		});
		checkBox_paintDoors.setBounds(575, 160, 29, 29);
		frame.getContentPane().add(checkBox_paintDoors);
		
		JLabel label = new JLabel("Paint Type");
		label.setBounds(337, 196, 127, 20);
		frame.getContentPane().add(label);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_paintTypeDoors
		JComboBox comboBox_paintTypeDoors = new JComboBox();
		comboBox_paintTypeDoors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_paintTypeDoors.getSelectedIndex()== 1) {
					paintCost_doors = 11.0;
				}else if (comboBox_paintTypeDoors.getSelectedIndex()== 2) {
					paintCost_doors = 4.25;
				}else if(comboBox_paintTypeDoors.getSelectedIndex()== 3) {
					paintCost_doors = 17.0;
				}else {
					paintCost_doors = 0;
				}
			}
		});
		comboBox_paintTypeDoors.setModel(new DefaultComboBoxModel(new String[] {"Please Select", "Branded Non-drip Gloss - \u00A311 per litre", "Branded Liquid Gloss - \u00A34.25 per litre", "Branded Varnish - \u00A317 per litre"}));
		comboBox_paintTypeDoors.setSelectedIndex(0);
		comboBox_paintTypeDoors.setBounds(457, 193, 246, 26);
		frame.getContentPane().add(comboBox_paintTypeDoors);
		
		JLabel label_1 = new JLabel("Number of Coats");
		label_1.setBounds(337, 232, 138, 20);
		frame.getContentPane().add(label_1);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_coatsDoors
		JComboBox comboBox_coatsDoors = new JComboBox();
		comboBox_coatsDoors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_coatsDoors.getSelectedIndex()== 1) {
					numOfCoats_doors = 2;
				}else if (comboBox_coatsDoors.getSelectedIndex()== 2) {
					numOfCoats_doors = 3;
				}else {
					numOfCoats_doors = 1;
				}
			}
		});
		comboBox_coatsDoors.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox_coatsDoors.setSelectedIndex(0);
		comboBox_coatsDoors.setBounds(568, 229, 36, 26); 
		frame.getContentPane().add(comboBox_coatsDoors);
		
		JLabel lblPaintWalls = new JLabel("Paint Walls?");
		lblPaintWalls.setBounds(337, 300, 127, 20);
		frame.getContentPane().add(lblPaintWalls);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- checkBox_paintWalls
		JCheckBox checkBox_paintWalls = new JCheckBox("");
		checkBox_paintWalls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ACTION = TICKING/UNTICKING THE BOX... ALTERNATIVELY SETTING PRIVATE BOOLEAN VALUE TO "FALSE" BUT IF SELECTED... SET TO "TRUE"
				paintWalls_Checked = false;
				if(checkBox_paintWalls.isSelected()) {
					paintWalls_Checked = true;
				}
			}
		});
		checkBox_paintWalls.setBounds(575, 291, 29, 29);
		frame.getContentPane().add(checkBox_paintWalls);
		
		JLabel lblPaintCost = new JLabel("Paint cost (5 litres)?");
		lblPaintCost.setBounds(337, 330, 147, 20);
		frame.getContentPane().add(lblPaintCost);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_paintCostWalls
		JComboBox comboBox_paintCostWalls = new JComboBox();
		comboBox_paintCostWalls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_paintCostWalls.getSelectedIndex()== 1) {
					paintCost_walls = 2.0; //price in £ per litre (£10 / 5litres)
				}else if (comboBox_paintCostWalls.getSelectedIndex()== 2) {
					paintCost_walls = 3.0;
				}else if(comboBox_paintCostWalls.getSelectedIndex()== 3) {
					paintCost_walls = 4.0;
				}else {
					paintCost_walls = 0;
				}
			}
		});
		comboBox_paintCostWalls.setModel(new DefaultComboBoxModel(new String[] {"Please Select", "\u00A310", "\u00A315", "\u00A320"}));
		comboBox_paintCostWalls.setSelectedIndex(0);
		comboBox_paintCostWalls.setBounds(489, 327, 115, 26);
		frame.getContentPane().add(comboBox_paintCostWalls);
		
		JLabel label_2 = new JLabel("Number of Coats");
		label_2.setBounds(337, 366, 138, 20);
		frame.getContentPane().add(label_2);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_coatsWalls
		JComboBox comboBox_coatsWalls = new JComboBox();
		comboBox_coatsWalls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_coatsWalls.getSelectedIndex()== 1) {
					numOfCoats_walls = 2;
				}else if (comboBox_coatsWalls.getSelectedIndex()== 2) {
					numOfCoats_walls = 3;
				}else {
					numOfCoats_walls = 1;
				}
			}
		});
		comboBox_coatsWalls.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox_coatsWalls.setSelectedIndex(0);
		comboBox_coatsWalls.setBounds(568, 369, 36, 26);
		frame.getContentPane().add(comboBox_coatsWalls);
		
		JLabel lblPaintCeiling = new JLabel("Paint Ceiling?");
		lblPaintCeiling.setBounds(337, 428, 127, 20);
		frame.getContentPane().add(lblPaintCeiling);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- checkBox_paintCeiling
		JCheckBox checkBox_paintCeiling = new JCheckBox("");
		checkBox_paintCeiling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ACTION = TICKING/UNTICKING THE BOX... ALTERNATIVELY SETTING PRIVATE BOOLEAN VALUE TO "FALSE" BUT IF SELECTED... SET TO "TRUE"
				paintCeiling_Checked = false;
				if(checkBox_paintCeiling.isSelected()) {
					paintCeiling_Checked = true;
				}
			}
		});
		checkBox_paintCeiling.setBounds(575, 419, 29, 29);
		frame.getContentPane().add(checkBox_paintCeiling);
		
		JLabel label_3 = new JLabel("Paint cost (5 litres)?");
		label_3.setBounds(337, 464, 147, 20);
		frame.getContentPane().add(label_3);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_paintCostCeiling
		JComboBox comboBox_paintCostCeiling = new JComboBox();
		comboBox_paintCostCeiling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_paintCostCeiling.getSelectedIndex()== 1) {
					paintCost_ceiling = 2.0; //price in £ per litre (£10 / 5litres)
				}else if (comboBox_paintCostCeiling.getSelectedIndex()== 2) {
					paintCost_ceiling = 3.0;
				}else if(comboBox_paintCostCeiling.getSelectedIndex()== 3) {
					paintCost_ceiling = 4.0;
				}else {
					paintCost_ceiling = 0;
				}
			}
		});
		comboBox_paintCostCeiling.setModel(new DefaultComboBoxModel(new String[] {"Please Select", "\u00A310", "\u00A315", "\u00A320"}));
		comboBox_paintCostCeiling.setSelectedIndex(0);
		comboBox_paintCostCeiling.setBounds(489, 461, 115, 26);
		frame.getContentPane().add(comboBox_paintCostCeiling);
		
		JLabel label_4 = new JLabel("Number of Coats");
		label_4.setBounds(337, 500, 138, 20);
		frame.getContentPane().add(label_4);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_coatsCeiling
		JComboBox comboBox_coatsCeiling = new JComboBox();
		comboBox_coatsCeiling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_coatsCeiling.getSelectedIndex()== 1) {
					numOfCoats_ceiling = 2;
				}else if (comboBox_coatsCeiling.getSelectedIndex()== 2) {
					numOfCoats_ceiling = 3;
				}else {
					numOfCoats_ceiling = 1;
				}
			}
		});
		comboBox_coatsCeiling.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox_coatsCeiling.setSelectedIndex(0);
		comboBox_coatsCeiling.setBounds(568, 497, 36, 26);
		frame.getContentPane().add(comboBox_coatsCeiling);
		
		JLabel lblPaintWoodwork = new JLabel("Paint Woodwork?");
		lblPaintWoodwork.setBounds(337, 550, 127, 20);
		frame.getContentPane().add(lblPaintWoodwork);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- checkBox_paintWoodwork
		JCheckBox checkBox_paintWoodwork = new JCheckBox("");
		checkBox_paintWoodwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ACTION = TICKING/UNTICKING THE BOX... ALTERNATIVELY SETTING PRIVATE BOOLEAN VALUE TO "FALSE" BUT IF SELECTED... SET TO "TRUE"
				paintWoodwork_Checked = false;
				if(checkBox_paintWoodwork.isSelected()) {
				paintWoodwork_Checked = true;
				}
			}
		});
		checkBox_paintWoodwork.setBounds(575, 550, 29, 29);
		frame.getContentPane().add(checkBox_paintWoodwork);
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_paintTypeWoodwork
		JComboBox comboBox_paintTypeWoodwork = new JComboBox();
		comboBox_paintTypeWoodwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_paintTypeWoodwork.getSelectedIndex()== 1) {
					paintCost_woodwork = 11.0;
				}else if (comboBox_paintTypeWoodwork.getSelectedIndex()== 2) {
					paintCost_woodwork = 4.25;
				}else if(comboBox_paintTypeWoodwork.getSelectedIndex()== 3) {
					paintCost_woodwork = 17.0;
				}else {
					paintCost_woodwork = 0;
				}
			}
		});
		comboBox_paintTypeWoodwork.setModel(new DefaultComboBoxModel(new String[] {"Please Select", "Branded Non-drip Gloss - \u00A311 per litre", "Branded Liquid Gloss - \u00A34.25 per litre", "Branded Varnish - \u00A317 per litre"}));
		comboBox_paintTypeWoodwork.setSelectedIndex(0);
		comboBox_paintTypeWoodwork.setBounds(457, 586, 246, 26);
		frame.getContentPane().add(comboBox_paintTypeWoodwork);
		
		JLabel label_5 = new JLabel("Paint Type");
		label_5.setBounds(337, 586, 127, 20);
		frame.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("Number of Coats");
		label_6.setBounds(337, 622, 138, 20);
		frame.getContentPane().add(label_6);
		
		//USER INPUT USER INPUT USER INPUT USER INPUT ----------------------------------------------------------------- comboBox_coatsWoodwork
		JComboBox comboBox_coatsWoodwork = new JComboBox();
		comboBox_coatsWoodwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_coatsWoodwork.getSelectedIndex()== 1) {
					numOfCoats_woodwork = 2;
				}else if (comboBox_coatsWoodwork.getSelectedIndex()== 2) {
					numOfCoats_woodwork = 3;
				}else {
					numOfCoats_woodwork = 1;
				}
			}
		});
		comboBox_coatsWoodwork.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox_coatsWoodwork.setSelectedIndex(0);
		comboBox_coatsWoodwork.setBounds(568, 619, 36, 26);
		frame.getContentPane().add(comboBox_coatsWoodwork);
		
		answerField_paintCost = new JTextField();
		answerField_paintCost.setBounds(15, 300, 278, 47);
		frame.getContentPane().add(answerField_paintCost);
		answerField_paintCost.setColumns(10);
		
		JLabel lblE = new JLabel("Paint Quanity Required");
		lblE.setBounds(15, 366, 163, 20);
		frame.getContentPane().add(lblE);
		
		JLabel lblPaintCost_1 = new JLabel("Paint Cost");
		lblPaintCost_1.setBounds(15, 272, 115, 20);
		frame.getContentPane().add(lblPaintCost_1);
		
	}
}
