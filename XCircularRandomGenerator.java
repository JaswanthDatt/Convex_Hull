import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javax.swing.JPanel;

class XCircularRandomGenerator extends JPanel
{
		private static final double PI = 3.14;
		float increment;
		ArrayList<Point> lst=new ArrayList<Point>();
	    public XCircularRandomGenerator(float increase) {
			this.increment=increase;
		}
///////////////////////////////////////////////////Drawing Random Circular points///////////////////////////////////  
		public void doDrawing(Graphics g) 
	    {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setColor(Color.YELLOW);
	        setBackground(Color.BLACK);
	 
	        double angle=0.0;
	        for(int i=0;i<10;i++)
			{
				while(angle<2*PI)
				{				 
				 Random rnd=new Random();
				 int randomNum = rnd.nextInt((200 - 100) + 1) + 100;
				 double x=300+Math.cos(angle)*randomNum;
				 double y=300+Math.sin(angle)*randomNum; 
				 angle=angle+increment;
				 
				 Point p=new Point(x, y);
				 int PX=(int) p.X;
				 int PY=(int) p.Y;
				 lst.add(p);
				 g2d.fillOval(PX,PY,10,10);
				
			     }		
			}// for loop end
	        for(Point al:lst)
			{
				System.out.println(al.getX()+" "+al.getY());
			}
	///////////////////////////////////////////////////////Sort the arrayList using Comparable min y ////////////////////////////
			Collections.sort(lst);
			System.out.println("Sorting based on Y value ascending order");
			for(Point al:lst)
			{
				System.out.println(al.getX()+"************* "+al.getY());
			}
	////////////////////////////////////////////Map of (points, polar angles)//////////////////////////////////////////////////
			
			int count=0;
			LinkedHashMap<Point,Double> polar_angles=getPolarAngle(lst);
		    System.out.println(polar_angles);
			
	///////////////////////////////////////////Sorting Points according to Polar Angle//////////////////////////////////////////
			
			System.out.println(polar_angles.get(0));
			
			LinkedHashMap<Point,Double> map = polar_angles;
			// Iterate over hashmap
			
			for(Map.Entry<Point,Double> entry : map.entrySet()) {
		        System.out.println(entry.getValue());
		    } 
			
	///////////////////////////////////////////SORT Points in Linked-HASHMAP BASED ON POLAR ANGLES/////////////////////////////////////////////////////

			List<Map.Entry<Point, Double>> entries = new ArrayList<Map.Entry<Point, Double>>(map.entrySet());
				Collections.sort(entries, new Comparator<Map.Entry<Point, Double>>() {
					  public int compare(Map.Entry<Point, Double> a, Map.Entry<Point, Double> b){
					    return a.getValue().compareTo(b.getValue());
					  }
					});   
					
			Map<Point, Double> sortedMap = new LinkedHashMap<Point, Double>();
					for (Map.Entry<Point, Double> entry : entries)
					{
					  sortedMap.put(entry.getKey(), entry.getValue());
					}
			        System.out.println("LinkedHashMap after sorting");
			        
					for(Map.Entry<Point,Double> entry : sortedMap.entrySet()) {
				        System.out.println(entry.getValue());
				    } 
					
					System.out.println(sortedMap);
					
	///////////////////////////////Convert LinkedHashMap to List/////////////////////////////////////////////////////////
					
					List<Point> points_sorted_by_angle = new ArrayList<Point>(sortedMap.keySet());
					System.out.println(points_sorted_by_angle);
	////////////////////////////////////////////PUSH the TOP three Elements onto stack////////////////////////////////////////////////////////////////////////////////////
					List<Point> points_on_HULL=getElements_CONVEXHULL(points_sorted_by_angle);
					ConvexDraw cd=new ConvexDraw(points_on_HULL);
					cd.drawConvexHull(g2d);
					System.out.println("POINTS ON HULL ARE :"+points_on_HULL);   
					
					
		/*	 List<Point> polar_angles = new ArrayList<Point>(getSortedPointSet(lst));
			    System.out.println("@@@@@@@@"+polar_angles);
				
		///////////////////////////////////////////Sorting Points according to Polar Angle//////////////////////////////////////////
				
				System.out.println("Polar angles in traversal order are: "+polar_angles);
				
				List<Point> list = new ArrayList<Point>(polar_angles);
				
				Stack<Point> points_on_HULL=getElements_CONVEXHULL(list);
				for(int i=0;i<points_on_HULL.size();i++)
				{
					System.out.println(points_on_HULL.get(i));
				}
				System.out.println("POINTS ON HULL ARE :"+points_on_HULL); 
				
			*/
			
					
		} // end of doDrawing method

		
		private static Stack<Point> getElements_CONVEXHULL(List<Point> points_sorted_by_angle) 
		{
			Stack<Point> stk=new Stack<Point>();
			// ADD the first three elements by polar angle to the stack
			Point p1=points_sorted_by_angle.get(0);
			stk.push(p1);
			Point p2=points_sorted_by_angle.get(1);
			stk.push(p2);
			Point p3=points_sorted_by_angle.get(2);
			stk.push(p3);
			
			for(int i=2;i<points_sorted_by_angle.size()-1;i++)
			{
				if(isLeftTurn(points_sorted_by_angle.get(i-1),points_sorted_by_angle.get(i),points_sorted_by_angle.get(i+1))==true) {
					
					stk.push(points_sorted_by_angle.get(i));
				}
				else if(isRightTurn(points_sorted_by_angle.get(i-1),points_sorted_by_angle.get(i),points_sorted_by_angle.get(i+1))==true)
				{
				    stk.pop();
				}
			}
			stk.push(p1);
			System.out.println(stk);
			return stk;
		}

