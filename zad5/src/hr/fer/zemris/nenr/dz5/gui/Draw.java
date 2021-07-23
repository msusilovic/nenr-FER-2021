package hr.fer.zemris.nenr.dz5.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Draw extends JFrame {

    private static final int M = 10;
    private static final long serialVersionUID = 1L;
    private int gestureCounter = 0;

    List<Point2D.Double> gesture = new ArrayList<>();
    List<Point2D.Double> representation = new ArrayList<>();

    public Draw() {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocation(20, 20);
        setSize(500, 500);
        setTitle("DZ 5");
        initGUI();
    }

    private void initGUI() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.WHITE);
        JPanel panel = new JPanel();

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(representation.size());
                gesture = new ArrayList<>();
                representation = new ArrayList<>();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                double tcx = 0;
                double tcy = 0;
                for (Point2D.Double p : gesture) {
                    tcx += p.x;
                    tcy += p.y;
                }
                tcx /= gesture.size();
                tcy /= gesture.size();
                double finalTcx = tcx;
                double finalTcy = tcy;
                gesture = gesture.stream().map(a -> new Point2D.Double(a.x - finalTcx, a.y - finalTcy)).collect(Collectors.toList());

                double mx = 0;
                double my = 0;
                for (Point2D.Double p : gesture) {
                    mx = Math.max(mx, Math.abs(p.x));
                    my = Math.max(my, Math.abs(p.y));
                }
                double m = Math.max(mx, my);
                gesture = gesture.stream().map(a -> new Point2D.Double(a.x / m, a.y / m)).collect(Collectors.toList());

                double D = 0;
                for (int i = 1; i < gesture.size(); i++) {
                    D += gesture.get(i).distance(gesture.get(i - 1));
                }

                for (int k = 0; k < M; k++) {
                    double dist = k * D / (M - 1);
                    double currentDist = 0;
                    int i = 0;
                    while (currentDist <= dist) {
                        i++;
                        if (i == gesture.size()) break;
                        currentDist += gesture.get(i).distance(gesture.get(i - 1));
                    }
                    representation.add(gesture.get(i - 1));
                }

                repaint();
                gestureCounter++;
                try {
                    DrawUtil.writeGesture(representation, (gestureCounter - 1) / 20);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (gestureCounter == 100) {
                    setVisible(false);
                }
            }

        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!gesture.isEmpty()) {
                    panel.getGraphics().drawLine((int) gesture.get(gesture.size() - 1).x, (int) gesture.get(gesture.size() - 1).y, e.getX(), e.getY());
                }
                gesture.add(new Point2D.Double(e.getX(), e.getY()));
            }
        });

        c.add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Draw().setVisible(true));
    }
}
