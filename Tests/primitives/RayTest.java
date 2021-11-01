package primitives;

import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * testing Ray class
 *
 *  @author yael and maya
 */
class RayTest {
    /**
     * testing Find Closest Point
     */
    @Test
    void testFindClosestPoint() {
        Point3D p1 = new Point3D(-1, 1, 0);
        Point3D p2 = new Point3D(-2, 2, 0);
        Point3D p3 = new Point3D(-3, 3, 0);
        Ray ray = new Ray(Point3D.PointZero, new Vector(-1, 1, 0));


        // ============ Equivalence Partitions Tests ==============
        // TC01: the point in the middle of the list is the closest to the ray
        List<Point3D> list1 = List.of(p2, p1, p3);
        assertEquals(p1, ray.findClosestPoint(list1), "");

        // =============== Boundary Values Tests ==================
        // TC01: empty list
        List<Point3D> list2 =null;
        assertNull(ray.findClosestPoint(list2), "");

        // TC02: the first element is closest to ray
        List<Point3D> list3 =List.of(p1,p2,p3);
        assertEquals(p1,ray.findClosestPoint(list3), "");

        // TC03: the last element is closest to ray
        List<Point3D> list4 =List.of(p2,p3,p1);
        assertEquals(p1,ray.findClosestPoint(list4), "");

    }

    /**
     * test for FindClosestGeoPoint
     */
    @Test
    void testFindClosestGeoPoint() {
        Plane plane= new Plane(new Point3D(1,0,0),new Vector(0,0,1));
        GeoPoint p1 = new GeoPoint(plane,new Point3D(-1, 1, 0));
        GeoPoint p2 = new GeoPoint(plane,new Point3D(-2, 2, 0));
        GeoPoint p3 = new GeoPoint(plane,new Point3D(-3, 3, 0));
        Ray ray = new Ray(Point3D.PointZero, new Vector(-1, 1, 0));


        // ============ Equivalence Partitions Tests ==============
        // TC01: the point in the middle of the list is the closest to the ray
        List<GeoPoint> list1 = List.of(p2, p1, p3);
        assertEquals(p1, ray.findClosestGeoPoint(list1), "");

        // =============== Boundary Values Tests ==================
        // TC01: empty list
        List<GeoPoint> list2 =null;
        assertNull(ray.findClosestGeoPoint(list2), "");

        // TC02: the first element is closest to ray
        List<GeoPoint> list3 =List.of(p1,p2,p3);
        assertEquals(p1,ray.findClosestGeoPoint(list3), "");

        // TC03: the last element is closest to ray
        List<GeoPoint> list4 =List.of(p2,p3,p1);
        assertEquals(p1,ray.findClosestGeoPoint(list4), "");

    }
}