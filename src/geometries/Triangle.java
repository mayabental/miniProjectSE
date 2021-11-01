package geometries;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * triangle class represents two-dimensional triangle in 3D Cartesian coordinate
 * system, that is inherited from polygon
 *
 * @author yael and maya
 */
public class Triangle extends Polygon
{
    /**
     * constructor for triangle based on 3 points
     * @param a
     * @param b
     * @param c
     */
    public Triangle(Point3D a, Point3D b, Point3D c)
    {
        super(a,b,c);
    }

    /**
     * getNormal
     * @param point
     * @return normal
     */
    public Vector getNormal(Point3D point){return super.getNormal(point);}

    /**
     * toString
     * @return vertices and plane as string
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    /**
     * findGeoIntersections
     * @param ray
     * @return List of GeoPoint that have Intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        var v=plane.findGeoIntersections(ray);
        if(v==null)//no Intersection points with all of the plane
            return null;

        Vector v1=(vertices.get(0)).subtract(ray.getP0());
        Vector v2=(vertices.get(1)).subtract(ray.getP0());
        Vector v3=(vertices.get(2)).subtract(ray.getP0());

        Vector N1=(v1.crossProduct(v2)).normalize();
        Vector N2=(v2.crossProduct(v3)).normalize();
        Vector N3=(v3.crossProduct(v1)).normalize();

        Vector vector=ray.getDir();
        double a=vector.dotProduct(N1);
        double b=vector.dotProduct(N2);
        double c=vector.dotProduct(N3);
        //checks if all vector have the same sign
        if(alignZero(a*b)>0 && alignZero(b*c)>0 && alignZero(c*a)>0){
            return List.of(new GeoPoint(this,v.get(0).point));
        }
        return null;
    }
}

