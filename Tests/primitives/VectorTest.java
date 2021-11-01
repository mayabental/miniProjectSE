package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Vector tests for primitives.Vector class
 *
 * @author yael and maya
 */
class VectorTests
{
    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    // vectors to help us during the tests
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v = new Vector(1, 2, 3);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * checks if the Constructor work's correctly
     */
    @Test
    public void testConstructor()
    {
        // =============== Boundary Values Tests ==================
        //TC01: checks if vector (0,0,0) throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, ()->new Vector(0,0,0));
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void testAdd()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks if add function adds correctly
        Vector v = v1.add(v2);
        Vector v4 = new Vector(-1,-2,-3);
        assertEquals(v,v4);
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    public void testSubtract()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks if subtract function subtracts correctly
        Vector f = v1.subtract(v2);
        Vector v4 = new Vector(-3,-6,-9);
        assertEquals(v4,f);
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: checks if scalar function multiplies t*(x,y,z) correctly
        Vector vscale = v1.scale(-0.999999);
        assertEquals(v1.length(), vscale.length(), 0.0001);
    }



    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct()
    {

        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue( Util.isZero(vr.dotProduct(v1)));
        assertTrue( Util.isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class, () -> v1.crossProduct(v3));
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}

    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void testDotProduct()
    {
        // =============== Boundary Values Tests ==================
        // TC01: checks if DotProduct function works when vectors are vertex
        assertTrue(isZero(v1.dotProduct(v3)));
        assertTrue(isZero(v1.dotProduct(v2) + 28));

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: checks LengthSquared value is correct
        assertTrue(isZero(v1.lengthSquared() - 14));

    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void testLength()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: checks Length value is correct
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue(isZero(vCopyNormalize.length() - 1));
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    public void testNormalize()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: checks Normalize function on head
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(vCopy,vCopyNormalize);

    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    public void testNormalized()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: checks normalized returns correct values
        Vector u = v.normalized();
        double sqrt14= Math.sqrt(14);
        Vector z = new Vector(1/sqrt14,2/sqrt14,3/sqrt14);
        assertEquals(u, z);
    }

}
