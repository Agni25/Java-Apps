public class Event implements Comparable<Event> {
	public final double time; // Time of collision.
	public final Particle a, b; /* Particles involved in Collision. (null, null) to redraw, (a, null) for particle VerticalWall,
					(null, b) for particle HorizantalWall and (a, b) for particle particle collision. */
	private int countA, countB;
	public Event(double t, Particle a, Particle b) {
		this.a = a;
		this.b = b;
		time = t;
		if(a != null) countA = a.count;
		else countA = -1;
		if(b != null) countB = b.count;
		else countB = -1;
	}
	public int compareTo(Event that) {
		if(this.time < that.time) return -1;
		else if(this.time > that.time) return +1;
		return 0;
	}
	public boolean isValid() {
		if(a != null && countA != a.count) return false;
		if(b != null && countB != b.count) return false;
		return true;
	}
}