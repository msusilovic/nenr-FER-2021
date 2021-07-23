package hr.fer.zemris.nenr.dz5.gui;

import java.awt.geom.Point2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DrawUtil {

    private static final String[] codes = {"1,0,0,0,0", "0,1,0,0,0", "0,0,1,0,0", "0,0,0,1,0", "0,0,0,0,1"};
    public static void writeGesture(List<Point2D.Double> gesture, int gestureIndex) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for(Point2D.Double point : gesture) {
            stringBuilder.append(point.x).append(",").append(point.y).append(",");
        }
        stringBuilder.append(codes[gestureIndex]).append("\n");
        FileOutputStream fos = new FileOutputStream("geste.txt", true);
        fos.write(stringBuilder.toString().getBytes());
        fos.close();
    }
}
