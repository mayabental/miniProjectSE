package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class RayTracerBase consists of a scene.
 *
 * @author yael and maya
 */
public abstract class RayTracerBase {
    protected final Scene _scene;

    /**
     * RayTracerBase
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * traceRay
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}
