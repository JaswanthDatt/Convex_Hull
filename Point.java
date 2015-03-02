import java.util.Comparator;


public class Point implements Comparable<Point>{
	double X;
	double Y;
	double polar_angle;
	
	public Point(double X,double Y)
	{
		this.X=X;
		this.Y=Y;
	}
	
	public Point() {
		
	}

	public void setX(double X)
	{
		this.X=X;
	}
	
	public void setY(double Y)
	{
		this.Y=Y;
	}
	public double getX()
	{
		return X;
	}
	
	public double getY()
	{
		return Y;
	}
	
	public double getPolarYX()
	{
		return polar_angle;
	}
	
	public void setPolarYX(double X,double Y)
	{
		polar_angle=Math.atan2(this.getY(),this.getX());
	}
	
   public boolean equals(Point p) 
   {
      if (this == p) return true;
      if (p == null || getClass() != p.getClass()) 
      {
            return false;
       } 
      else
      {
            return compareTo((Point) p) == 0;
      }
    }
   @Override
   public int compareTo(Point p)
   {
	if (p == null)
	{
        throw new NullPointerException("Attempted to compare " + this + " to null");
    } 
	else if (!this.getClass().equals(p.getClass()))
	{
        throw new ClassCastException("Possible ClassLoader issue. Failed attempt to compare " + this + " to " + p);
    }
	else if (this.Y < p.Y) 
	{
        return -1;
    } 
	else if (this.Y < p.Y) 
	{
        return 1;
    }
    return 0;
}   
	
	
}

