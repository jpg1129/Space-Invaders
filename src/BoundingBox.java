import java.awt.Rectangle;

/**
 * Interface BoundingBox used with objects 
 * @author JamesGomatos
 *
 */
public interface BoundingBox
{
	public Rectangle getBoundingBox();

	public double getBoundingBoxWidth();

	public double getBoundingBoxHeight();

	public double getBoundingBox_Max_X();
	
	public double getBoundingBox_Max_Y();

	public double getBoundingBox_Min_X();

	public double getBoundingBox_Min_Y();

	public void setBoundingBox(double xP, double yP, double width,
			double height);

	public void updateBoundingBox(double xP, double yP);

	public boolean intersectsBoundingBox(Rectangle random);
}
