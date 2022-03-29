package coreRummikubGUI;


import java.awt.BorderLayout;
import javax.swing.JPanel;
import core.CustomAppearance;
import core.ObjectLandPanel;
import rummikub.Maine;

// The big cheese around presenting the Game with GUI controls.  This
// panel contains HeaderPanel (with controls) and ObjectLandPanel (the grid).
// It also establishes the input demuxer to combine GUI commands and
// Scanner console commands, and establishes a dedicated mouse listener.
public class RummikubPanel extends JPanel
{
	// ----------------------------------------------------------------------
	// STATICS
	// ----------------------------------------------------------------------
	
    // Need one of these to avoid serializable class w/out UUID warning
	private static final long serialVersionUID = 8353462461769949516L;
	
	private static boolean testMode = false;
	private static InputDemux inputDemux = null;
	
	/**
	 * Do not call; for internal testing purposes only
	 */
	public static void enableTestMode()
	{
		testMode = true;
	}

	/**
	 * Call this first thing in main to enable the extra Rummikub controls inside the
	 * frame housing the ObjectLandPanel grid.
	 */
	public static void useRummikubControls()
	{
		// We don't want any of this stdin demux nonsense gumming up the works
		// when automated tests run, so refuse to any GUI GoL customizations then.
		if (!testMode)
		{
			inputDemux = new InputDemux();
			core.API.useCustomFrame(RummikubPanel::createRummikubFrame);
		}
	}
	
	// Callback passed to core.API to create and initialize a GameOfLifePanel
	// at the right time
	private static JPanel createRummikubFrame(ObjectLandPanel olPanel, CustomAppearance ca)
	{
		return new RummikubPanel(olPanel, ca);
	}
	
	// ----------------------------------------------------------------------
	// INSTANCE
	// ----------------------------------------------------------------------
	
	private HeaderPanel headerPanel;
	private ObjectLandPanel olPanel;
	
	public RummikubPanel(ObjectLandPanel olPanelP, CustomAppearance ca)
	{
		headerPanel = new HeaderPanel(this);
		olPanel = olPanelP;
		
		// If student didn't customize window title away from
		// the Object Land default, set it to Game of Life now
		if (ca.getWindowTitle().equals("Object Land"))
		{
			ca.setWindowTitle("Rummikub");
		}
		
		new MouseListener(this, olPanel);
		setLayout(new BorderLayout());
		add(headerPanel, BorderLayout.PAGE_START);
		add(olPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void sendTakeCommand()
	{
		int index = olPanel.getMouseRow();
		int group = olPanel.getMouseColumn();
		if (index < 0 || index >= olPanel.getRows() || group < 0 || group >= olPanel.getColumns())
		{
			return;
		}
		
		headerPanel.updateGameText();
		inputDemux.sendSupplementalCommand("take " + group + " " + index);
	}
	
	public void sendPlaceCommand()
	{
		int index = olPanel.getMouseRow();
		int group = olPanel.getMouseColumn();
		if (index < 0 || index >= olPanel.getRows() || group < 0 || group >= olPanel.getColumns())
		{
			return;
		}
		
		headerPanel.updateGameText();
		inputDemux.sendSupplementalCommand("place " + group + " " + index);
	}
	
	public void sendEndCommand()
	{
		headerPanel.updateGameText();
		inputDemux.sendSupplementalCommand("end");
	}

	
	public void sendResetCommand()
	{
		headerPanel.updateGameText();
		inputDemux.sendSupplementalCommand("reset");
	}
	
	public void sendTextCommand(String text)
	{
		headerPanel.updateGameText();
		inputDemux.sendSupplementalCommand(text);
	}
	
}
