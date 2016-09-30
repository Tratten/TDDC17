import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TutorialController extends Controller {

    public SpringObject object;

    ComposedSpringObject cso;

    /* These are the agents senses (inputs) */
	DoubleFeature x; /* Positions */
	DoubleFeature y;
	DoubleFeature vx; /* Velocities */
	DoubleFeature vy;
	DoubleFeature angle; /* Angle */

    /* Example:
     * x.getValue() returns the vertical position of the rocket 
     */

	/* These are the agents actuators (outputs)*/
	RocketEngine leftRocket;
	RocketEngine middleRocket;
	RocketEngine rightRocket;

    /* Example:
     * leftRocket.setBursting(true) turns on the left rocket 
     */
	
	DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US); 
	
	public void init() {
		System.out.println("hejhej");
		cso = (ComposedSpringObject) object;
		x = (DoubleFeature) cso.getObjectById("x");
		y = (DoubleFeature) cso.getObjectById("y");
		vx = (DoubleFeature) cso.getObjectById("vx");
		vy = (DoubleFeature) cso.getObjectById("vy");
		angle = (DoubleFeature) cso.getObjectById("angle");

		leftRocket = (RocketEngine) cso.getObjectById("rocket_engine_left");
		rightRocket = (RocketEngine) cso.getObjectById("rocket_engine_right");
		middleRocket = (RocketEngine) cso.getObjectById("rocket_engine_middle");
	}

    public void tick(int currentTime) {
    	middleRocket.setBursting(false);

    	/* TODO: Insert your code here */
		double tmpVx = vx.getValue();
		double tmpVy = vy.getValue();
		double tmpAngle = angle.getValue();
		
		if(y.getValue() > 0) {
			middleRocket.setBursting(true);
		}
		
    	System.out.println("sensors vx: " + df.format(tmpVx) + " vy: " + df.format(tmpVy) + " angle: " + df.format(tmpAngle));
    	
    }

}