	///////////////////////////////////////////////RIGHT TURN///////////////////////////////////////////////////////////////

		private static boolean isRightTurn(Point point1, Point point2, Point point3) {
			// (x_2-x_1)(y_3-y_1)-(y_2-y_1)(x_3-x_1).
					double check_value=(point2.getX()-point1.getX())*(point3.getY()-point1.getY())-(point2.getY()-point1.getY())*(point3.getX()-point1.getX());
					if(check_value<0)
						return true;
					return false;
		}


	////////////////////////////////////////////////Left Turn///////////////////////////////////////////////////////////////////
		private static boolean isLeftTurn(Point point1, Point point2, Point point3) {
		
			// (x_2-x_1)(y_3-y_1)-(y_2-y_1)(x_3-x_1).
			double check_value=(point2.getX()-point1.getX())*(point3.getY()-point1.getY())-(point2.getY()-point1.getY())*(point3.getX()-point1.getX());
			if(check_value>0)
				return true;
			return false;
		}


	/////////////////////////////////////////////////////to Get Polar angle/////////////////////////////////////////////////////
		public static LinkedHashMap<Point, Double> getPolarAngle(ArrayList<Point> lst)
		{
			Point p=lst.get(0);
			double xo=p.getX();
			double yo=p.getY();
			
			LinkedHashMap<Point, Double> polar_angles=new LinkedHashMap<Point, Double>();
			System.out.println(xo+" #################origin################### "+yo);
			LinkedHashMap<Point, Double> polar_positive=new LinkedHashMap<Point, Double>();
			LinkedHashMap<Point, Double> polar_negative=new LinkedHashMap<Point, Double>();

			for(int i=1;i<lst.size();i++)
			{
				System.out.println(lst.get(i).getX()+"<<------------>>>"+lst.get(i).getY());
				double delta_y=lst.get(i).getY()-yo;
				double delta_x=lst.get(i).getX()-xo;
				
				double angle=delta_y/delta_x;	
				if(delta_x>0)
				{
					polar_positive.put(lst.get(i),angle);
				      
				}
				else if(delta_x<0)
				{
					polar_negative.put(lst.get(i),angle);
				}
				else if(delta_x==0)
				{
					
				}
			}
				List<Map.Entry<Point, Double>> positive_entries = new ArrayList<Map.Entry<Point, Double>>(polar_positive.entrySet());
				Collections.sort(positive_entries, new Comparator<Map.Entry<Point, Double>>() {
				  public int compare(Map.Entry<Point, Double> a, Map.Entry<Point, Double> b){
				    return (a.getValue().compareTo(b.getValue()));
				  }
				});
				
				List<Map.Entry<Point, Double>> negative_entries = new ArrayList<Map.Entry<Point, Double>>(polar_negative.entrySet());
				Collections.sort(negative_entries, new Comparator<Map.Entry<Point, Double>>() {
				  public int compare(Map.Entry<Point, Double> a, Map.Entry<Point, Double> b){
				    return (a.getValue().compareTo(b.getValue()));
				  }
				});
				
			polar_angles.put(p, 0.0);
				
			Map<Point, Double> sortedMap_positive = new LinkedHashMap<Point, Double>();
			for (Map.Entry<Point, Double> entry : positive_entries)
			{
				sortedMap_positive.put(entry.getKey(), entry.getValue());
				polar_angles.put(entry.getKey(),  entry.getValue());
			}
			  
			for(Map.Entry<Point,Double> entry : sortedMap_positive.entrySet()) {
		        System.out.println(entry.getKey()+"#####Positive######"+entry.getValue());
		    } 
			
			 System.out.println("##########################################################################");
			
			Map<Point, Double> sortedMap_negative = new LinkedHashMap<Point, Double>();
			for (Map.Entry<Point, Double> entry : negative_entries)
			{
				sortedMap_negative.put(entry.getKey(), entry.getValue());
				polar_angles.put(entry.getKey(),  entry.getValue());
			}
			 
			for(Map.Entry<Point,Double> entry : sortedMap_negative.entrySet()) {
		        System.out.println(entry.getKey()+"#####Negative######"+entry.getValue());
		    } 		
			
			return polar_angles;
		}
				
	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(500, 500);
	    }
	   
	    @Override
	    public void paintComponent(Graphics g)
	    {
	    	super.paintComponent(g);
		    doDrawing(g);
		}  
	    

		public static Set<Point> getSortedPointSet(List<Point> lst) {

		      final Point lowest = lst.get(0);
		       TreeSet<Point> set = new TreeSet<Point>(new Comparator<Point>() {
		           @Override
		            public int compare(Point a, Point b) {

		                if(a == b || a.equals(b)) {
		                    return 0;
		                }

		                // use longs to guard against int-underflow
		                double thetaA = Math.atan2((long)a.Y - lowest.Y, (long)a.X - lowest.X);
		                double thetaB = Math.atan2((long)b.Y - lowest.Y, (long)b.X - lowest.X);
		                if(thetaA < thetaB) {
		                    return -1;
		                }
		                else if(thetaA > thetaB) {
		                   return 1;
		               }
		               else {
		                    // collinear with the 'lowest' point, let the point closest to it come first
		                    // use longs to guard against int-over/underflow
		                    double distanceA = Math.sqrt((((long)lowest.X - a.X) * ((long)lowest.Y - a.X)) +
		                                              (((long)lowest.Y - a.Y) * ((long)lowest.Y - a.X)));
		                    double distanceB = Math.sqrt((((long)lowest.X - b.X) * ((long)lowest.X - b.X)) +
		                                                (((long)lowest.Y - b.Y) * ((long)lowest.Y - b.Y)));

		                    if(distanceA < distanceB) {
		                        return -1;
		                    }
		                    else {
		                        return 1;
		                    }
		                }
		            }
		        });

		        set.addAll(lst);

		        return set;
		   }

}// end of program
