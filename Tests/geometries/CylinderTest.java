package geometries;

import org.junit.jupiter.api.Test;
/**
 * Unit tests for Geometries.Cylinder class
 *
 *  @author yael and maya
 */

public class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() { return ; }

//       { Cylinder c = new Cylinder(new Ray(new Point3D(0.0,0.0,1.0), new Vector(0.0,0.0,2.0)),4.0, 8.0);
//        // ============ Equivalence Partitions Tests ==============
//
//
//        //part one: on the side of the cylinder
//        assertEquals(c.getNormal(new Point3D(4.0,0.0,0.0)),(new Vector(0.8,0.0,0.6)));
//        assertEquals(c.getNormal(new Point3D(1.0,-4.0,2.0)),(new Vector(0.19611613513818404, -0.7844645405527362, -0.5883484054145521)));
//
//        //part two: base of the cylinder
//        assertEquals(c.getNormal(new Point3D(1.0,2.0,-3.0)),(new Vector(0.08192319205190406, 0.1638463841038081, 0.9830783046228486)));//bottom base
//
//        //part three: bottom base of the cylinder
//        assertEquals(c.getNormal(new Point3D(0.0,3.0,5.0)),(new Vector(0.0, 0.0, 2.0)));//top base
//        // =============== Boundary Values Tests ==================
//        // the boundary values are at the intersections of the bases and the side of the cylinder
//
//        assertEquals(c.getNormal(new Point3D(4.0,0.0,5.0)),(new Vector(0.0, 0.0, 2.0)));
//        assertEquals(c.getNormal(new Point3D(0.0,-4.0,-3.0)),(new Vector(0.0, -0.31622776601683794, 0.9486832980505)));
//    }
}