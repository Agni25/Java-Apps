import java.applet.*;
import java.awt.*;
public class CollisionSystem extends Applet {
	private MinPq<Event> pq; // Priority Queue, storing all the probable events.
	private Particle[] particles; // Particles array.
	private int N; // Total number of particles.
	private double timer; // System Clock.
	private void predict(Particle a) {
		if(a == null) return;
		for(int i = 0; i < N; i++) {
			double dt = (a.TimeToHit(particles[i]) / 10);
			System.out.println("Collision thith other particle at : "+dt);
			pq.insert(new Event((dt + timer), a, particles[i]));
		}
		double dt1 = (a.TimeToHitVerticalWall() / 10);
		pq.insert(new Event((dt1 + timer), null, a));
		// System.out.println("Time1:"+a.TimeToHitVerticalWall());
		double dt2 = a.TimeToHitHorizantalWall() / 10;
		pq.insert(new Event((dt2 + timer), a, null));
		// System.out.println("Time2:"+a.TimeToHitHorizantalWall());
	}
	public void init() {
		N = 10;
		timer = 0.0;
		particles = new Particle[N];
		pq = new MinPq<Event>();
		System.out.println("Start");
		for(int i = 0; i < N; i++) particles[i] = new Particle();
		setBackground(Color.white); 
	}
	public void start() {}
	public void paint(Graphics g) {
		for(int i = 0; i < N; i++) predict(particles[i]);
		System.out.println("Simulation Starting");
		if(pq.isEmpty()) repaint();
			Event event = pq.DelMin();
			System.out.println("Event going to occur in :"+event.time/10);
			while(!event.isValid() && !pq.isEmpty()) event = pq.DelMin();
			if(event == null) repaint();
			Particle a = event.a;
			Particle b = event.b;
			double t = event.time;
			while(timer < t) {
				System.out.println("Painting");
				g.clearRect(0, 0, 500, 500);
				g.setColor(Color.black);
				for(int i = 0; i < N; i++) {
					particles[i].move();
					g.fillOval((int)(particles[i].rx*500), (int)(particles[i].ry*500), 5, 5);
					System.out.println("Position of Particle :"+particles[i].rx*500 + ":"+ particles[i].ry*500);
				}
				timer = timer + 0.1;
				System.out.println("Time is :"+timer);
				try { Thread.sleep(50); }
				catch(Exception e) { e.printStackTrace(); }
			}
			System.out.println("Calculating Next Event");
			if(a != null && b != null){
				a.bounceOff(b);
				System.out.println("Particle Particle");
			}
			if(a == null && b != null){
				b.bounceOfVerticalWall();
				System.out.println("Particle vertical Wall");
			}
			if(a != null && b == null) {
				a.bounceOfHorizantalWall();
				System.out.println("Particle Horizantal Wall");
			}
		repaint();
	}
	public void stop() {}
	public void destroy() {}
}