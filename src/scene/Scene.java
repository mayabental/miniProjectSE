package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class Scene consists of name, ambientLight, backgroundColor and geometries.
 *
 *  @author yael and maya
 */
public class Scene {
    public final String _name;
    public AmbientLight _ambientLight= new AmbientLight(Color.BLACK,0);
    public Color _backgroundColor=Color.BLACK;
    public Geometries geometries=new Geometries();
    public List<LightSource> lights=new LinkedList<LightSource>();
    /**
     * constructor
     * @param name
     */
    public Scene(String name) {
        _name = name;
    }

    /**
     * setLights
     * @param lights
     * @return
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * setAmbientLight
     * @param ambientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this._ambientLight=ambientLight;
        return this;
    }

    /**
     * setBackground
     * @param color
     * @return this
     */
    public Scene setBackground(Color color) {
        this._backgroundColor=color;
        return this;
    }
}
