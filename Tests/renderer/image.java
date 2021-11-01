package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.SpotLight;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;


public class image {
    private Scene scene = new Scene("Test scene");

        @Test
    public void imageTest1() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.GREEN), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Plane(new Point3D(-200,20,-50), new Point3D(20,-200,-50), new Point3D(-200, -200, 100)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)));



        double radius=4;
        for(int i=-200;i<500;i+=50){
            scene.geometries.add(
                    new Sphere(new Point3D(i, 52, -50),radius+=3) //
                            .setEmission(new Color(java.awt.Color.GREEN)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.9)));
        }

        radius=4;
        for(int i=-200;i<500;i+=50){
            scene.geometries.add(
                    new Sphere(new Point3D(i, 10, -50),radius+=3) //
                            .setEmission(new Color(java.awt.Color.CYAN)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.9)));
        }

        radius=3;
        for(int i=-200;i<500;i+=50){
            scene.geometries.add(
                    new Sphere(new Point3D(60, i, -50),radius+=3) //
                    .setEmission(new Color(java.awt.Color.BLUE)) //
                    .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.7)));
        }

        radius=3;
        for(int i=-200;i<500;i+=50){
            scene.geometries.add(
                    new Sphere(new Point3D(20, i, -50),radius+=3) //
                            .setEmission(new Color(java.awt.Color.RED)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.7)));
        }


       scene.geometries.add(
                new Cylinder(new Ray(new Point3D(200,200,-150),new Vector(-1,-1,1)),2,200)
                        .setEmission(new Color(java.awt.Color.YELLOW)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.99)));


        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("image1BeforeSuperSampling", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void flag_imageTest1() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(750);

        scene.geometries.add( //

                new Sphere(new Point3D(0, 0, -50), 80) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.9)),
                new Sphere(new Point3D(0, 0, -50), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.7)),
                new Polygon(new Point3D(-40, -22, 0), new Point3D(-40, 22, 0), new Point3D(40, 22, 0), new Point3D(40, -22, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Polygon(new Point3D(-40, 15, 1), new Point3D(-40, 20, 1), new Point3D(40, 20, 1), new Point3D(40, 15, 1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Polygon(new Point3D(-40, -15, 1), new Point3D(-40, -20, 1), new Point3D(40, -20, 1), new Point3D(40, -15, 1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(15, 6, 1), new Point3D(-15, 6, 1), new Point3D(0, -14.5, 1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Polygon(new Point3D(-3, 4.5, 1.1), new Point3D(3, 4.5, 1.1), new Point3D(7, -1, 1.1), new Point3D(3, -6, 1.1), new Point3D(-3, -6, 1.1), new Point3D(-7, -1, 1.1))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-15, -8, 1), new Point3D(15, -8, 1), new Point3D(0, 12.5, 1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-2, 6.5, 1.2), new Point3D(2, 6.5, 1.2), new Point3D(0, 9.5, 1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(2, -8.5, 1.2), new Point3D(-2, -8.5, 1.2), new Point3D(0, -11.5, 1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-10.5, 4, 1.2), new Point3D(-5.5, 4, 1.2), new Point3D(-8, 1, 1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(10.5, -6, 1.2), new Point3D(5.5, -6, 1.2), new Point3D(8, -3, 1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(10.5, 4, 1.2), new Point3D(5.5, 4, 1.2), new Point3D(8, 1, 1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-10.5, -6, 1.2), new Point3D(-5.5, -6, 1.2), new Point3D(-8, -3, 1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)));
        int radius = 6;
        for (int i = -70; i < 90; i += 20) {
            scene.geometries.add(
                    new Sphere(new Point3D(i, -40, -50), radius) //
                            .setEmission(new Color(java.awt.Color.WHITE)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.9)));
        }
        radius = 4;
        for (int i = -60; i < 80; i += 20) {
            scene.geometries.add(
                    new Sphere(new Point3D(i, -44, -50), radius) //
                            .setEmission(new Color(java.awt.Color.WHITE)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.9)));
        }
        scene.lights.add( //
                new SpotLight(new Color(700, 600, 0), new Point3D(-100, -100, 200), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("image2AfterSuperSampling", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();

    }


    @Test
    public void flag_imageTest2() {
        Scene scene = new Scene("Flag_scene");
        Point3D camera_position = new Point3D(500, 0, -1000);
        Point3D center_of_logo = new Point3D(0, 0, -200);
        Vector camera_vector = (center_of_logo.subtract(camera_position)).normalize();
        Camera camera=new Camera(camera_position,camera_vector, new Vector(0, -1, 0))
                .setViewPlaneSize(200, 200).setDistance(500);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add(
                new Polygon(new Point3D(-40, -22, 0), new Point3D(-40, 22, 0), new Point3D(40, 22, 0), new Point3D(40, -22, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Polygon(new Point3D(-40, 15, -1), new Point3D(-40, 20, -1), new Point3D(40, 20, -1), new Point3D(40, 15, -1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Polygon(new Point3D(-40, -15, -1), new Point3D(-40, -20, -1), new Point3D(40, -20, -1), new Point3D(40, -15, -1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(15, 6, -1), new Point3D(-15, 6, -1), new Point3D(0, -14.5, -1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Polygon(new Point3D(-3, 4.5, -1.1), new Point3D(3, 4.5, -1.1), new Point3D(7, -1, -1.1), new Point3D(3, -6, -1.1), new Point3D(-3, -6, -1.1), new Point3D(-7, -1, -1.1))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-15, -8, -1), new Point3D(15, -8, -1), new Point3D(0, 12.5, -1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-2, 6.5, -1.2), new Point3D(2, 6.5, -1.2), new Point3D(0, 9.5, -1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(2, -8.5, -1.2), new Point3D(-2, -8.5, -1.2), new Point3D(0, -11.5, -1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-10.5, 4, -1.2), new Point3D(-5.5, 4, -1.2), new Point3D(-8, 1, -1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(10.5, -6, -1.2), new Point3D(5.5, -6, -1.2), new Point3D(8, -3, -1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(10.5, 4, -1.2), new Point3D(5.5, 4, -1.2), new Point3D(8, 1, -1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                new Triangle(new Point3D(-10.5, -6, -1.2), new Point3D(-5.5, -6, -1.2), new Point3D(-8, -3, -1.2))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),

                new Sphere( new Point3D(30, 0, -100),96).setEmission( Color.BLACK).setMaterial( new Material(0.8, 0.8, 30, 0.8, 0)),

                new Sphere( new Point3D(30, 0, -100),74).setEmission( Color.BLACK).setMaterial( new Material(0.8, 0.8, 30, 0.8, 0)),

                new Polygon(new Point3D(-250, 100, -50),new Point3D(250, 100, -50),  new Point3D(900, 100, -350), new Point3D(-900, 100, -350)).setEmission(Color.BLACK).setMaterial(new Material(0.8, 1, 10000, 0, 1)));

        scene.lights.add(
                new DirectionalLight(new Color(10, 10, 10), new Vector(1, -1, 0)));
        scene.lights.add(
                new SpotLight(new Color(400, 400, 1020), new Point3D(-150, -150, -50), new Vector(2, 2, -3)));
        Render render = new Render()
                .setImageWriter(new ImageWriter("FlagAdaptiveSuperSampling", 500, 500))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImageAdaptiveSuperSampling();
        render.writeToImage();

    }
}
