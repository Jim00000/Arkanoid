package arkanoid;

public interface Collidable<T> {

	/**
	 * When this object collides with another object then this object would call
	 * this method
	 * 
	 * @param collider
	 *            the other objects that collides with this object
	 */
	public void onCollision(T collider);

	/**
	 * Check whether this object collides with assigned object
	 * 
	 * @param collider
	 */
	public boolean isCollided(T collider);

}
