package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testing triangle
 *
 * @author yael and maya
 */
public class TriangleTest {
    /**
     * Test method for Triangle getNormal(Point3D point)
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks if getNormal function works correctly
        Triangle t = new Triangle(new Point3D(0, 0, 0),
                new Point3D(1, 0, 0),new Point3D(0, 8, 0));
        Vector v1 = new Vector(0,0,1);
        Vector v2 = new Vector(0,0,-1);
        Vector answer= t.getNormal(new Point3D(0,1,0));
        assertTrue(answer.equals(v1)||answer.equals(v2),"get normal for triangle is incorrect");
    }



    @Test
    void testFindGeoIntersections() {
        Triangle t=new Triangle(new Point3D(1.0,3.0,5.0),new Point3D(5.0,3.0,1.0),new Point3D(0.0,3.0,1.0));

        // ============ Equivalence Partitions Tests ==============

        //TC01- ray with triangle
        Ray r=new Ray(new Point3D(1.0,-5.0,4.0),new Vector(0.0,3.0,0.0));
        List<GeoPoint> l=t.findGeoIntersections(r);
        assertEquals(new Point3D(1.0,3.0,4.0),l.get(0).point,"when ray intersects with triangle the point is incorrect");

        //TC02- ray intersects with plane but outside the triangle against edge
        r=new Ray(new Point3D(1.0,-5.0,4.0),new Vector(3.0,0.0,-1.0));
        l=t.findGeoIntersections(r);
        assertNull(l, "when ray intersects with triangle but outside the triangle against edge " +
                "the point is incorrect");

        //TC03- ray intersects with plane but outside the triangle against vertex
        r=new Ray(new Point3D(1.0,-5.0,4.0),new Vector(1.0,3.0,6.0));
        l=t.findGeoIntersections(r);
        assertNull(l, "when ray intersects with triangle but outside the triangle against vertex " +
                "the point is incorrect");

        // =============== Boundary Values Tests ==================

        //TC01- the ray begins before the plane on the edge of triangle
        r=new Ray(new Point3D(4.0,2.0,1.0),new Vector(0.0,1.0,0.0));
        l=t.findGeoIntersections(r);
        assertNull(l, "when the ray begins before the plane on the edge of triangle the point is incorrect");

        //TC02- the ray begins before the plane on vertex
        r=new Ray(new Point3D(1.0,2.0,5.0),new Vector(0.0,1.0,0.0));
        l=t.findGeoIntersections(r);
        assertNull(l, "when the ray begins before the plane on vertex the point is incorrect");

        //TC03- the ray begins before the plane on edge's continuation
        r=new Ray(new Point3D(8.0,2.0,1.0),new Vector(0.0,1.0,0.0));
        l=t.findGeoIntersections(r);
        assertNull(l, "when the ray begins before the plane on edge's continuation the point is incorrect");
    }
}