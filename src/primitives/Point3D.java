package primitives;

import java.util.Objects;

/**
 * basic geometric object for #d point
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    static Point3D PointZero = new Point3D(0, 0, 0);

    /**
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * @return reference to coordinate x
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x.coord, _y.coord, _z.coord);
    }

    public double distanceSquared(Point3D point3D) {
        double x1 = _x.coord;
        double y1 = _y.coord;
        double z1 = _z.coord;
        double x2 = point3D._x.coord;
        double y2 = point3D._y.coord;
        double z2 = point3D._z.coord;
        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
    }

    /**
     *
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));
    }

    public Point3D add(Vector vector) {
        return new Point3D(
                _x.coord + vector._head._x.coord,
                _y.coord + vector._head._y.coord,
                _z.coord + vector._head._z.coord);
    }
}
