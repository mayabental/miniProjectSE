package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.MissingResourceException;

/**
 * class Render consists of ImageWriter, Camera and RayTracerBase.
 *
 * @author yael and maya
 */
public class Render {
    private ImageWriter _imageWriter;
    private Camera _camera;
    private RayTracerBase _rayTracer;
    private static final int NUM_OF_SAMPLE_RAYS = 9;

    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage
    private static final int LEVEL = 4;

    /*
    adaptiveSuperSampling
    return color of pixel
     */
    public Color adaptiveSuperSampling(Point3D pCenter, double w, double h, int level) {
        var lstc = new LinkedList<Color>();
        var lstp = new LinkedList<Point3D>();

        //produce 4 rays - from each center of the 4 "mini pixels" in the given pixel
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                Point3D point3d = _camera.calcPij(w, h, 2, 2, b, a, pCenter);
                lstp.add(point3d);
                lstc.add(_rayTracer.traceRay(_camera.buildRay(point3d)));
            }
        }

        Color color = new Color(0, 0, 0);
        //if we reach the maximum level of recursion, return the average of the 4 rays
        if (level == 1) {
            for (Color c : lstc)
                color = color.add(c);
            return color.reduce(4);
        }

        Color centerColor = _rayTracer.traceRay(_camera.buildRay(pCenter));
        for (int k = 0; k < 4; k++) {
            //for each mini pixel:
            //if the color of its center is equal to the color of the center of the real pixel-
            //this is the returned color
            //else- we continue to divide the mini pixel to 4 mini pixels in recursion
            if (!(lstc.get(k)).equals(centerColor))
                color = color.add(adaptiveSuperSampling(lstp.get(k), w / 2, h / 2, level - 1).reduce(4));
            else
                color = color.add(lstc.get(k).reduce(4));
        }
        return color;
    }
    /*
    renderImageAdaptiveSuperSampling
    render image using adaptive super sampling 
     */
    public void renderImageAdaptiveSuperSampling() {
        try {
            if (_imageWriter == null)
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");

            if (_camera == null)
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");

            if (_rayTracer == null)
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");

            //rendering the image
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {

                    Point3D Pij = _camera.calcPij(_camera.getWidth(), _camera.getHeight(), nX, nY, j, i, _camera.getPCenter());
                    Color pixelColor = adaptiveSuperSampling(Pij, _camera.getWidth() / nX, _camera.getHeight() / nY, LEVEL);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * setImageWriter
     *
     * @param imageWriter
     * @return this
     */
    //chaining methods
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * setCamera
     *
     * @param camera
     * @return this
     */
    //chaining methods
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     * setRayTracer
     *
     * @param basicRayTracer
     * @return this
     */
    //chaining methods
    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        _rayTracer = basicRayTracer;
        return this;
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = _camera.constructRayThroughPixel(nX, nY, col, row);
        Color color = _rayTracer.traceRay(ray);
        _imageWriter.writePixel(col, row, color);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * renderImage - renders the image from the parameters in render
     */
    public void renderImage() {
        try {
            if (_imageWriter == null)
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");

            if (_camera == null)
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");

            if (_rayTracer == null)
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");

            //rendering the image
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            Color pixelColor = Color.BLACK;
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    for (Ray ray : _camera.constructRaysThroughPixel(nX, nY, j, i, NUM_OF_SAMPLE_RAYS)) {
                        //calculates the color of the pixel (i,j) using super sampling traceRay
                        pixelColor = pixelColor.add(_rayTracer.traceRay(ray));
                    }
                    pixelColor = pixelColor.reduce(NUM_OF_SAMPLE_RAYS * NUM_OF_SAMPLE_RAYS);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

//    /**
//     * renderImage - renders the image from the parameters in render
//     */
//    public void renderImage() {
//        try {
//            if (_imageWriter == null) {
//                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
//            }
//
//            if (_camera == null) {
//                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
//            }
//
//            if (_rayTracer == null) {
//                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
//            }
//
//            //rendering the image
//            int nX = _imageWriter.getNx();
//            int nY = _imageWriter.getNy();
//            for (int i = 0; i < nY; i++) {
//                for (int j = 0; j < nX; j++) {
//                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
//                    Color pixelColor = _rayTracer.traceRay(ray);
//                    _imageWriter.writePixel(j, i, pixelColor);
//                }
//            }
//        } catch (MissingResourceException e) {
//            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
//        }
//    }

    /**
     * printGrid
     *
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color) {
        if (_imageWriter == null || _camera == null || _rayTracer == null)
            throw new MissingResourceException("render cannot be empty", "Render", "");

        int rows = this._imageWriter.getNy();
        int columns = _imageWriter.getNx();
        //Writing the lines.
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if ((i % interval == 0) || (j % interval == 0)) {
                    _imageWriter.writePixel(i, j, color);
                }
            }
        }
    }

    /**
     * use the imageWriter to create an image from scene
     */
    public void writeToImage() {
        if (_imageWriter == null || _camera == null || _rayTracer == null)
            throw new MissingResourceException("render cannot be empty", "Render", "");
        _imageWriter.writeToImage();
    }

}
