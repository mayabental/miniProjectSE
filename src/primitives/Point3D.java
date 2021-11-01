package primitives;

/**
 * basic geometric object for #d point
 *
 *  @author yael and maya
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    public static Point3D PointZero = new Point3D(0, 0, 0);

    /**
     * getX
     * @return x
     */
    public double getX() {
        return _x.coord;
    }

    /**
     * getY
     * @return y
     */
    public double getY() {
        return _y.coord;
    }

    /**
     * getZ
     * @return z
     */
    public double getZ() {
        return _z.coord;
    }

    /**
     * constructor
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * toString
     * @return x y z as string
     */
    @Override
    public String toString() {
        return "Point3D [x=" + _x.toString() + ", y=" + _y.toString() + ", z=" + _z.toString() + "]";
    }

    /**
     * equals
     * @param o
     * @return if o is equal to Point3D
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    /**
     * distanceSquared
     * @param point3D, x1, y1, z1, x2, y2, z2
     * @return the distance Squared
     */
    public double distanceSquared(Point3D point3D) {
        double y1 = _y.coord;
        double x1 = _x.coord;
        double z1 = _z.coord;
        double x2 = point3D._x.coord;
        double y2 = point3D._y.coord;
        double z2 = point3D._z.coord;
        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
    }

    /**
     * distance
     * @param point3D
     * @return the distance
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));
    }

    /**
     * add: adds the two vectors and creates a new vector with the added values
     * @param vector
     * @return the new added vector
     */
    public Point3D add(Vector vector) {
        return new Point3D(
                _x.coord + vector._head._x.coord,
                _y.coord + vector._head._y.coord,
                _z.coord + vector._head._z.coord);
    }

    /**
     * subtract: subtracts between two points and creates a new point with the subtracted values
     * @param p
     * @return subtracted new point
     */
    public Vector subtract(Point3D p)
    {
        Point3D q = new Point3D(
                _x.coord - p._x.coord,
                _y.coord - p._y.coord,
                _z.coord - p._z.coord);
        Vector v= new Vector(q);
        return v;
    }
}
