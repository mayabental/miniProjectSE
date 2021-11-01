package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * plane class represents two-dimensional plane in 3D Cartesian coordinate
 * system
 *
 * @author yael and maya
 */
public class Plane extends Geometry
{
    final Point3D q0;
    final Vector normal;

    /**
     *  plane constructor based on point3d and vector.
     * @param q0
     * @param normal
     */
    public Plane(Point3D q0, Vector normal)
    {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * plane constructor based on 3 point3d
     * @param a
     * @param b
     * @param c
     */
    public Plane(Point3D a, Point3D b, Point3D c)
    {
        q0 = a;
        //Vector AB
        Vector v1 = b.subtract(a);
        //Vector AC
        Vector v2 = c.subtract(a);
        //Normal to AB and AC
        Vector n = v1.crossProduct(v2);
        normal = n.normalize();
    }

    /**
     * getQ0
     * @return q0
     */
    public Point3D getQ0() {
        return q0;
    }

    /**
     * getNormal
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }

    /* getNormal implementation of Geometry interface
     * @param point dummy point not use for flat geometries
     *  should be assigned null value
     * @return normal to the plane
     */
    @Override
    public Vector getNormal(Point3D p)
    {
        return normal;
    }

    /**
     * toString
     * @return q0 and normal as string
     */
    @Override
    public String toString() {
        return "Plane [q0=" + q0.toString() + ", normal=" + normal.toString() + "]";
    }

    /**
     * findGeoIntersections
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (q0 == ray.getP0())
            return null;

        Vector p0Q = q0.subtract(ray.getP0());

        double nv = alignZero(normal.dotProduct(ray.getDir()));
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;
        double t = alignZero(normal.dotProduct(p0Q) / nv);

        if (t <= 0) {
            return null;
        }
        return List.of(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(t))));    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (q0 == ray.getP0())
            return null;

        Vector p0Q = q0.subtract(ray.getP0());

        double nv = alignZero(normal.dotProduct(ray.getDir()));
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;
        double t = alignZero(normal.dotProduct(p0Q) / nv);

        if (t <= 0 ) {
            return null;
        }
        if(alignZero(t-maxDistance) <= 0) {
            return List.of(new GeoPoint(this, ray.getP0().add(ray.getDir().scale(t))));
        }
        return null;
    }

}
