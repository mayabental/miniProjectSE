package primitives;

import static primitives.Point3D.PointZero;

public class Vector {
    Point3D _head;

    public Point3D getHead(){
        return new Point3D(_head._x, _head._y, _head._z);
    }
    public Vector(Point3D head){
        if(head.equals(PointZero)){
            throw new IllegalArgumentException("head cannot be Point(0, 0)");
        }
        _head = head;
    }
}
