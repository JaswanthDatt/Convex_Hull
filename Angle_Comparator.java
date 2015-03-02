import java.util.ArrayList;
import java.util.Comparator;


public class Angle_Comparator implements Comparator<Point>{

	Point origin=new Point();;
	ArrayList<Point> point_list;
	
	public Angle_Comparator(Point p) {
		origin.X=p.getX();
		origin.Y=p.getY();
		
	}

	 public double angleTo(Point that) {
	        double dx = that.X - origin.X;
	        double dy = that.Y - origin.Y;
	        return Math.atan2(dy, dx);
	    }

	
	public int compare(Point p1,Point p2) {
			
			double angle1=angleTo(p1);
			double angle2=angleTo(p2);
			
			if(angle1>angle2)
				return 1;
			else if(angle1<angle2)
				return -1;
			return 0;
										
		
		
}
}