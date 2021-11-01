package elements;
import primitives.Color;

/**
 * class AmbientLight represents the surrounding light
 *
 *  @author yael and maya
 */
public class AmbientLight extends Light{

    /**
     * empty constructor send as default Black
     */
    public AmbientLight() { super(Color.BLACK);}

    /**
     * constructor
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
}
