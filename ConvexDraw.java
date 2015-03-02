import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;


public class ConvexDraw extends JPanel{

	List<Point> stk_pnts;
	public ConvexDraw(List<Point> points_on_HULL) 
	{
		this.stk_pnts=points_on_HULL;
	}
	public void drawConvexHull(Graphics g) 
	{
		double x_c=0,y_c=0;
		Graphics2D g2 = (Graphics2D) g;
		
		for(int i=0;i<stk_pnts.size()-1;i++)
		{
			Point p1=stk_pnts.get(i);
			Point p2=stk_pnts.get(i+1);
			g.setColor(Color.RED);
			g2.draw(new Line2D.Double(p1.X, p1.Y, p2.X, p2.Y));			
			//g.drawLine((int)p1.X,(int)p1.Y,(int)p2.X,(int)p2.Y);
		}
	
	}

	  public Dimension getPreferredSize() 
	  {
	        return new Dimension(500, 500);
	  }
	  
	   	 

@Override
public void paintComponent(Graphics g){
	super.paintComponent(g);  //paint background
	drawConvexHull(g);
}
	
}


