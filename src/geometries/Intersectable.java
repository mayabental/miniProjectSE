package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable class consists of a list of intersection points
 *
 * @author yael and maya
 */
public interface Intersectable  {
    /**
     * findGeoIntersections
     * @param ray
     * @return List of gep points
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    /**
     * class GeoPoint
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * equals
         * @param o
         * @return true if geo point is equal to o
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }



}
