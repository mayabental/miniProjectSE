package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing camera Rays Intersection with different geometries
 */
class cameraRaysIntersectionsTest {
    /**
     * testing Construct Ray Through Pixel And Sphere
     */
    @Test
    public void testConstructRayThroughPixelAndSphere() {
        //TC01: sphere is captured by one pixel and creates two Intersection points
        Camera camera1 = new Camera(Point3D.PointZero, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere1=new Sphere(new Point3D(0,0,-3),1);
        assertEquals(2,sumOfIntersections(camera1,3,3,sphere1),"incorrect amount of Intersections points when sphere is" +
                " after view plane and camera and is captured by one pixel");

        //TC02: sphere is captured by all pixels and creates 18 Intersection points
        Camera camera2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere2=new Sphere(new Point3D(0,0,-2.5),2.5);
        assertEquals(18,sumOfIntersections(camera2,3,3,sphere2),"incorrect amount of Intersections points when sphere is " +
                "after view plane and camera and is captured by all pixels");

        //TC03: sphere is captured by 5 pixels and creates 10 Intersection points
        Camera camera3 = new Camera(new Point3D(0, 0, 0.5), new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere3=new Sphere(new Point3D(0,0,-2),2);
        assertEquals(10,sumOfIntersections(camera3,3,3,sphere3),"incorrect amount of Intersections points when sphere is " +
                "after view plane and camera and is captured by part of the pixels");

        //TC04: view plane is inside of the sphere and creates 9 Intersection points
        Camera camera4 = new Camera(Point3D.PointZero, new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere4=new Sphere(new Point3D(0,0,-1),4);
        assertEquals(9,sumOfIntersections(camera4,3,3,sphere4),"incorrect amount of Intersections points when camera is " +
                "inside of the sphere");

        //TC05: sphere is not captured by view plane
        Camera camera5 = new Camera(Point3D.PointZero, new Vector(0,0,-1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere5=new Sphere(new Point3D(0,0,1),0.5);
        assertEquals(0,sumOfIntersections(camera5,3,3,sphere5),"incorrect amount of Intersections points when sphere is" +
                " before view plane ");
    }

    /**
     * testing Construct Ray Through Pixel And Plane
     */
    @Test
    public void testConstructRayThroughPixelAndPlane() {
        //TC01: Plane is captured by all pixel and creates 9 Intersection points
        Camera camera1 = new Camera(Point3D.PointZero, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Plane plane1=new Plane(new Point3D(0,0,-3),new Vector(0,0,1));
        assertEquals(9,sumOfIntersections(camera1,3,3,plane1),"incorrect amount of Intersections points when Plane is " +
                "after view plane and camera");

        //TC02: sideways Plane is captured by all pixel and creates 9 Intersection points
        Camera camera2 = new Camera(Point3D.PointZero, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Plane plane2=new Plane(new Point3D(0,0,-10),new Vector(0,-1,10));
        assertEquals(9,sumOfIntersections(camera2,3,3,plane2),"incorrect amount of Intersections points when Plane is " +
                "after view plane and camera and plane is sideways");

        //TC03: sideways Plane is captured by 6 pixels and creates 6 Intersection points
        Camera camera3 = new Camera(Point3D.PointZero, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Plane plane3=new Plane(new Point3D(0,0,-10),new Vector(0,-10,10));
        assertEquals(6,sumOfIntersections(camera3,3,3,plane3),"incorrect amount of Intersections points when Plane is " +
                "after view plane and camera and plane is sideways");
    }

    /**
     * testing Construct Ray Through Pixel And Triangle
     */
    @Test
    public void testConstructRayThroughPixelAndTriangle() {
        //TC01: Triangle is captured by one pixel and creates 1 Intersection points
        Camera camera1 = new Camera(Point3D.PointZero, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Triangle triangle1=new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        assertEquals(1,sumOfIntersections(camera1,3,3,triangle1),"incorrect amount of Intersections points when Triangle is " +
                "after view plane and camera and captured by one pixel");

        //TC02: Triangle is captured by two pixels and creates two Intersection points
        Camera camera2 = new Camera(Point3D.PointZero, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Triangle triangle2=new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        assertEquals(2,sumOfIntersections(camera2,3,3,triangle2),"incorrect amount of Intersections points when Triangle is " +
                "after view plane and camera and captured by two pixels");

    }

    /**
     * sumOfIntersections
     * @param camera
     * @param Nx
     * @param Ny
     * @param geometry
     * @return amount of intersection points
     */
    public int sumOfIntersections(Camera camera, int Nx, int Ny, Geometry geometry){
        int count =0;
        for(int i=0;i<Nx;i++){
            for(int j=0;j<Ny;j++){
                Ray ray=camera.constructRayThroughPixel(Nx,Ny,j,i);
                List<Intersectable.GeoPoint> findInt=geometry.findGeoIntersections(ray);
                if(findInt!=null)
                {
                    count+=findInt.size();
                }
            }
        }
        return count;
    }
}