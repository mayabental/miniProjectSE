package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 *  geometry class represents interface all geometries in system.
 *
 * @author yael and maya
 */

public abstract class Geometry implements Intersectable
{
    protected Color emission=Color.BLACK;
    private Material material=new Material();

    /**
     * getMaterial
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * setMaterial
     * @param material
     * @return this
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getEmission
     * @return
     */
    public Color getEmission() {
         return emission;
    }

    /**
     * setEmmission
     * @param emission
     * @return Geometry-(this)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Normalization function according to the class
     * @param p
     * @return
     */
    public abstract Vector getNormal(Point3D p);

}
