
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

import javax.swing.JPanel;

public final class CircularRandomGenerator extends JPanel{
	private static final double PI = 3.14;
	float increment=0;
	public CircularRandomGenerator(float increase) {
		this.increment=increase;
	}


	public void doDrawing(Graphics g) 
    {
		//super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        setBackground(Color.BLACK);
 
		 double angle=0.0;
		 ArrayList<Point> lst=new ArrayList<Point>();
	       
		while(angle<2*PI)
				{				 
					 Random rnd=new Random();
					 int randomNum = rnd.nextInt((200 - 100) + 1) + 100;
					 double x=300+Math.cos(angle)*randomNum;
					 double y=300+Math.sin(angle)*randomNum; 
					 angle=angle+increment;
					
					 	 
				 
				 Point p=new Point(x,y);
				 int PX=(int) p.X;
				 int PY=(int) p.Y;
				 g2d.fillOval(PX,PY,2,2);
				 lst.add(p);
				}    
	/*     Point a = new Point(58, 39);
		 Point b = new Point(99,41);
         Point c = new Point(54,50);
         Point d = new Point(51,86);
         Point e = new Point(30,52);0.03925
         Point f = new Point(100,67);
         Point g1 = new Point(52, 45);    
         Point h = new Point(83, 17);
         Point x = new Point(81, 88); 
         Point e21 = new Point(69, 24);
         Point f21 = new Point(70, 19);
         Point g21 = new Point(90, 21);
         Point h21 = new Point(16,33);
         Point x21 = new Point(76, 79);
         Point x29 = new Point(20, 33); 
         Point x20 = new Point(56, 6); 
         Point x28 = new Point(27, 98);   
       
         
        lst.add(a);
         lst.add(b);
         lst.add(c);
         lst.add(d);
        lst.add(e);
        lst.add(f);
         lst.add(g1);  
         lst.add(h);
        lst.add(x);   
       
         lst.add(e21);
         lst.add(f21);
         lst.add(g21);
         lst.add(h21);
         lst.add(x21); 
         
         lst.add(x29);
         lst.add(x20);
         lst.add(x28);     */
		
	/*			 for(Point al:lst)
					{
						System.out.println(al.getX()+"<><><><>"+al.getY());
					}  */
			///////////////////////////////////////////////////////Sort the arrayList using Comparable min y ////////////////////////////
					//Arrays.asList(a, b, c, d, e, f, g, h, i);
					Collections.sort(lst, new Comparator<Point>() {
					    @Override
					    public int compare(Point z1, Point z2) {
					        if (z1.getY() > z2.getY())
					            return 1;
					        if (z1.getY() < z2.getY())
					            return -1;
					        return 0;
					    }
					});
					
		//			System.out.println();
		//			System.out.println("Sorting based on Y value ascending order");
		/*			for(Point al:lst)
					{
						System.out.println(al.getX()+"************* "+al.getY());
					}   */
			////////////////////////////////////////////Map of (points, polar angles)//////////////////////////////////////////////////
					Point origin=lst.get(0);
					int count=0;
					LinkedHashMap<Point,Double> polar_angles=getPolarAngle(lst);
					
				
			//	    System.out.println("@@@@@@@@"+polar_angles);
					
			///////////////////////////////////////////Sorting Points according to Polar Angle//////////////////////////////////////////
				    List<Point>sorted_Point_list = new ArrayList<Point>(polar_angles.keySet());
			/*	    for(int i=0;i<sorted_Point_list.size();i++)
				    {
				    	System.out.println(sorted_Point_list.get(i).getX()+" "+sorted_Point_list.get(i).getY());
				    }  
					System.out.println("Polar angles in traversal order are: "+polar_angles);  */
					
				///////////////////////////////////////////////////////////////////////////////////////////////////////
							Stack<Point> points_on_HULL=getElements_CONVEXHULL(sorted_Point_list,origin);
					/*		for(int i=0;i<points_on_HULL.size();i++)
							{
								System.out.println(points_on_HULL.get(i).getX()+","+points_on_HULL.get(i).getY());
							}
							System.out.println("POINTS ON HULL ARE :"+points_on_HULL);  */
							ConvexDraw cd=new ConvexDraw(points_on_HULL);
							cd.drawConvexHull(g2d);
							
				}// end of MAIN



			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

				
				public static Stack<Point> getElements_CONVEXHULL(List<Point> points_sorted_by_angle,Point orgin) 
				{
					
					Stack<Point> stk=new Stack<Point>();
					// ADD the first three elements by polar angle to the stack
					stk.push(orgin);
					Point p1=points_sorted_by_angle.get(0);
					stk.push(p1);
					Point p2=points_sorted_by_angle.get(1);
					stk.push(p2);
			//		System.out.println("initally"+orgin.X+","+orgin.Y+">>"+p1.X+","+p1.Y+">>"+p2.X+","+p2.Y);
					
			/*		for(int i=0;i<points_sorted_by_angle.size();i++)
					{
						//System.out.println("*********"+points_sorted_by_angle.get(i));
					}
					*/
					for(int i=2;i<points_sorted_by_angle.size();i++)
					{
						Point third_ele=points_sorted_by_angle.get(i);
								
						Point second_ele=stk.pop();
						Point first_ele=stk.peek();
					//	System.out.println(first_ele.getX()+", "+first_ele.getY()+">>>"+second_ele.getX()+" ,"+second_ele.getY()+">>> "+third_ele.getX()+" "+third_ele.getY());
						if(isLeftTurn(first_ele,second_ele,third_ele)==true) 
						{
							stk.push(second_ele);
							stk.push(third_ele);
						//	System.out.println("LeftTurn is made "+third_ele.X+","+third_ele.Y+"  is pushed on stack");
						}
						else if(isRightTurn(first_ele,second_ele,third_ele)==true)
						{
							i--;
						 // stk.push(first_ele);
							//Point right=stk.pop();
						//	System.out.println("RightTurn is made "+right.X+","+right.Y+"  is deleted on stack");
							
							
						}
						else if(isCollinear(first_ele,second_ele,third_ele)==true)
						{
							stk.push(third_ele);
						}
						
					}				
					
					stk.push(orgin);
				//	System.out.println(stk);
					return stk;
				}

