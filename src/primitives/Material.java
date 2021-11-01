package primitives;
/**
 * Class Material represents the material of an object in the scene, its shininess, reflectiveness, transparency.
 *
 * @author yael and maya
 */
public class Material {
    double _kD=0;
    double _kS=0;
    int _nShininess=0;
    double _kT=0;
    double _kR=0;

    /**
     * Constructor for material
     * @param _kD Diffusion factor of material
     * @param _kS Specular factor of material
     * @param _nShininess Shininess level of material
     * @param _kT Transparency factor of material
     * @param _kR Reflectance factor of material
     */
    public Material(double _kD, double _kS,int _nShininess,double _kT,double _kR) {
        this._kD = _kD;
        this._kS = _kS;
        this._kT=_kT;
        this._kR=_kR;
        this._nShininess = _nShininess;
    }

    /**
     * Constructor for material
     */
    public Material() {
        this(0, 0, 0, 0, 0);
    }
    /**
     * @return Transparency factor of material
     */
    public double getKT() { return _kT;}

    /**
     * @return Reflectance factor of material
     */
    public double getKR() { return _kR; }

    /**
     * @return Diffusion factor of material
     */
    public double getKd() {
        return _kD;
    }

    /**
     * @return Specular factor of material
     */
    public double getKs() {
        return _kS;
    }

    /**
     * @return Shininess of material
     */
    public int getShininess() {
        return _nShininess;
    }

    /**
     * setkD
     * @param kD
     *  @return this;
     */
    public Material setkD(double kD) {
        _kD = kD;
        return this;
    }

    /**
     * setkS
     * @param kS
     * @return this
     */
    public Material setkS(double kS) {
        _kS = kS;
        return this;
    }

    /**
     * setnShininess
     * @param nShininess
     * @return this
     */
    public Material setnShininess(int nShininess) {
        _nShininess = nShininess;
        return this;
    }

    /**
     * setkT
     * @param kT
     * @return this
     */
    public Material setkT(double kT) {
        _kT = kT;
        return this;
    }

    /**
     * setkR
     * @param kR
     * @return this
     */
    public Material setkR(double kR) {
        _kR = kR;
        return this;
    }
}