package coreRummikubGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

import rummikub.Maine;

// Houses the GUI controls in the top stripe of the GameOfLifeFrame
// Intentionally not public--only classes in this package should need to use this
class HeaderPanel extends JPanel
{
	private RummikubPanel frame;
	private JComboBox<String> tileTypeCombo;
	//private JTextField ageText;
	//private JTextField stepsText;
	private JLabel gameText;
	//private JTextField pauseText;
	
	// Contains the GUI controls in the top portion of the window, using
	// the following (rough) organization:
	//
	// Tile type: <ComboBox>   Age: <EditControl> [FILL]
	// Steps: <EditControl>   Pause ms: <EditControl> [EVOLVE]
	public HeaderPanel(RummikubPanel frameP)
	{
		super();
		
		frame = frameP;
		
		// First stripe: reset & end commands
		
		JPanel resetCmdPanel = new JPanel();
		GroupLayout resetCmdLayout = new GroupLayout(resetCmdPanel);
		resetCmdPanel.setLayout(resetCmdLayout);
		resetCmdLayout.setAutoCreateGaps(true);
		resetCmdLayout.setAutoCreateContainerGaps(true);

		//JLabel setAndFillInstructions = new JLabel("Click grid to SET individual tile or button to FILL");
		//JLabel tileTypeLabel = new JLabel("Tile type:");
		//tileTypeCombo = new JComboBox<String>(new String[] { "constant", "rainbow", "mono", "quad", "immigration"});
		//JLabel ageLabel = new JLabel("Age:");
		//ageText = new JTextField("1", 10);
		JButton endButton = new JButton("End Turn");
		endButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				frame.sendEndCommand();
			}
		});
		
		resetCmdLayout.setHorizontalGroup(
				resetCmdLayout.createSequentialGroup()
				//.addGroup(resetCmdLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						//.addComponent(setAndFillInstructions)
						//.addGroup(setCmdLayout.createSequentialGroup()
								//.addComponent(tileTypeLabel)
								//.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								//.addComponent(tileTypeCombo)
								//.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								//.addComponent(ageLabel)
								//.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								//.addComponent(ageText)
								//.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(endButton));
		
		resetCmdLayout.setVerticalGroup(
				resetCmdLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				//.addComponent(setAndFillInstructions)
				//.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				//.addGroup(resetCmdLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						//.addComponent(tileTypeLabel)
						//.addComponent(tileTypeCombo)
						//.addComponent(ageLabel)
						//.addComponent(ageText)
						.addComponent(endButton));
		
		TitledBorder resetCmdBorder = BorderFactory.createTitledBorder("end button");
		resetCmdPanel.setBorder(resetCmdBorder);

		// Second stripe: reset command
		
		JPanel endCmdPanel = new JPanel();
		GroupLayout endCmdLayout = new GroupLayout(endCmdPanel);
		endCmdPanel.setLayout(endCmdLayout);
		endCmdLayout.setAutoCreateGaps(true);
		endCmdLayout.setAutoCreateContainerGaps(true);
		
		//JLabel stepsLabel = new JLabel("Number of steps:");
		//stepsText = new JTextField("10", 4);
		//JLabel pauseLabel = new JLabel("Pause ms:");
		//pauseText = new JTextField("400", 6);
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				frame.sendResetCommand();
			}
		});

		endCmdLayout.setHorizontalGroup(
				endCmdLayout.createSequentialGroup()	        
				//.addComponent(stepsLabel)
				//.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				//.addComponent(stepsText)
				//.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				//.addComponent(pauseLabel)
				//.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				//.addComponent(pauseText)
				//.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,      GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(resetButton));

		endCmdLayout.setVerticalGroup(
				endCmdLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				//.addGroup(endCmdLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						//.addComponent(stepsLabel)
						//.addComponent(stepsText)
						//.addComponent(pauseLabel)
						//.addComponent(pauseText)
						.addComponent(resetButton));
		
		TitledBorder endCmdBorder = BorderFactory.createTitledBorder("reset button");
		endCmdPanel.setBorder(endCmdBorder);
		
		//Wild buttons to set wild card from game screen
		JPanel wildCmdPanel = new JPanel();
		GroupLayout wildCmdLayout = new GroupLayout(wildCmdPanel);
		wildCmdPanel.setLayout(wildCmdLayout);
		wildCmdLayout.setAutoCreateGaps(true);
		wildCmdLayout.setAutoCreateContainerGaps(true);
		
		JButton blueButton = new JButton(" ");
		blueButton.setBackground(Color.BLUE);
		blueButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				frame.sendTextCommand("blue");
			}
		});
		
		JButton greenButton = new JButton(" ");
		greenButton.setBackground(Color.GREEN);
		greenButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				frame.sendTextCommand("green");
			}
		});
		
		JButton redButton = new JButton(" ");
		redButton.setBackground(Color.RED);
		redButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				frame.sendTextCommand("red");
			}
		});
		
		JButton blackButton = new JButton(" ");
		blackButton.setBackground(Color.BLACK);
		blackButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				frame.sendTextCommand("black");
			}
		});
		
		JButton[] numberButtons = new JButton[13];
		
		for (int i = 0; i < numberButtons.length; i++) {
			
			final int buttonValue = i + 1;
			numberButtons[i] = new JButton(buttonValue + "");
			
			numberButtons[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					frame.sendTextCommand(buttonValue + "");
				}
			});
		}
		
		GroupLayout.SequentialGroup horiz = wildCmdLayout.createSequentialGroup()
				.addComponent(blueButton)
				.addComponent(greenButton)
				.addComponent(redButton)
				.addComponent(blackButton);
		for (int i = 0; i < numberButtons.length; i++) {
			horiz.addComponent(numberButtons[i]);
		}
		wildCmdLayout.setHorizontalGroup(horiz);
		
		GroupLayout.ParallelGroup vert = wildCmdLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(blueButton)
				.addComponent(greenButton)
				.addComponent(redButton)
				.addComponent(blackButton);
		for (int i = 0; i < numberButtons.length; i++) {
			vert.addComponent(numberButtons[i]);
		}
		wildCmdLayout.setVerticalGroup(vert);
		
		TitledBorder wildCmdBorder = BorderFactory.createTitledBorder("wild modifier");
		wildCmdPanel.setBorder(wildCmdBorder);
		
		
		// text display
		JPanel textCmdPanel = new JPanel();
		GroupLayout textCmdLayout = new GroupLayout(textCmdPanel);
		textCmdPanel.setLayout(textCmdLayout);
		textCmdLayout.setAutoCreateGaps(true);
		textCmdLayout.setAutoCreateContainerGaps(true);
		
		gameText = new JLabel("Rummikub");
		
		textCmdLayout.setHorizontalGroup(textCmdLayout.createSequentialGroup().addComponent(gameText));
		textCmdLayout.setVerticalGroup(textCmdLayout.createSequentialGroup().addComponent(gameText));
		
		// Combine the lines together
		
		GroupLayout controlLayout = new GroupLayout(this);
		setLayout(controlLayout);
		controlLayout.setAutoCreateGaps(true);
		controlLayout.setAutoCreateContainerGaps(true);
		
		controlLayout.setHorizontalGroup(
				controlLayout.createSequentialGroup()
				//.addGroup(controlLayout.createSequentialGroup()
						.addComponent(resetCmdPanel)
						.addComponent(endCmdPanel)
						.addComponent(wildCmdPanel)
						.addComponent(textCmdPanel));
		
		controlLayout.setVerticalGroup(
				controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(resetCmdPanel)
				.addComponent(endCmdPanel)
				.addComponent(wildCmdPanel)
				.addComponent(textCmdPanel));
	}
	
	public String getGameText() {
		return gameText.getText();
	}

	public void updateGameText() {
		String text = Maine.game.getDisplayText();
		System.out.println("gameText is \"" + Maine.game.getDisplayText() +  "\"");
		this.gameText.setText(Maine.game.getDisplayText());
	}

	public String getTileType()
	{
		return (String) tileTypeCombo.getSelectedItem();
	}
	
}