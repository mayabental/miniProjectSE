package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class Basic Ray Tracer is an extension of RayTracerBase
 *
 *  @author yael and maya
 */
public class BasicRayTracer extends RayTracerBase {
    //creates the final parameters

    //max times to do recursive function
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    //min effect that we care to calculate
    private static final double MIN_CALC_COLOR_K = 0.0001;
    //starting point for the materials
    private static final double INITIAL_K=1;


    /**
     * constructor
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * unshaded
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;//no shade
        double lightDistance = light.getDistance(geopoint.point);//calculates the Distance between light and point
        for (GeoPoint gp : intersections) {
            //checks if the point is behind light
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().getKT() == 0)//no transparency
                return false;//the point should be shaded
        }
        return true;//no shade
    }

    /**
     * traceRay
     * @param ray
     * @return color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersection = _scene.geometries.findGeoIntersections(ray);
        //if there are not intersection return The background color of the scene
        if (intersection == null) {
            return _scene._backgroundColor;
        }
        //The point closest to the beginning of the foundation will be found
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersection);
        //the point color
        return calcColor(closestPoint, ray);
    }

    /**
     * calcColor(GeoPoint geopoint, Ray ray)
     * @param geopoint
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }

    /**
     * calcColor(GeoPoint intersection, Ray ray, int level, double k)
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return color
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();//color is the objects color
        color = color.add(calcLocalEffects(intersection, ray,k));//adding the local effects
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));//adding the global effects
    }

    /**
     * calcGlobalEffects recursive
     * @param geoPoint
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.getMaterial();
        double kkr = k * material.getKR();
        //Conditions for stopping a recursive function, by The contribution of light to a point.
        //if the effect of light on the point color is minimal then stop with the recursion
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(geoPoint.point, v, n), level, material.getKR(), kkr);
        double kkt = k * material.getKT();
        //Conditions for stopping a recursive function, by The contribution of light to a point.
        //if the effect of light on the point color is minimal then stop with the recursion
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geoPoint.point, v, n), level, material.getKT(), kkt));
        return color;
    }

    /**
     * calcGlobalEffect
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return color
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) {
            return _scene._backgroundColor.scale(kx);
        }
        Color temp = calcColor(gp, ray, level - 1, kkx);
        return temp.scale(kx);
    }

    /**
     * calcLocalEffects
     * @param intersection
     * @param ray
     * @return color
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, intersection);
                //if the effect of light on the point color is minimal then stop with the recursion
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(material.getKd(), l, n, lightIntensity),
                            calcSpecular(material.getKs(), l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * calcSpecular the surrounding lights create a specular effect that looks shiny
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return color
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double p = nShininess;
        double ln=l.dotProduct(n);
        Vector R = l.add(n.scale(-2 * ln)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        // [rs,gs,bs](-V.R)^p
        return lightIntensity.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * calcDiffusive creates a treeD allusion
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return color
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln=l.dotProduct(n);
        if(ln<0){
            ln=-ln;
        }
        return lightIntensity.scale(ln * kd);
    }

    /**
     *  constructReflectedRay
     * @param gp
     * @param v
     * @param n
     * @return ray
     */
    private Ray constructReflectedRay(Point3D gp, Vector v, Vector n){
        double vn = alignZero(v.dotProduct(n));

        if (vn==0) { //Perpendicular vectors
            return new Ray(gp,v);
        }
        Vector r = v.subtract(n.scale( 2 * vn)).normalize();
        return new Ray(gp, r, n);
    }

    /**
     * constructRefractedRay t
     * @param gp
     * @param v
     * @param n
     * @return ray
     */
    private Ray constructRefractedRay(Point3D gp, Vector v, Vector n){
        return new Ray(gp,v,n );//creates new ray that is scaled by delta- it is moved a little from surface of geometry
    }

    /**
     * findClosestIntersection
     * @param ray
     * @return closestIntersection
     */
    private GeoPoint findClosestIntersection(Ray ray){
        if (ray == null)
            return null;

        List<GeoPoint> intersectionList=_scene.geometries.findGeoIntersections(ray);

        if(intersectionList==null)
            return null;

        //find closest intersection geo point
        GeoPoint closestIntersection= ray.findClosestGeoPoint(intersectionList);
        return closestIntersection;
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return ktr-
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geopoint.point);
        var intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) -lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().getKT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;//no transparency
            }
        }
        return ktr;
    }



}
