import java.util.Random;
public class Particle {
	public  double rx, ry; // Position of Particle.
	private double vx, vy; // Velocity of Particle.
	public final double radius; // Radius of Particle.
	private final int mass; // Mass of Particle.
	public int count; // No. of collisions
	/* Assigin Random Property values to partcile and convert it to unit box value */
	public Particle() {
		Random rm = new Random();
		rx = (double)(rm.nextInt(450));
		rx = rx / 500;
		ry = (double)(rm.nextInt(450));
		ry = ry / 500;
		vx = (double)(rm.nextInt(10));
		vx = vx / 500;
		vy = (double)(rm.nextInt(10));
		vy = vy / 500;
		radius = (double)20/500;
		mass = 10;
		count = 0;
		System.out.println("Particle Defined with following parameters :"+rx + ":" + ry + ":"+ vx + ":" + vy);
	}
	/* Move particle forward in straight line */
	public void move() {
		this.rx = this.rx+this.vx;
		this.ry = this.ry+this.vy;
	}
 //Collision Prediction
	public double TimeToHit(Particle that) {
		if(this == that) return Double.POSITIVE_INFINITY;
		double drx = (that.rx - rx) ; double dvx = (that.vx - vx);
		double dry = (that.ry - ry) ; double dvy = (that.vy - vy);
		double dvdr = (dvx * drx) + (dvy * dry);
		if(dvdr > 0) return Double.POSITIVE_INFINITY;
		double dvdv = (dvx * dvx) + (dvy * dvy);
		double drdr = (drx * drx) + (dry * dry);
		double sigma = (that.radius + radius);
		double d = (dvdr * dvdr) - (dvdv)*(drdr - (sigma * sigma));
		if(d < 0) return Double.POSITIVE_INFINITY;
		return -(dvdr + Math.sqrt(d))/dvdv;
	}
	public double TimeToHitVerticalWall() {
		//System.out.println("Vertical :"+rx+":"+vx);
		if(vx >= 0) return (1 - rx - radius) / vx;
		return - ((rx - radius) / vx);
	}
	public double TimeToHitHorizantalWall() {
		//System.out.println("Horizantal :"+ry+":"+vy);
		if(vy >= 0) return (1 - ry - radius) / vy;
		return - ((ry - radius) / vy);
	}
	// Collision Resolution.
	public void bounceOff(Particle that) {
		double dx  = that.rx - this.rx, dy  = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx*dvx + dy*dvy;       
		double dist = this.radius + that.radius;   
		double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
		double Jx = J * dx / dist;
		double Jy = J * dy / dist;
		this.vx += Jx / this.mass;
		this.vy += Jy / this.mass;
		that.vx -= Jx / that.mass;
		that.vy -= Jy / that.mass;
		this.count++;
		that.count++;
	}
	public void bounceOfVerticalWall() {
		vx = vx*-1;
		count++;
	}
	public void bounceOfHorizantalWall() {
		vy = vy*-1;
		count++;
	}
}