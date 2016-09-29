package states;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public abstract class GameState extends JPanel implements MouseListener
{
    public abstract void transition();
    public abstract void step();
    public abstract void paintComponent(Graphics g);
    @Override
    public abstract void mouseClicked(MouseEvent arg0);
    @Override
    public abstract void mouseEntered(MouseEvent arg0);
    @Override
    public abstract void mouseExited(MouseEvent arg0);
    @Override
    public abstract void mousePressed(MouseEvent arg0);
    @Override
    public abstract void mouseReleased(MouseEvent arg0);
    
        
}
