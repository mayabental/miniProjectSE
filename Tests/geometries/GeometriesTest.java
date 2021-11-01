package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * GeometriesTest
 *
 *  @author yael and maya
 */
class GeometriesTest {

    /**
     * GeometriesTest checks if all the Intersection points with all the different geometries are correct
     */
    @Test
    void testFindGeoIntersections() {
        Geometries geometries = new Geometries();

        // =============== Boundary Values Tests ==================
        //TC01: empty geometries list
        assertNull(geometries.findGeoIntersections(new Ray(new Point3D(0.0, 1.0, 0.0), new Vector(1.0, 0.0, 5.0))),"when empty geometries list the" +
                " amount of points are incorrect");

        geometries.add(new Plane( new Point3D(1.0, 1.0, 0.0),new Vector(0.0, 0.0, 1.0)));
        geometries.add(new Triangle(new Point3D(1.0, 0.0, 0.0), new Point3D(0.0, 1.0, 0.0), new Point3D(0.0, 0.0, 1.0)));
        geometries.add(new Sphere( new Point3D(1.0, 0.0, 0.0),1.0));

        //TC02: each geometry doesn't have intersection points
        assertNull(geometries.findGeoIntersections(new Ray(new Point3D(0.0, 0.0, 2.0), new Vector(0.0, -1.0, 0.0))),"when each geometry doesn't have intersection" +
                " points the amount of points are incorrect");

        //TC03: just one geometry has intersections point
        assertEquals(1, geometries.findGeoIntersections(new Ray(new Point3D(0.0, 5.0, -1.0), new Vector(0.0, 0.0, 1.0))).size(),"when just one geometry has intersections" +
                " point the amount of points are incorrect");

        //TC04: all of the geometries have intersection points
        assertEquals(4, geometries.findGeoIntersections(new Ray(new Point3D(0.05,0.05,5), new Vector(0, 0,-1))).size(),"when all of the geometries have intersection points," +
                "the amount of points are incorrect");


        // ============ Equivalence Partitions Tests ==============
        //TC11: part of the geometries have intersection points and part don't
        assertEquals(2, geometries.findGeoIntersections(new Ray(new Point3D(1.0, 0.0, -1.0), new Vector(0.0, 0.0, 1.0))).size(),"when part of the geometries has intersection points" +
                "the amount of points are incorrect");
    }
}