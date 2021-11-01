package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries contains a list of Intersectables
 *
 * @author yael and maya
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _intersectables;

    /**
     * constructor builds ArrayList<Intersectable>
     */
    public Geometries(){
        this._intersectables = new ArrayList<Intersectable>();
    }

    /**
     * constructor adds the geometries
     * @param geometries
     */
    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    /**
     * add function
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(_intersectables, geometries);
    }

    /**
     * findGeoIntersections
     * @param ray
     * @return Geo Intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result=null;
        for(Intersectable geo:_intersectables){
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray);
            if(geoPoints!=null){
                //first Intersection point found
                if(result==null){
                    result=new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;    }

    /**
     * findGeoIntersections
     * @param ray
     * @param maxDistance
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> result=null;
        for(Intersectable geo:_intersectables){
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray,maxDistance);
            if(geoPoints!=null){
                //first Intersection point found
                if(result==null){
                    result=new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;     }
}
