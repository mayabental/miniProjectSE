package primitives;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Class ray is the basic class representing a ray for Cartesian
 * coordinate system. given the value of the point3d and the direction. The class is based on Util controlling the accuracy.
 *
 * @author yael and maya
 *
 */
public class Ray
{
    private static final double DELTA = 0.1;
    Point3D p0;
    Vector dir;

    /**
     * getP0
     * @return p0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getDir
     * @return dir
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * ray constructor receiving a point3d value and vector value
     * @param p0
     * @param dir
     * @throws IllegalArgumentException when value of vector is incorrect
     */
    public Ray(Point3D p0, Vector dir) throws IllegalArgumentException
    {

        this.p0 = p0;
        double n = ((dir._head._x.coord * dir._head._x.coord) + (dir._head._y.coord*dir._head._y.coord) + (dir._head._z.coord*dir._head._z.coord)) ;
        double sqrt_n = Math.sqrt(n);
        if(sqrt_n == 1) //is normalized
        {
            this.dir = dir;
        }
        else //in need of normalization
        {
            Coordinate cx = new Coordinate(dir._head._x.coord/sqrt_n);
            Coordinate cy = new Coordinate(dir._head._y.coord/sqrt_n);
            Coordinate cz = new Coordinate(dir._head._z.coord/sqrt_n);

            Point3D p = new Point3D(cx.coord, cy.coord, cz.coord);

            this.dir = new Vector(p);
        }
    }

    public Ray(Point3D p0, Vector dir, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(dir) > 0 ? DELTA : - DELTA);
        Point3D point = p0.add(delta);
        this.p0 = point;
        this.dir=dir.normalized();
    }


    /**
     * toString
     * @return string of ray values
     */
    @Override
    public String toString() {
        return "Ray [p0=" + p0.toString() + ", dir=" + dir.toString() + "]";
    }

    /**
     * equals
     * @param obj
     * @return if both rays are equal true otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ray other = (Ray) obj;

        return p0.equals(other.p0) && dir.equals(other.dir);
    }

    /**
     * findClosestPoint
     * @param point3DList
     * @return
     */
    public Point3D findClosestPoint(List<Point3D> point3DList){
        if(point3DList==null)
            return null;
        Point3D closestPoint=point3DList.get(0);
        for(Point3D p: point3DList ){
            if(closestPoint.distance(p0)>p.distance(p0))
                closestPoint=p;
        }
        return closestPoint;
    }

    /**
     * findClosestGeoPoint
     * @param GeoPointList
     * @return Closest Geo Point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> GeoPointList) {

        if(GeoPointList==null)
            return null;
        GeoPoint closestPoint=GeoPointList.get(0);
        for(GeoPoint p: GeoPointList ){
            if(closestPoint.point.distance(p0)>p.point.distance(p0))
                closestPoint=p;
        }
        return closestPoint;
    }

    /**
     *  getPoint
     * @param t
     * @return p0 added with the direction of dir multiplied with t
     */
    public Point3D getPoint(double t) {
        return p0.add(dir.scale(t));
    }


}
