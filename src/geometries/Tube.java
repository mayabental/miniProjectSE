package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * tube class represents two-dimensional tube in 3D Cartesian coordinate
 * system
 *
 * @author yael and maya
 */
public class Tube extends Geometry {
    Ray axisRay;
    double radius;

    /**
     * constructor of tube based on ray and double
     *
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius) {
        super();
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getAxisRay
     *
     * @return axisRay
     */
    public Ray getAxisRay() {
        return axisRay;
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
     * @return axisRay and radius as string
     */
    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay.toString() + ", radius=" + radius + "]";
    }

    /**
     * getNormal override function
     *
     * @param p Point 3D point
     * @return normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        Point3D P0 = axisRay.getP0();
        Vector v = axisRay.getDir();
        Vector PO_P = p.subtract(P0);
        double t = alignZero(PO_P.dotProduct(v));

        if (isZero(t))//p_p0 is orthogonal to v
        {
            return PO_P.normalize();
        }
        Point3D O = P0.add(v.scale(t));
        Vector O_P = p.subtract(O);
        return O_P.normalize();
    }

    /**
     * findGeoIntersections
     * @param ray ray intersecting with the tube
     * @return intersection points
     */
  @Override
   public List<GeoPoint> findGeoIntersections(Ray ray) {
        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this,ray.getPoint(radius)));

            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
        else // t2 is behind the head
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0 && alignZero(radius - maxDistance) <= 0) // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this, ray.getPoint(radius)));

            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            if (alignZero(t - maxDistance) > 0)
                return null;
            return t == 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                if (alignZero(t - maxDistance) > 0)
                    return null;
                return t == 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1
        List<GeoPoint> result = new LinkedList<>();
        if (alignZero(t1 - maxDistance) <= 0)
            result.add(new GeoPoint(this, ray.getPoint(t1)));

        double t2 = alignZero(tm - th);
        // if both t1 and t2 are positive
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0)
            result.add(new GeoPoint(this, ray.getPoint(t2)));
        if(result.size()==0)
            return null;
        return result;        }
}
