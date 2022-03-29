package coreRummikubGUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import core.ObjectLandPanel;
import rummikub.Maine;

// Dedicated mouse listener so we don't have to poll using the simplified
// ObjectLand API, which would require another thread, more sleeping, and would be slower.
// Intentionally not public--only classes in this package should need to use this
class MouseListener extends MouseAdapter
{
	private RummikubPanel frame;
	private ObjectLandPanel olPanel;
	
	public MouseListener(RummikubPanel frameP, ObjectLandPanel olPanelP)
	{
		frame = frameP;
		olPanel = olPanelP;
		
		olPanel.addMouseListener(this);
		olPanel.addMouseMotionListener(this);
	}

    // Dispatcher calls this when button is pressed
    @Override
    public void mousePressed(MouseEvent e)
    {
        // Only expected mouse events are accepted

        if (e.getID() != MouseEvent.MOUSE_PRESSED)
        {
            return;
        }
        
        int button = e.getButton();

        if (button == MouseEvent.BUTTON1 && Maine.game.isPickedUp())
        {
        	frame.sendPlaceCommand();
        } 
        else if (button == MouseEvent.BUTTON1 && !Maine.game.isPickedUp())
        {
        	frame.sendTakeCommand();
        }
        return;
    }
    
    // Dispatcher calls this when the mouse is dragged
    /*
    public void mouseDragged(MouseEvent e)
    {
        // Only expected mouse events are accepted

        if (e.getID() != MouseEvent.MOUSE_DRAGGED)
        {
            return;
        }

        frame.sendSetCommand();
    }	  
    */  
}	