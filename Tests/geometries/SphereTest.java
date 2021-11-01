package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * testing sphere
 *
 *  @author yael and maya
 */
public class SphereTest {

    /**
     * Test method for Sphere getNormal(Point3D point)
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks if getNormal function works correctly
        Vector answer=new Vector (1,0,0);
        Sphere sphere= new Sphere(new Point3D(0,0,0),1);
        assertEquals(sphere.getNormal(new Point3D(1,0,0)),answer);
    }

    @Test
    void testFindGeoIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0),1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(
                sphere.findGeoIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),"Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        GeoPoint p1 = new GeoPoint(sphere,new Point3D(0.0651530771650466, 0.355051025721682, 0));
        GeoPoint p2 = new GeoPoint(sphere,new Point3D(1.53484692283495, 0.844948974278318, 0));
        List<GeoPoint> result1 = sphere.findGeoIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals( 2, result1.size(),"Wrong number of points");
        if (result1.get(0).point.getX() > result1.get(1).point.getX())
            result1 = List.of(result1.get(1), result1.get(0));
        assertEquals( List.of(p1, p2), result1,"Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        List<GeoPoint> result2 = sphere.findGeoIntersections(new Ray(new Point3D(1,0.5,0),new Vector(0,1,0)));
        assertEquals( 1, result2.size(),"Wrong number of points");
        assertEquals(new Point3D(1,1,0),result2.get(0).point,"when Ray starts inside the sphere wrong Intersection point ");

        // TC04: Ray starts after the sphere (0 points)
        List<GeoPoint> result3 = sphere.findGeoIntersections(new Ray(new Point3D(1,2,0),new Vector(0,1,0)));
        assertNull(result3,"when Ray starts outside the sphere wrong Intersection point ");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        List<GeoPoint> result4 = sphere.findGeoIntersections(new Ray(new Point3D(0,0,0),new Vector(1,1,0)));
        assertEquals( 1, result4.size(),"Wrong number of points");
        assertEquals(result4.get(0).point,new Point3D(1,1,0),"when Ray starts on edge of the sphere and in, wrong Intersection point ");

        // TC12: Ray starts at sphere and goes outside (0 points)
        List<GeoPoint> result5 = sphere.findGeoIntersections(new Ray(new Point3D(1,1,0),new Vector(-1,1,0)));
        assertNull(result5,"when Ray starts on edge of the sphere and out, wrong Intersection point ");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        List<GeoPoint> result6 = sphere.findGeoIntersections(new Ray(new Point3D(-1,0,0),new Vector(1,0,0)));
        assertEquals( result6.size(),2,"Wrong number of points");
        if (result6.get(0).point.getX() > result6.get(1).point.getX())
            result6 = List.of(result6.get(1), result6.get(0));
        assertEquals( List.of(new GeoPoint(sphere,new Point3D(0,0,0)),new GeoPoint(sphere,new Point3D(2,0,0))), result6,"when Ray starts before and through the center of sphere, " +
                "wrong Intersection point ");

        // TC14: Ray starts at sphere and goes inside (1 points)
        List<GeoPoint> result7 = sphere.findGeoIntersections(new Ray(new Point3D(0,0,0),new Vector(1,0,0)));
        assertEquals( 1, result7.size(),"Wrong number of points");
        assertEquals(result7.get(0).point,new Point3D(2,0,0),"when Ray starts on edge of sphere and goes through the center of sphere, wrong Intersection point ");

        // TC15: Ray starts inside (1 points)
        List<GeoPoint> result8 = sphere.findGeoIntersections(new Ray(new Point3D(0.5,0,0),new Vector(1,0,0)));
        assertEquals( 1, result8.size(),"Wrong number of points");
        assertEquals(result8.get(0).point,new Point3D(2,0,0),"when Ray starts in sphere and goes through the center of sphere, wrong Intersection point ");

        // TC16: Ray starts at the center (1 points)
        List<GeoPoint> result9 = sphere.findGeoIntersections(new Ray(new Point3D(1,0,0),new Vector(1,0,0)));
        assertEquals( 1, result9.size(),"Wrong number of points");
        assertEquals(result9.get(0).point,new Point3D(2,0,0),"when Ray starts at the center of sphere, wrong Intersection point ");

        // TC17: Ray starts at sphere and goes outside (0 points)
        List<GeoPoint> result10 = sphere.findGeoIntersections(new Ray(new Point3D(0,0,0),new Vector(-1,0,0)));
        assertNull(result10,"when Ray starts on edge of the sphere and out, wrong Intersection point ");

        // TC18: Ray starts after sphere (0 points)
        List<GeoPoint> result11 = sphere.findGeoIntersections(new Ray(new Point3D(-1,0,0),new Vector(-1,0,0)));
        assertNull(result11,"when Ray starts on after the sphere and out, wrong Intersection point ");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        List<GeoPoint> result12 = sphere.findGeoIntersections(new Ray(new Point3D(0,1,0),new Vector(1,0,0)));
        assertNull(result12,"when Ray starts before and tangent to sphere, wrong Intersection point ");

        // TC20: Ray starts at the tangent point
        List<GeoPoint> result13 = sphere.findGeoIntersections(new Ray(new Point3D(1,1,0),new Vector(1,0,0)));
        assertNull(result13,"when Ray starts at the tangent point on sphere, wrong Intersection point ");

        // TC21: Ray starts after the tangent point
        List<GeoPoint> result14 = sphere.findGeoIntersections(new Ray(new Point3D(2,1,0),new Vector(1,0,0)));
        assertNull(result14,"when Ray starts after the tangent point on sphere, wrong Intersection point ");

        // **** Group: Special cases
        // TC19: the vector between the center and P0 of the Ray is orthogonal to the ray
        List<GeoPoint> result15 = sphere.findGeoIntersections(new Ray(new Point3D(-1,0,0),new Vector(0,1,0)));
        assertNull(result15,"when the vector between the center and P0 of the Ray is orthogonal to the ray , wrong Intersection point ");

    }
}