import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class KarnaughMap implements ActionListener {

	private JFrame frmKarnaughMap;
	private JPanel panTruthTable = new JPanel();
	private JPanel panKMap = new JPanel();
	JLabel lblFunction = new JLabel("F(A,B) = AB");

	JButton[] listTruthButtons;
	JButton[][] listKMapButtons;

	private String[] variable2 = new String[] { "00", "01", "10", "11" };
	private String[] variable3 = new String[] { "000", "001", "010", "011", "100", "101", "110", "111" };
	private String[] variable4 = new String[] { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
			"1001", "1010", "1011", "1100", "1101", "1110", "1111" };

	private String[][] variable2KMap = new String[][] { { "00", "10" }, { "01", "11" } };

	private String[][] variable3KMap = new String[][] { { "000", "010", "110", "100" },
			{ "001", "011", "111", "101" } };

	private String[][] variable4KMap = new String[][] { { "0000", "0100", "1100", "1000" },
			{ "0001", "0101", "1101", "1001" }, { "0011", "0111", "1111", "1011", },
			{ "0010", "0110", "1110", "1010" } };

	private String[] truth2 = new String[] { "0", "0", "0", "0" };
	private String[] truth3 = new String[] { "0", "0", "0", "0", "0", "0", "0", "0" };
	private String[] truth4 = new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
			"0" };

	private String[][] kmap2 = new String[][] { { "0", "0" }, { "0", "0" } };
	private String[][] kmap3 = new String[][] { { "0", "0", "0", "0" }, { "0", "0", "0", "0" } };
	private String[][] kmap4 = new String[][] { { "0", "0", "0", "0" }, { "0", "0", "0", "0" }, { "0", "0", "0", "0", },
			{ "0", "0", "0", "0" } };

	private String[] VARIABLE_NAME = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N"};

	private int VARIABLE;
	private boolean ALLOW_IGNORANCE = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KarnaughMap window = new KarnaughMap();
					window.frmKarnaughMap.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KarnaughMap() {
		initialize();
	}

	/**
	 * implement the function for action listener
	 * 
	 * @param e
	 *            the current event that users interact with
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		String actionType = actionCommand.substring(0, 8);

		// this is the button index
		int actionIndex;

		try {
			System.out.println("Selected: " + actionCommand);
			if (actionType.equals("variable")) {
				VARIABLE = Integer.parseInt(actionCommand.substring(8));

				switch (VARIABLE) {
				case 2:
					updateTruthTablePanel(variable2, truth2);
					updateKMapPanel(variable2KMap, kmap2);
					
					simplify(variable2, truth2, variable2KMap, kmap2);
					break;
				case 3:
					updateTruthTablePanel(variable3, truth3);
					updateKMapPanel(variable3KMap, kmap3);
					
					simplify(variable3, truth3, variable3KMap, kmap3);
					break;
				case 4:
					updateTruthTablePanel(variable4, truth4);
					updateKMapPanel(variable4KMap, kmap4);
					
					simplify(variable4, truth4, variable4KMap, kmap4);
					break;
				}
			} else if (actionType.equals("btntruth")) {
				actionIndex = Integer.parseInt(actionCommand.substring(8));
				String[] mapping;
				int row = 0, column = 0;
				
				switch (VARIABLE) {
				case 2:
					mapping = getRowOrColumn(variable2, actionIndex, variable2KMap, row, column, 1);					
					row = Integer.parseInt(mapping[0]);
					column = Integer.parseInt(mapping[1]);
					
					updateValueInTable(variable2, truth2, actionIndex, variable2KMap, kmap2, row, column, 1);
					updateTruthTablePanel(variable2, truth2);
					updateKMapPanel(variable2KMap, kmap2);
					
					simplify(variable2, truth2, variable2KMap, kmap2);
					break;
				case 3:
					mapping = getRowOrColumn(variable3, actionIndex, variable3KMap, row, column, 1);					
					row = Integer.parseInt(mapping[0]);
					column = Integer.parseInt(mapping[1]);
					
					updateValueInTable(variable3, truth3, actionIndex, variable3KMap, kmap3, row, column, 1);
					updateTruthTablePanel(variable3, truth3);
					updateKMapPanel(variable3KMap, kmap3);
					
					simplify(variable3, truth3, variable3KMap, kmap3);
					break;
				case 4:
					mapping = getRowOrColumn(variable4, actionIndex, variable4KMap, row, column, 1);					
					row = Integer.parseInt(mapping[0]);
					column = Integer.parseInt(mapping[1]);
					
					updateValueInTable(variable4, truth4, actionIndex, variable4KMap, kmap4, row, column, 1);
					updateTruthTablePanel(variable4, truth4);
					updateKMapPanel(variable4KMap, kmap4);
					
					// simplify
					simplify(variable4, truth4, variable4KMap, kmap4);
					break;
				}

			} else if (actionType.equals("btnkamap")) {
				String cellIndex = actionCommand.substring(8);
				String[] mapping;
				int row = Integer.parseInt(cellIndex.substring(0, 1));
				int column = Integer.parseInt(cellIndex.substring(2));
				int index = 0;
				switch (VARIABLE) {
				case 2:
					mapping = getRowOrColumn(variable2, index, variable2KMap, row, column, 2);					
					index = Integer.parseInt(mapping[0]);
					
					updateValueInTable(variable2, truth2, index, variable2KMap, kmap2, row, column, 2);
					updateTruthTablePanel(variable2, truth2);
					updateKMapPanel(variable2KMap, kmap2);
					
					simplify(variable2, truth2, variable2KMap, kmap2);
					break;
				case 3:
					mapping = getRowOrColumn(variable3, index, variable3KMap, row, column, 2);					
					index = Integer.parseInt(mapping[0]);
					
					updateValueInTable(variable3, truth3, index, variable3KMap, kmap3, row, column, 2);
					updateTruthTablePanel(variable3, truth3);
					updateKMapPanel(variable3KMap, kmap3);
					
					// simplify
					simplify(variable3, truth3, variable3KMap, kmap3);
					break;
				case 4:
					mapping = getRowOrColumn(variable4, index, variable4KMap, row, column, 2);					
					index = Integer.parseInt(mapping[0]);
					
					updateValueInTable(variable4, truth4, index, variable4KMap, kmap4, row, column, 2);
					updateTruthTablePanel(variable4, truth4);
					updateKMapPanel(variable4KMap, kmap4);
					
					// simplify
					simplify(variable4, truth4, variable4KMap, kmap4);
					break;
				}
			}
			// Don't care
			else if (actionType.equals("allowIgn")) {
				if (ALLOW_IGNORANCE == false) {
					ALLOW_IGNORANCE = true;
				} else {
					ALLOW_IGNORANCE = false;
				}
			}
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(frmKarnaughMap, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void simplify (String[] aValueTruthTable, String[] theTruthTable, String[][] aValueKMapTable, String[][] theKMapTable)
	{
		String functionOutput = "";
		int i;
		boolean isAllZeros = true, isAllOnes = true, isAllIgnorances = true;
		
		if (VARIABLE == 2)
			functionOutput = "F(" + VARIABLE_NAME[0] + ", " + VARIABLE_NAME[1] + ") = "; 
		else if (VARIABLE == 3)
			functionOutput = "F(" + VARIABLE_NAME[0] + ", " + VARIABLE_NAME[1] + ", " + VARIABLE_NAME[2]  + ") = "; 
		else if (VARIABLE == 4)
			functionOutput = "F(" + VARIABLE_NAME[0] + ", " + VARIABLE_NAME[1] + ", " + VARIABLE_NAME[2] + ", " + VARIABLE_NAME[3] + ") = "; 
		
		
		// check if all 0s or all 1s
		for (i = 0; i < theTruthTable.length; i++)
		{
			if (theTruthTable[i].equals("1"))
			{
				isAllZeros = false;
			}
			if (theTruthTable[i].equals("0"))
			{
				isAllOnes = false;
			}
			if (!theTruthTable[i].equals("x"))
			{
				isAllIgnorances = false;
			}
		}
		
		if (isAllZeros || isAllIgnorances)
		{
			functionOutput += "0";
		}
		else if (isAllOnes || isAllIgnorances)
		{
			functionOutput += "1";
		}	
		// do the simplification here
		else
		{
			Simplifier simplifier = new Simplifier();
			String functionSimplify = "";
			
			int[] arrMinTerms = getSubArray(theTruthTable, 1);
			int[] arrIgnorances = getSubArray(theTruthTable, 2);
			
			printArray(arrMinTerms);
			printArray(arrIgnorances);
			
			//functionOutput += getFunctionOutput(aValueKMapTable, theKMapTable);
			
			functionSimplify = simplifier.getSimplifier(arrMinTerms, arrIgnorances, VARIABLE);			
			functionOutput += " = " + functionSimplify;
		}
		
		lblFunction.setText(functionOutput);		
	}
	
	
	private void printArray (int[] anArray)
	{
		for (int i = 0; i < anArray.length; i++)
		{
			System.out.print(anArray[i] + " ");
		}
		System.out.println();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
 	private void initialize() {

		frmKarnaughMap = new JFrame();
		frmKarnaughMap.setTitle("Karnaugh Map");
		frmKarnaughMap.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		frmKarnaughMap.setBounds(100, 100, 510, 450);
		frmKarnaughMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKarnaughMap.setResizable(false);
		frmKarnaughMap.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		ButtonGroup variableButtonGroup = new ButtonGroup();

		JPanel panTop = new JPanel();
		panTop.setBorder(new EmptyBorder(0, 10, 10, 0));
		frmKarnaughMap.getContentPane().add(panTop);
		panTop.setPreferredSize(new Dimension(500, 50));
		panTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JLabel lblKarnaughMap = new JLabel("Karnaugh Map");
		lblKarnaughMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblKarnaughMap.setPreferredSize(new Dimension(500, 25));
		lblKarnaughMap.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panTop.add(lblKarnaughMap);

		JRadioButton rdbtn2Variable = new JRadioButton("Two Variables");
		rdbtn2Variable.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtn2Variable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		rdbtn2Variable.setActionCommand("variable2");
		rdbtn2Variable.addActionListener(this);
		panTop.add(rdbtn2Variable);

		JRadioButton rdbtn3Variable = new JRadioButton("Three Variables");
		rdbtn3Variable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		rdbtn3Variable.setActionCommand("variable3");
		rdbtn3Variable.addActionListener(this);
		panTop.add(rdbtn3Variable);

		JRadioButton rdbtn4Variable = new JRadioButton("Four Variables");
		rdbtn4Variable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		rdbtn4Variable.setActionCommand("variable4");
		rdbtn4Variable.addActionListener(this);
		panTop.add(rdbtn4Variable);

		variableButtonGroup.add(rdbtn2Variable);
		variableButtonGroup.add(rdbtn3Variable);
		variableButtonGroup.add(rdbtn4Variable);

		JCheckBox chckbxIgnorenceAllow = new JCheckBox("Allow Ignorence");
		chckbxIgnorenceAllow.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		chckbxIgnorenceAllow.setActionCommand("allowIgn");
		chckbxIgnorenceAllow.addActionListener(this);
		panTop.add(chckbxIgnorenceAllow);

		JPanel panResult = new JPanel();
		panResult.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panResult.setPreferredSize(new Dimension(500, 30));
		frmKarnaughMap.getContentPane().add(panResult);

		lblFunction.setBorder(new EmptyBorder(0, 10, 10, 0));
		lblFunction.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFunction.setPreferredSize(new Dimension(500, 20));
		panResult.add(lblFunction);

		JPanel panInput = new JPanel();
		panInput.setBorder(new EmptyBorder(0, 10, 10, 0));
		panInput.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panInput.setPreferredSize(new Dimension(500, 350));
		frmKarnaughMap.getContentPane().add(panInput);
		panInput.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
		panTruthTable.setBorder(new EmptyBorder(0, 0, 0, 0));

		panTruthTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panTruthTable.setPreferredSize(new Dimension(200, 310));
		panInput.add(panTruthTable);

		panKMap.setPreferredSize(new Dimension(290, 310));
		panKMap.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		panInput.add(panKMap);

	}

	private void updateValueInTable(String[] aValueTruthTable, String[] theTruthTable, int anIndex,
			String[][] aValueKMapTable, String[][] theKMapTable, int row, int column, int direction) {

		if (ALLOW_IGNORANCE == false) {
			// update the truth table
			// 1 <==> 0, 0 <==> 1
			if (theTruthTable[anIndex].equals("0") && theKMapTable[row][column].equals("0") || theTruthTable[anIndex].isEmpty()
					 && theKMapTable[row][column].isEmpty()) {

				synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
						column, "1", direction);

			} else {

				synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
						column, "0", direction);
			}

		} else {
			// 0 ==> 1
			if (theTruthTable[anIndex].equals("0") && theKMapTable[row][column].equals("0") || theTruthTable[anIndex].isEmpty()
					 && theKMapTable[row][column].isEmpty()) {
				
				synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
						column, "1", direction);

			} else if (theTruthTable[anIndex].equals("1") && theKMapTable[row][column].equals("1")) {
				// 1 ==> x
				synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
						column, "x", direction);
			} else {
				// x ==> 0
				synchonizeTwoTables(aValueTruthTable, theTruthTable, anIndex, aValueKMapTable, theKMapTable, row,
						column, "0", direction);

			}
		}
	}

	private void synchonizeTwoTables(String[] aValueTruthTable, String[] theTruthTable, int anIndex,
			String[][] aValueKMapTable, String[][] theKMapTable, int row, int column, String theNewValue,
			int direction) {
		int i, j;
		String theValue;
		boolean isDone = false;

		theValue = aValueTruthTable[anIndex];
		if (direction == 2) {
			theValue = aValueKMapTable[row][column];
		}

		// synchronize from truth table to kmap
		for (i = 0; i < aValueKMapTable.length; i++) {
			for (j = 0; j < aValueKMapTable[i].length; j++) {
				if (theValue.equalsIgnoreCase(aValueKMapTable[i][j])) {
					// set value for theKMapTable
					theKMapTable[i][j] = theNewValue;
					isDone = true;
					break;
				}
			}

			if (isDone == true)
				break;
		}

		// synchronize from kmap to truth table
		for (i = 0; i < aValueTruthTable.length; i++) {
			if (theValue.equalsIgnoreCase(aValueTruthTable[i])) {
				theTruthTable[i] = theNewValue;
				break;
			}
		}
		
		//print out the matrix
		for (i = 0; i < theKMapTable.length; i++) {
			for (j = 0; j < theKMapTable[i].length; j++) {
				System.out.print(theKMapTable[i][j] + " ");
			}
			System.out.println();
			
		}
		
		// print out the truth table
		for (i = 0; i < aValueTruthTable.length; i++) {
			System.out.print(theTruthTable[i] + " ");
		}
		System.out.println();
	}

	private void updateTruthTablePanel(String[] valueTable, String[] truthTable) {
		int rows = 0;
		int i = 0, j = 0;
		String _rowValue = "", _columnValue = "";

		rows = valueTable.length;

		panTruthTable.removeAll();
		panTruthTable.revalidate();
		panTruthTable.repaint();
		panTruthTable.setLayout(new GridLayout(rows, valueTable[0].length(), 5, 5));

		listTruthButtons = new JButton[rows];

		for (i = 0; i < rows; i++) {
			_rowValue = valueTable[i];

			for (j = 0; j < _rowValue.length(); j++) {
				_columnValue = "" + _rowValue.charAt(j);

				JTextField aTextFields;
				aTextFields = new JTextField(_columnValue);
				aTextFields.setPreferredSize(new Dimension(15, 15));
				aTextFields.setMaximumSize(new Dimension(15, 15));
				aTextFields.setMinimumSize(new Dimension(15, 15));
				aTextFields.setEditable(false);
				aTextFields.setBorder(BorderFactory.createEmptyBorder());
				aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				aTextFields.setHorizontalAlignment(SwingConstants.CENTER);

				panTruthTable.add(aTextFields);
			}
			// for the last columns
			String buttonText = "0";

			if (truthTable[i].equals("1")) {
				buttonText = "1";
			} else if (truthTable[i].equals("x")) {
				buttonText = "x";
			} else {
				buttonText = "0";
			}

			listTruthButtons[i] = new JButton(buttonText);
			listTruthButtons[i].setPreferredSize(new Dimension(15, 15));
			listTruthButtons[i].setMaximumSize(new Dimension(15, 15));
			listTruthButtons[i].setMinimumSize(new Dimension(15, 15));
			listTruthButtons[i].setActionCommand("btntruth" + i);
			listTruthButtons[i].addActionListener(this);
			listTruthButtons[i].setBorder(BorderFactory.createEmptyBorder());
			listTruthButtons[i].setForeground(Color.BLACK);

			if (truthTable[i].equals("1")) {
				listTruthButtons[i].setBackground(new Color(40, 150, 97));
			} else if (truthTable[i].equals("x")) {
				listTruthButtons[i].setBackground(Color.lightGray);
			} else {
				listTruthButtons[i].setBackground(Color.WHITE);
			}

			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(5, 5, 5, 5);
			Border compound = new CompoundBorder(line, margin);
			listTruthButtons[i].setBorder(compound);

			// listButtons[i].setBorder(null);
			panTruthTable.add(listTruthButtons[i]);
		}
	}

	private void updateKMapPanel(String[][] valueTable, String[][] KMap) {
		int rows = 0, columns = 0;
		int i = 0, j = 0;
		// add 1 for the label
		rows = valueTable.length;
		columns = valueTable[0].length;

		String[] rowValue = new String[columns];
		String cellValue = "";
		listKMapButtons = new JButton[rows][columns];
		JTextField aTextFields;

		panKMap.removeAll();
		panKMap.revalidate();
		panKMap.repaint();
		panKMap.setLayout(new GridLayout(rows + 1, columns + 1, 5, 5));

		// add title
		// an empty cell
		if (VARIABLE == 2)
			cellValue = VARIABLE_NAME[0] + " / " + VARIABLE_NAME[1];
		else if (VARIABLE == 3)
			cellValue = VARIABLE_NAME[0] + VARIABLE_NAME[1] + " / " + VARIABLE_NAME[2];
		else if (VARIABLE == 4)
			cellValue = VARIABLE_NAME[0] + VARIABLE_NAME[1] + " / " + VARIABLE_NAME[2] + VARIABLE_NAME[3];

		aTextFields = new JTextField(cellValue);
		aTextFields.setPreferredSize(new Dimension(15, 15));
		aTextFields.setMaximumSize(new Dimension(15, 15));
		aTextFields.setMinimumSize(new Dimension(15, 15));
		aTextFields.setEditable(false);
		aTextFields.setBorder(BorderFactory.createEmptyBorder());
		aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		aTextFields.setHorizontalAlignment(SwingConstants.CENTER);
		panKMap.add(aTextFields);

		rowValue = valueTable[0];
		for (j = 0; j < rowValue.length; j++) {
			if (VARIABLE == 2)
				cellValue = rowValue[j].substring(0, 1);
			else if (VARIABLE == 3)
				cellValue = rowValue[j].substring(0, 2);
			else if (VARIABLE == 4)
				cellValue = rowValue[j].substring(0, 2);

			aTextFields = new JTextField(cellValue);
			aTextFields.setPreferredSize(new Dimension(15, 15));
			aTextFields.setMaximumSize(new Dimension(15, 15));
			aTextFields.setMinimumSize(new Dimension(15, 15));
			aTextFields.setEditable(false);
			aTextFields.setBorder(BorderFactory.createEmptyBorder());
			aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			aTextFields.setHorizontalAlignment(SwingConstants.CENTER);
			panKMap.add(aTextFields);
		}

		for (i = 0; i < rows; i++) {
			rowValue = valueTable[i];
			for (j = 0; j < rowValue.length; j++) {
				// add title
				if (j == 0) {
					if (VARIABLE == 2)
						cellValue = rowValue[j].substring(1);
					else if (VARIABLE == 3)
						cellValue = rowValue[j].substring(2);
					else if (VARIABLE == 4)
						cellValue = rowValue[j].substring(2);

					aTextFields = new JTextField(cellValue);
					aTextFields.setPreferredSize(new Dimension(15, 15));
					aTextFields.setMaximumSize(new Dimension(15, 15));
					aTextFields.setMinimumSize(new Dimension(15, 15));
					aTextFields.setEditable(false);
					aTextFields.setBorder(BorderFactory.createEmptyBorder());
					aTextFields.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
					aTextFields.setHorizontalAlignment(SwingConstants.CENTER);
					panKMap.add(aTextFields);
				}
				// button
				String buttonText = "0";
				if (KMap[i][j].equals("1")) {
					buttonText = "1";
				} else if (KMap[i][j].equals("x")) {
					buttonText = "x";
				} else {
					buttonText = "0";
				}

				listKMapButtons[i][j] = new JButton(buttonText);
				listKMapButtons[i][j].setPreferredSize(new Dimension(15, 15));
				listKMapButtons[i][j].setMaximumSize(new Dimension(15, 15));
				listKMapButtons[i][j].setMinimumSize(new Dimension(15, 15));
				listKMapButtons[i][j].setActionCommand("btnkamap" + i + "_" + j);
				listKMapButtons[i][j].addActionListener(this);
				listKMapButtons[i][j].setBorder(BorderFactory.createEmptyBorder());

				listKMapButtons[i][j].setForeground(Color.BLACK);

				if (KMap[i][j].equals("1")) {
					listKMapButtons[i][j].setBackground(new Color(40, 150, 97));
				} else if (KMap[i][j].equals("x")) {
					listKMapButtons[i][j].setBackground(Color.lightGray);
				} else {
					listKMapButtons[i][j].setBackground(Color.WHITE);
				}
				Border line = new LineBorder(Color.BLACK);
				Border margin = new EmptyBorder(5, 5, 5, 5);
				Border compound = new CompoundBorder(line, margin);
				listKMapButtons[i][j].setBorder(compound);

				// listButtons[i].setBorder(null);
				panKMap.add(listKMapButtons[i][j]);
			}
		}
	}

	private String[] getRowOrColumn (String[] aValueTruthTable, int anIndex,
			String[][] aValueKMapTable, int row, int column, int direction)
	{
		String[] theReturn = new String[2];
		String theValue;
		int i, j;
		
		switch (direction)
		{
		case 1:
			theValue = aValueTruthTable[anIndex];
			for (i = 0; i < aValueKMapTable.length; i++)
			{
				for (j = 0; j < aValueKMapTable[i].length; j++)
				{
					if (theValue.equalsIgnoreCase(aValueKMapTable[i][j]))
					{
						theReturn[0] = ""+ i;
						theReturn[1] = "" + j;
						
						return theReturn;
					}
				}
			}
			break;
			
		case 2:
			theValue = aValueKMapTable[row][column];
			for (i = 0; i < aValueTruthTable.length; i++)
			{
				if (theValue.equalsIgnoreCase(aValueTruthTable[i]))
				{
					theReturn[0] = ""+ i;
					theReturn[1] = "-1";
					
					return theReturn;
				}
			}
			
			break;
		}
		
		return theReturn;
	}
	
	private String getFunctionOutput (String[][] aValueKMapTable, String[][] theKMapTable)
	{
		String functionOutput = "";
		String theCellValue = "";
		String theValue = ""; 
		String oneTerm = "";
		int index = 0;
		
		// loop through the matrix vertically
		for (int j = 0; j < theKMapTable[0].length; j++)
		{
			for (int i = 0; i < theKMapTable.length; i++)
			{
				if (!theKMapTable[i][j].equals("0"))
				{
					theCellValue = aValueKMapTable[i][j];
					index = 0;
					oneTerm = "";
					
					if (!functionOutput.isEmpty())
					{
						functionOutput += "+";
					}
					
					while (theCellValue.length() > 0)
					{
						theValue = theCellValue.substring(0,1);
						theCellValue = theCellValue.substring(1);
																		
						if (theValue.equals("0"))
						{
							oneTerm += VARIABLE_NAME[index] + "'";
						}
						else
						{
							oneTerm += VARIABLE_NAME[index];
						}
						index++;
					}
					functionOutput += oneTerm;
				}
			}
		}
		
		return functionOutput;
	}

	private int[] getSubArray (String[] theTruthTable, int subArrayType)
	{
		int[] anArray = new int[theTruthTable.length + 1];
		int i, arrayIndex = 0;
		
		for (i = 0; i< anArray.length; i++)
		{
			anArray[i] = -1;
		}
		// for the 1s
		if (subArrayType == 1)
		{
			for (i = 0, arrayIndex = 0; i< theTruthTable.length; i++)
			{
				if (theTruthTable[i].equals("1"))
				{
					anArray[arrayIndex] = i;
					arrayIndex++;
				}
			}
			//anArray[arrayIndex] = -1;
		}
		// for don't care
		else if (subArrayType == 2)
		{
			for (i = 0, arrayIndex = 0; i< theTruthTable.length; i++)
			{
				if (theTruthTable[i].equals("x"))
				{
					anArray[arrayIndex] = i;
					arrayIndex++;
				}
			}
			//anArray[arrayIndex] = -1;
		}
		
		return anArray;
	}

}
