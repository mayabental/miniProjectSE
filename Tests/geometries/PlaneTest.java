package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  Unit tests for Geometries.Plane class
 *
 *  @author yael and maya
 */
public class PlaneTest
{

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormalPoint3D()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: there is only one part to test- points that are on the plane
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(3, 3, 2);
        Point3D p3 = new Point3D(6, 6, 6);
        Plane pl = new Plane(p1, p2, p3);
        Vector n = pl.getNormal();
        Vector s = new Vector(5, -5, 0);
        s.normalize();
        assertEquals( s, n,"incorrect when there is only one part to test- points that are on the plane");

    }


    @Test
    void testFindGeoIntersections() {
        Plane p=new Plane(new Point3D(0.0,2.0,2.0), new Vector(1.0,0.0,0.0));

        // ============ Equivalence Partitions Tests ==============

        //TC01- the ray intersects the plane:
        Ray r1= new Ray(new Point3D(-1,1,1),new Vector(1,-3,-3));
        assertEquals((p.findGeoIntersections(r1)), List.of( new GeoPoint(p,new Point3D(0,-2,-2))),"Intersection with plane is incorrect");
        //TC02- the ray does not intersect the plane:
        Ray r2= new Ray(new Point3D(-1,1,1),new Vector(-1,1,1));
        assertEquals(p.findGeoIntersections(r2),null,"Intersection with plane when ray does not intersect the plane is incorrect");
        // =============== Boundary Values Tests ==================

        //ray is parallel to plane:
        //TC01-part 1 ray is not included in plane:
        Ray r3=new Ray(new Point3D(4.0,-1.0,1.0),new Vector(0.0,2.0,0.0));
        assertEquals(null,p.findGeoIntersections(r3),"Intersection with plane is incorrect when ray is " +
                "not included in plane");

        //TC02-part 2 ray is included in plane:
        Ray r4=new Ray(new Point3D(0.0,-1.0,1.0),new Vector(0.0,2.0,0.0));
        assertEquals(null,p.findGeoIntersections(r4),"Intersection with plane is incorrect when ray is included in plane");

        //ray is orthogonal to plane:
        //TC03 part 1- before
        Ray r5=new Ray(new Point3D(-1,1,1),new Vector(1,0,0));
        assertEquals(List.of(new GeoPoint(p,new Point3D(0,1,1))),p.findGeoIntersections(r5),"Intersection with plane is incorrect when ray is orthogonal to plane and before plane ");

        //TC04 part 2- in
        Ray r6=new Ray(new Point3D(0,1,1),new Vector(1,0,0));
        assertEquals(null,p.findGeoIntersections(r6),"Intersection with plane is incorrect when ray is orthogonal to plane and in plane ");

        //TC05 part 3- after:
        Ray r7=new Ray(new Point3D(1,1,1),new Vector(1,0,0));
        assertEquals(null,p.findGeoIntersections(r7),"Intersection with plane is incorrect when ray is orthogonal to plane and after plane ");

        //Ray is neither orthogonal nor parallel to the plane and begins in
        //TC06- the same point which appears as reference point in the plane:
        Ray r8=new Ray(new Point3D(0,1,1),new Vector(1,1,0));
        assertEquals(null,p.findGeoIntersections(r8),"Intersection with plane is incorrect when ray is neither orthogonal nor parallel to the plane and begins in " +
                "the same point which appears as reference point in the plane ");
    }

    /**
     * testTestFindGeoIntersections
     */
    @Test
    void testTestFindGeoIntersections() {
        Plane p=new Plane(new Point3D(0.0,2.0,2.0), new Vector(1.0,0.0,0.0));

        // ============ Equivalence Partitions Tests ==============

        //TC01- the ray intersects the plane:
        Ray r1= new Ray(new Point3D(-1,1,1),new Vector(1,-3,-3));
        assertEquals((p.findGeoIntersections(r1)), List.of( new GeoPoint(p,new Point3D(0,-2,-2))),"Intersection with plane is incorrect");
        //TC02- the ray does not intersect the plane:
        Ray r2= new Ray(new Point3D(-1,1,1),new Vector(-1,1,1));
        assertEquals(p.findGeoIntersections(r2),null,"Intersection with plane when ray does not intersect the plane is incorrect");
        // =============== Boundary Values Tests ==================

        //ray is parallel to plane:
        //TC01-part 1 ray is not included in plane:
        Ray r3=new Ray(new Point3D(4.0,-1.0,1.0),new Vector(0.0,2.0,0.0));
        assertEquals(null,p.findGeoIntersections(r3),"Intersection with plane is incorrect when ray is " +
                "not included in plane");

        //TC02-part 2 ray is included in plane:
        Ray r4=new Ray(new Point3D(0.0,-1.0,1.0),new Vector(0.0,2.0,0.0));
        assertEquals(null,p.findGeoIntersections(r4),"Intersection with plane is incorrect when ray is included in plane");

        //ray is orthogonal to plane:
        //TC03 part 1- before
        Ray r5=new Ray(new Point3D(-1,1,1),new Vector(1,0,0));
        assertEquals(List.of(new GeoPoint(p,new Point3D(0,1,1))),p.findGeoIntersections(r5),"Intersection with plane is incorrect when ray is orthogonal to plane and before plane ");

        //TC04 part 2- in
        Ray r6=new Ray(new Point3D(0,1,1),new Vector(1,0,0));
        assertEquals(null,p.findGeoIntersections(r6),"Intersection with plane is incorrect when ray is orthogonal to plane and in plane ");

        //TC05 part 3- after:
        Ray r7=new Ray(new Point3D(1,1,1),new Vector(1,0,0));
        assertEquals(null,p.findGeoIntersections(r7),"Intersection with plane is incorrect when ray is orthogonal to plane and after plane ");

        //Ray is neither orthogonal nor parallel to the plane and begins in
        //TC06- the same point which appears as reference point in the plane:
        Ray r8=new Ray(new Point3D(0,1,1),new Vector(1,1,0));
        assertEquals(null,p.findGeoIntersections(r8),"Intersection with plane is incorrect when ray is neither orthogonal nor parallel to the plane and begins in " +
                "the same point which appears as reference point in the plane ");
    }
}
