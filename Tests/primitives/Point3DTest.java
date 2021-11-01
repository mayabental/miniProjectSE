package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Unit tests for primitives.Point3D class
 *
 *  @author yael and maya
 */
class Point3DTest {

    Point3D p1= new Point3D(1.0d, 2.0d, 3.0d);
    Point3D p2= new Point3D(1.000000000000001, 2, 3);
    Point3D p3= new Point3D(3, 4, 5);
    Vector v1= new Vector(-1,0,1) ;

    /**
     * checks the function Equals()
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks equality between two points
        assertEquals(p1, p2,"equal doesn't work");
    }
    /**
     * checks the function DistanceSquared()
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Square distance from a point to point
       Point3D p3 = new Point3D(-1, -2, -3);
       double result = p1.distanceSquared(p3);
       assertEquals(56,result, 0.001,"distance doesn't work");
    }
    /**
     * checks the function Distance()
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: distance from a point to point
        Point3D p3 = new Point3D(-1, -2, -3);
        double result = p1.distance(p3);
        assertEquals(Math.sqrt(56),result, 0.001,"distance doesn't work");
    }
    /**
     * checks the function Add()
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks if p1+Vector(2,2,2)=p3
        Point3D p=p1.add(new Vector(2,2,2));
        assertEquals(p,p3,"the Add function does not add correctly");
    }
    /**
     * checks the function Subtract()
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks if the subtraction between points returns the correct answer
        Vector v2 =p1.subtract(new Point3D(2,2,2));
        assertEquals(v1,v2,"the Subtract function does not Subtract correctly");
    }
}