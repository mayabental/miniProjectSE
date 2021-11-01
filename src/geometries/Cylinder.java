package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class is a extension of tube with height
 *
 * @author yael and maya
 */

public class Cylinder extends Tube  {
    double height;

    /**
     *constructor calls super for tube constructor and also saves height
     * @param axisRay  the center of the cylinder
     * @param radius
     * @param height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * getHeight
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * toString
     * @return all fields of Cylinder
     */
    @Override
    public String toString() {
        return "Cylinder [height=" + height + super.toString() + "]";
    }


     /* get normal to received point
     * @param point3D
     * @return the normal to the point received
     */
    @Override
    public Vector getNormal(Point3D point3D) {

        Point3D b_center1 = axisRay.getP0();
        Vector v = axisRay.getDir();

        // base canter1 point on bottom base
        if(point3D.equals(b_center1))
            return v.scale(-1).normalize();

        Vector P0_P= point3D.subtract(b_center1);
        double t = alignZero(P0_P.dotProduct(v));

        if(isZero(t)){
            return v.scale(-1).normalize();
        }

        // base canter2 point on top base
        Point3D b_center2= b_center1.add(v.scale(height));
        if(point3D.equals(b_center2))//p is a center of top base
            return v.normalize();

        P0_P= point3D.subtract(b_center2);
        t= alignZero(P0_P.dotProduct(v));
        if(isZero(t)){
            return v.normalized();
        }

        //if p is on shell of cylinder
        return super.getNormal(point3D);
    }
}
