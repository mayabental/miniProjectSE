package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class Point Light represents a light source that shines from a certain point in space.
 * Extends Light abstract class and implements LightSource interface.
 *
 * @author yael and maya
 */

public class PointLight extends Light implements LightSource{
    private Point3D _position;
    private double _kC; // Constant attenuation
    private double _kL; // Linear attenuation
    private double _kQ; // Quadratic attenuation

    /**
     * constructor for point light
     * @param colorIntensity intensity (color) of light
     * @param position position of light
     * @param kC Constant attenuation
     * @param kL Linear attenuation
     * @param kQ Quadratic attenuation
     */
    public PointLight(Color colorIntensity, Point3D position, double kC, double kL, double kQ) {
        super(colorIntensity);
        this._position = position;
        this._kC = kC;
        this._kL = kL;
        this._kQ = kQ;
    }

    /**
     * constructor for point light
     * by default, the constant attenuation value is 1 and the other two values are 0
     * @param colorIntensity intensity (color) of light
     * @param position position of light
     */
    public PointLight(Color colorIntensity, Point3D position) {
        this(colorIntensity, position, 1d, 0d, 0d);
    }

    /**
     * dummy overriding Light getIntensity()
     * @return light intensity
     */
    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    /**
     * overriding LightSource getIntensity(Point3D)
     * @param p point
     * @return intensity of light from light source on certain point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dsquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        return (_intensity.reduce(_kC + _kL * d + _kQ * dsquared));
    }

    /**
     * @param p point
     * @return Light vector
     */
    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalize();
    }

    /**
     * @param point point
     * @return distance from light to point
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

    /**
     * setkC
     * @param kC
     * @return this
     */
    public PointLight setkC(double kC) {
        _kC = kC;
        return this;
    }

    /**
     * setkL
     * @param kL
     * @return this
     */
    public PointLight setkL(double kL) {
        _kL = kL;
        return this;
    }

    /**
     * setkQ
     * @param kQ
     * @return this
     */
    public PointLight setkQ(double kQ) {
        _kQ = kQ;
        return this;
    }
}
