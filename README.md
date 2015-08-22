# Convex_Hull
GUI Application for Convex hull using Graham Scan </br>

<b> Introduction </b></br>
Convex Hull is set covering where every point lies within the set covering.<i>Graham Scan Algorithm</i> has been used to plot the convex hull for the given set of points.
We have generated the points randomly in a circular path.

int randomNum = rnd.nextInt((200 - 100) + 1) + 100; </br>
double x=300+Math.cos(angle)*randomNum;   </br>
double y=300+Math.sin(angle)*randomNum; </br>
angle=angle+increment;  </br>

Based on the Angular increment given as input,the random points were generated.We apply <i>Graham Scan Algorithm</i> on the set of randomly generated points to generate a convex hull.
