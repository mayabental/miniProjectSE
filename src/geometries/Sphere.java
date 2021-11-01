package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * sphere class represents two-dimensional shaper in 3D Cartesian coordinate
 * system
 *
 * @author yael and maya
 */
public class Sphere extends Geometry {
    Point3D center;
    double radius;

    /**
     * sphere constructor based on point3d and radius
     *
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }

    /**
     * getCenter
     *
     * @return center
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * getRadius
     *
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * toString
     *
     * @return center and radios as string
     */
    @Override
    public String toString() {
        return "Sphere [center=" + center.toString() + ", radius=" + radius + "]";
    }

    /**
     * getNormal
     *
     * @param p
     * @return Normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        if (p.equals(center)) {//checks if the center is equal to p
            throw new IllegalArgumentException("point cannot be equals to the center of the sphere");
        }
        Vector V_P = p.subtract(center);
        return V_P.normalize();
    }

    /**
     * findGeoIntersections
     *
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Point3D o = center;
        Vector V = ray.getDir();


        if (o.equals(p0))//when p0 is equal to center
        {
            return List.of(new GeoPoint(this, p0.add(V.scale(radius))));
        }
        Vector U = o.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= radius) {// ray does not hit sphere
            return null;
        }

        double th = Math.sqrt(radius * radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;
        List<GeoPoint> result = new LinkedList<>();
        //one Intersection point
        if (t1 > 0) {
            result.add(new GeoPoint(this, ray.getP0().add(ray.getDir().scale(t1))));
        }

        //one Intersection points
        if (t2 > 0) {
            result.add(new GeoPoint(this, ray.getP0().add(ray.getDir().scale(t2))));
        }

        if (result.size() == 0)
            return null;
        return result;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
        Point3D rayQ0 = ray.getP0();
        Vector rayDir = ray.getDir();
        if(rayQ0.equals(center))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        Vector q0ToCenter = center.subtract(rayQ0);
        double tm = alignZero(rayDir.dotProduct(q0ToCenter)); ;
        double d = alignZero(Math.sqrt(q0ToCenter.lengthSquared()-tm*tm));
        if(d>=radius)
            return null;
        double th = alignZero(Math.sqrt(radius*radius - d*d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        //A list of conditions that verify that the points are indeed intersection points and are within the desired range
        if((t1 <= 0 )&& ( t2 <= 0 ))
            return null;
        if((t1 <= 0)&&( t2 > 0 && (alignZero(t2 - maxDistance) <= 0)))
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        if((t1 > 0 && (alignZero(t1 - maxDistance) <= 0))&&( t2 <= 0 ))
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        if((t1 > 0 && (alignZero(t1 - maxDistance) <= 0))&&( t2 > 0 && (alignZero(t2 - maxDistance) <= 0)))
            return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));
        return null;
    }
}

    /**
     * findGeoIntersections
     * @param ray
     * @param maxDistance
     * @return
     */
//    @Override
//    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
//        Point3D p0 = ray.getP0();
//        Point3D o = center;
//        Vector V = ray.getDir();
//
//        if (o.equals(p0))//when p0 is equal to center
//        {
//            if(alignZero(o.distance(ray.getP0())-maxDistance)<=0)
//                return List.of(new GeoPoint(this,p0.add(V.scale(radius))));
//            return null;
//        }
//        Vector U = o.subtract(p0);
//        double tm = V.dotProduct(U);
//        double d = Math.sqrt(U.lengthSquared() - tm * tm);
//        if (d >= radius) {// ray does not hit sphere
//            return null;
//        }
//
//        double th = Math.sqrt(radius * radius - d * d);
//        double t1 = tm - th;
//        double t2 = tm + th;
//        List<GeoPoint> result=new LinkedList<>();
//        //two Intersection points
////        if (t1 > 0 && t2 > 0){
////            if(alignZero(t1-maxDistance)<=0)
////                 result.add(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(t1))));
////            if(alignZero(t2-maxDistance)<=0)
////                result.add(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(t2))));
////            if(result.size()==0)
////                return null;
////            return result;
////        }
//
//        //one Intersection point
//        if (t1 > 0 && alignZero(t1-maxDistance)<=0) {
//            result.add(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(t1))));
//        }
//
//        //one Intersection points
//        if (t2 > 0 && alignZero(t2-maxDistance)<=0) {
//            result.add(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(t2))));
//        }
//
//        if(result.size()==0)
//            return null;
//        return result;    }
//}
