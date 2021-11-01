package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * Class Spot Light represents a light source that shines from a certain point in space, and in a certain direction.
 * Extends PointLight class.
 *
 * @author yael and maya
 */

public class SpotLight extends PointLight {
    Vector _direction;

    /**
     * spot light constructor
     * @param colorIntensity intensity (color) of light
     * @param position position of light
     * @param direction direction of light
     */
    public SpotLight(Color colorIntensity, Point3D position, Vector direction) {
        super(colorIntensity, position);
        this._direction = new Vector(direction.getHead()).normalized();
    }

    /**
     * getIntensity
     * @return spotlight intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        //dot product of light-direction vector and vector from light source to point
        double projection = _direction.dotProduct(getL(p));

        if (Util.isZero(projection)) {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color pointlightIntensity = super.getIntensity(p);//intensity of light source

        //scale the intensity with factor
        return (pointlightIntensity.scale(factor));
    }

}