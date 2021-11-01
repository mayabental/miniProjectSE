package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * class Camera represents a simple camera with three vectors Vto
 * Vup Vright and a starting point.
 *
 * @author yael and maya
 */
public class Camera {
    final private Point3D P0;
    final private Vector Vup;
    final private Vector Vto;
    final private Vector Vright;

    //view plane consist of width, height and distance
    private double width;
    private double height;
    private double distance;

    /**
     * constructor for camera that receives the point where camera is located, vUp, and vTowards, and creates vRight by cross product of both vectors
     * @param p0 camera's location
     * @param vto vector from the camera towards scene
     * @param vup vector from camera upwards
     */
    public Camera(Point3D p0, Vector vto,Vector vup) {
        if(!isZero(vto.dotProduct(vup))){
            throw new IllegalArgumentException("up vector and to vector are not orthogonal");
        }
        P0 = p0;
        Vup = vup.normalized();
        Vto = vto.normalized();
        Vright= (Vto.crossProduct(Vup));
    }

    /**
     * getP0
     @return the point where camera is located
     */
    public Point3D getP0() {
        return P0;
    }

    /**
     * getVup
     * @return vector Vup
     */
    public Vector getVup() {
        return Vup;
    }

    /**
     * getVto
     * @return vector Vto
     */
    public Vector getVto() {
        return Vto;
    }

    /**
     * getVright
     * @return vector Vright
     */
    public Vector getVright() {
        return Vright;
    }


    /**
     * getWidth
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * getHeight
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * getDistance
     * @return distance
     */
    public double getDistance() {
        return distance;
    }
    /**
     * setDistance
     * @param distance
     * @return camera-(this)
     */
    //chaining methods
    public Camera setDistance(double distance){
        this.distance=distance;
        return this;
    }

    /**
     * setViewPlaneSize
     * @param width
     * @param height
     * @return camera-(this)
     */
    //chaining methods
    public Camera setViewPlaneSize(double width, double height){
        this.width=width;
        this.height=height;
        return this;
    }

    /**
     * constructRayThroughPixel
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return  Ray from camera to pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

        Point3D Pc= P0.add(Vto.scale(distance));
        double Rx=width/nX;
        double Ry=height/nY;

        Point3D Pij = Pc;
        double Yi= -Ry * (i-(nY-1)/2d);
        double Xj= Rx * (j-(nX-1)/2d);

        if(!isZero(Xj)){
            Pij = Pij.add(Vright.scale(Xj));
        }
        if(!isZero(Yi)){
            Pij = Pij.add(Vup.scale(Yi));
        }
        return new Ray(P0,Pij.subtract(P0));
    }
    /**
     * Receives pixel coordinates and constructs a beam of rays through it.
     * This method is used for super sampling. Creating a beam of rays allows a more exact calculation of the color of the pixel.
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j j coordinate of pixel
     * @param i i coordinate of pixel
     * @param num_of_sample_rays number of sample rays required
     * @return beam of rays through receives pixel
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, int num_of_sample_rays)
    {
        //The distance between the screen and the camera cannot be 0
        if (isZero(distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        List<Ray> sample_rays = new ArrayList<>();

        double Ry = height/nY; //The height of every  pixel
        double Rx = width/nX;  //The width of every pixel
        double yi =  ((i - nY/2d)*Ry); //distance of original pixel from (0,0) on Y axis
        double xj=   ((j - nX/2d)*Rx); //distance of original pixel from (0,0) on x axis
        double pixel_Ry = Ry/num_of_sample_rays; //The height of every mini pixel
        double pixel_Rx = Rx/num_of_sample_rays; //The width of every mini pixel

        for (int row = 0; row < num_of_sample_rays; ++row) {//foreach place in the pixel grid
            for (int column = 0; column < num_of_sample_rays; ++column) {
                sample_rays.add(constructRaysThroughPixel(pixel_Ry,pixel_Rx,yi, xj, row, column));//add the ray
            }
        }
        return sample_rays;
    }

    /**
     * In this function we treat each pixel like a little screen of its own and divide it to smaller "pixels".
     * Through each one we construct a ray. This function is similar to ConstructRayThroughPixel.
     * @param Ry height of each grid block we divided the pixel into
     * @param Rx width of each grid block we divided the pixel into
     * @param yi distance of original pixel from (0,0) on Y axis
     * @param xj distance of original pixel from (0,0) on X axis
     * @param j j coordinate of small "pixel"
     * @param i i coordinate of small "pixel"

     * @return beam of rays through pixel
     */
    private Ray constructRaysThroughPixel(double Ry,double Rx, double yi, double xj, int j, int i)
    {
        Point3D Pc = getP0().add(Vto.scale(distance)); //the center of the screen point

        double y_sample_i =  (i *Ry + Ry/2d); //The pixel starting point on the y axis
        double x_sample_j=   (j *Rx + Rx/2d); //The pixel starting point on the x axis


        Point3D Pij = Pc; //The point at the pixel through which a beam is fired
        //Moving the point through which a beam is fired on the x axis
        if (!Util.isZero(x_sample_j + xj))
        {
            Pij = Pij.add(Vright.scale(x_sample_j + xj));
        }
        //Moving the point through which a beam is fired on the y axis
        if (!Util.isZero(y_sample_i + yi))
        {
            Pij = Pij.add(Vup.scale(-y_sample_i -yi ));
        }
        Vector Vij = Pij.subtract(getP0());
        return new Ray(getP0(),Vij);//create the ray throw the point we calculate here
    }



    /*public Point3D calcPIJ(Point3D pCenter, double width, double height, int nX, int nY, int j, int i) {
		double Ry = height / nY;
		double Rx = width / nX;
		double yi = -(double)(i - ((nY - 1) /(double)2)) * Ry;
		double xj = (double)(j - (nX - 1) /(double)2) * Rx;
		Point3D pIJ = pCenter;
		if (xj != 0) pIJ = pIJ.add(vRight.scale(xj));
		if (yi != 0) pIJ = pIJ.add(vUp.scale(yi));
		return pIJ;
	}*/


    public Point3D calcPij(double width,double height,int nX, int nY, int j, int i,Point3D pCenter) {
        double Ry = height / nY;
        double Rx = width / nX;
        double yi = -(double)(i - ((nY - 1) /(double)2)) * Ry;
        double xj = (double)(j - (nX - 1) /(double)2) * Rx;
        Point3D pIJ = pCenter;
        if (xj != 0) pIJ = pIJ.add(getVright().scale(xj));
        if (yi != 0) pIJ = pIJ.add(getVup().scale(yi));
        return pIJ;
    }


    public Ray buildRay(Point3D p){
        return new Ray(P0, p.subtract(P0));
    }


    public Point3D getPCenter() { return P0.add(Vto.scale(distance));}

}

