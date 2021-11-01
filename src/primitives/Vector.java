package primitives;

import static primitives.Point3D.PointZero;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * basic geometric object for Vector representing a vector given a Point3D _head
 *
 * @author yael and maya
 */
public class Vector {
    Point3D _head;

    /**
     * constructor (Point3D head)
     * primary constructor for Vector3D
     * @return head
     */
    public Vector(Point3D head) {
        if (head.equals(PointZero)) {
            throw new IllegalArgumentException("head cannot be Point(0, 0)");
        }
        _head = head;
    }

    /**
     * constructor (double x, double y, double z)
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    /**
     * getHead
     * @return _head
     */
    public Point3D getHead() {
        return _head;
    }

    /**
     * toString
     * @return head as string
     */
    @Override
    public String toString() {
        return "Vector [head=" + _head.toString() + "]";
    }

    /**
     * equals
     * @param o
     * @return if both vectors are equal true otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    /**
     * adding vector with value of new vector
     * @param v
     * @return new vector vec with the added Coordinate values
     */
    public Vector add(Vector v) {
        Coordinate cx = new Coordinate(v._head._x.coord + this._head._x.coord);
        Coordinate cy = new Coordinate(v._head._y.coord + this._head._y.coord);
        Coordinate cz = new Coordinate(v._head._z.coord + this._head._z.coord);

        Point3D p = new Point3D(cx.coord, cy.coord, cz.coord);
        Vector vec = new Vector(p);
        return vec;
    }

    /**
     * subtract
     * @param v
     * @return new vector vec with the subtracted Coordinate values
     */
    public Vector subtract(Vector v)
    {
        if(v.equals(this))
        {
            throw new IllegalArgumentException("parameter vector can't be equal to me");
        }
        else
        {
            double cx = this._head._x.coord-v._head._x.coord ;
            double cy = this._head._y.coord-v._head._y.coord;
            double cz = this._head._z.coord-v._head._z.coord ;

            Point3D p = new Point3D(cx, cy, cz);
            Vector vec = new Vector(p);
            return vec;
        }

    }
    /**
     * distanceSquared: square distance between this point3d and received value of point3d
     * @param p
     * @return square distance
     */
    public double distanceSquared(Point3D p)
    {
        double dis =  ((p._x.coord-_head._x.coord)*(p._x.coord-_head._x.coord) + (p._y.coord - _head._y.coord)*(p._y.coord - _head._y.coord) + (p._z.coord - _head._z.coord)*(p._z.coord - _head._z.coord));
        return dis;

    }

    /**
     * distance between this point3d and recieved value of point3d
     * @param p
     * @return distance
     */
    public double distance(Point3D p)
    {
        double dis = distanceSquared(p);
        return Math.sqrt(dis);
    }


    /**
     * lengthSquared
     * @return  length of vector
     */
        public double lengthSquared()
    {
        double xx = this._head._x.coord*this._head._x.coord;
        double yy = this._head._y.coord*this._head._y.coord;
        double zz = this._head._z.coord*this._head._z.coord;

        return(xx+yy+zz);
    }

    /**
     * length of vector
     * @return
     */
    public double length()
    {
        return(Math.sqrt(this.lengthSquared()));
    }

    /**
     * normalized
     * normalization of vector
     * @return normalized vector
     */
    public Vector normalized()
    {
        Vector r = new Vector(_head);
        r.normalize();
        return r;
    }

    /**
     *  normalize
     * helps the normalization of this vector
     * @return same vector but normalized
     */
    public Vector normalize()
    {
        double len = length();
        if(isZero(len))
        {
            throw new IllegalArgumentException("length equals 0");
        }
        else
        {
            double




                    x = this._head._x.coord/len;
            double y = this._head._y.coord/len;
            double z = this._head._z.coord/len;

            this._head = new Point3D(x, y, z);

            return this;

        }


    }

    /**
     * scale
     * multiplying vector with value of double
     * @param d
     * @return Vector p
     */
    public Vector scale(double d)
    {
        if(d == 0 || alignZero(d) == 0)
        {
            throw new IllegalArgumentException("multiplier can't be 0");
        }
        else
        {
            Coordinate cx = new Coordinate(d *  this._head._x.coord);
            Coordinate cy = new Coordinate(d *  this._head._y.coord);
            Coordinate cz = new Coordinate(d *  this._head._z.coord);

            Point3D p = new Point3D(cx.coord, cy.coord, cz.coord);
            return new Vector(p);
        }

    }

    /**
     * crossProduct: of vector with new value of vector
     * the normal to both of the vectors(v * this)
     * @param v
     * @return Vector p
     */
    public Vector crossProduct(Vector v)
    {
        Coordinate cx = new Coordinate(this._head._y.coord * v._head._z.coord - this._head._z.coord*v._head._y.coord);
        Coordinate cy = new Coordinate(this._head._z.coord * v._head._x.coord - this._head._x.coord*v._head._z.coord);
        Coordinate cz = new Coordinate(this._head._x.coord * v._head._y.coord - this._head._y.coord*v._head._x.coord);

        Point3D p = new Point3D(cx.coord,cy.coord,cz.coord);
        return new Vector(p);

    }

    /**
     * dotProduct of vector with value of new vector
     * Scalar product,
     * (v x this) : (x1*x2),(y1*y2),(z1*z2)
     * @param v
     * @return p, Scalar product
     */
    public double dotProduct(Vector v)
    {
        double p = (v._head._x.coord * this._head._x.coord) + (v._head._y.coord * this._head._y.coord) + (v._head._z.coord * this._head._z.coord);
        return p;
    }

}