			private static boolean isCollinear(Point point1,	Point point2, Point point3) {
				
				double check_value=((point2.getX()-point1.getX())*(point3.getY()-point2.getY()))-((point2.getY()-point1.getY())*(point3.getX()-point2.getX()));
				if(check_value==0.0)
				{
				//	System.out.println("check value is"+check_value);
					return true;
				}
				return false;
				
			}


			///////////////////////////////////////////////RIGHT TURN///////////////////////////////////////////////////////////////

				private static boolean isRightTurn(Point point1, Point point2, Point point3) {
					// (x_2-x_1)(y_3-y_2)-(y_2-y_2)(x_3-x_2).
			  				double check_value=((point2.getX()-point1.getX())*(point3.getY()-point2.getY()))-((point2.getY()-point1.getY())*(point3.getX()-point2.getX()));
							if(check_value<0.0)
							{
						//		System.out.println("check value is"+check_value);
								return true;
							}
							return false;
				}


			////////////////////////////////////////////////Left Turn///////////////////////////////////////////////////////////////////
				private static boolean isLeftTurn(Point point1, Point point2, Point point3) {
				
					// (x_2-x_1)(y_3-y_1)-(y_2-y_1)(x_3-x_1).
					double check_value=((point2.getX()-point1.getX())*(point3.getY()-point2.getY()))-((point2.getY()-point1.getY())*(point3.getX()-point2.getX()));
					
					if(check_value > 0.0)
					{
					//	System.out.println("check value is:"+check_value);
						return true;
					}
					return false;
				}


			/////////////////////////////////////////////////////to Get Polar angle/////////////////////////////////////////////////////
				public static LinkedHashMap<Point, Double> getPolarAngle(ArrayList<Point> lst)
				{
				final Point p=lst.get(0);
				final double xo=p.getX();
				final double yo=p.getY();
					
					LinkedHashMap<Point, Double> polar_angles=new LinkedHashMap<Point, Double>();
				//	System.out.println(xo+" #################origin################### "+yo);
					LinkedHashMap<Point, Double> polar_positive=new LinkedHashMap<Point, Double>();
					LinkedHashMap<Point, Double> polar_negative=new LinkedHashMap<Point, Double>();

					for(int i=1;i<lst.size();i++)
					{
					//	System.out.println(lst.get(i).getX()+"<<------------>>>"+lst.get(i).getY());
						double delta_y=lst.get(i).getY()-yo;
						double delta_x=lst.get(i).getX()-xo;
						
						double angle=delta_y/delta_x;	
						if(delta_x>=0)
						{
							polar_positive.put(lst.get(i),angle);
							lst.get(i).polar_angle=angle;
						      
						}
						else if(delta_x<0)
						{
							polar_negative.put(lst.get(i),angle);
							lst.get(i).polar_angle=angle;
						}
					
					}
						List<Map.Entry<Point, Double>> positive_entries = new ArrayList<Map.Entry<Point, Double>>(polar_positive.entrySet());
						Collections.sort(positive_entries, new Comparator<Map.Entry<Point, Double>>() {
						  public int compare(Map.Entry<Point, Double> a, Map.Entry<Point, Double> b){
							return a.getValue().compareTo(b.getValue());
						  }
							
						});
						
						List<Map.Entry<Point, Double>> negative_entries = new ArrayList<Map.Entry<Point, Double>>(polar_negative.entrySet());
						Collections.sort(negative_entries, new Comparator<Map.Entry<Point, Double>>() {
						  public int compare(Map.Entry<Point, Double> a, Map.Entry<Point, Double> b){
							  return a.getValue().compareTo(b.getValue());
							  
							  }			
						});
						
					
						
					Map<Point, Double> sortedMap_positive = new LinkedHashMap<Point, Double>();
					for (Map.Entry<Point, Double> entry : positive_entries)
					{
						sortedMap_positive.put(entry.getKey(), entry.getValue());
						polar_angles.put(entry.getKey(),  entry.getValue());
					}
					  
			/*		for(Map.Entry<Point,Double> entry : sortedMap_positive.entrySet()) {
				        System.out.println(entry.getKey().X+","+entry.getKey().Y+ " #####Positive###### "+entry.getValue());
				    }   */
					
					//polar_angles.put(p, 0.0);
			//		 System.out.println("##########################################################################");
					
					Map<Point, Double> sortedMap_negative = new LinkedHashMap<Point, Double>();
					for (Map.Entry<Point, Double> entry : negative_entries)
					{
						sortedMap_negative.put(entry.getKey(), entry.getValue());
						polar_angles.put(entry.getKey(),  entry.getValue());
					}
			/*		 
					for(Map.Entry<Point,Double> entry : sortedMap_negative.entrySet()) {
				        System.out.println(entry.getKey().X+","+entry.getKey().Y+" #####Negative###### "+entry.getValue());
				    } 		*/
					
					return polar_angles;
				}  
					
			
					@Override
				   public Dimension getPreferredSize() {
				       return new Dimension(600, 600);
				    }
				   
				    @Override
				    public void paintComponent(Graphics g)
				    {
				    	super.paintComponent(g);				    	
					    doDrawing(g);
					   
					}  


	   }

	
 
