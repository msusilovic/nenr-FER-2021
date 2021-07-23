package hr.fer.zemris.nenr.dz5;

import hr.fer.zemris.nenr.dz5.neuralNetwork.NeuralNetwork;
import hr.fer.zemris.nenr.dz5.neuralNetwork.TrainingData;
import hr.fer.zemris.nenr.dz5.neuralNetwork.Util;
import hr.fer.zemris.nenr.dz5.neuralNetwork.function.SigmoidFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterClassifier extends JFrame {

    private static final int M = 10;
    private static final long serialVersionUID = 1L;

    private List<Point2D.Double> gesture = new ArrayList<>();
    private List<Point2D.Double> representation = new ArrayList<>();
    private NeuralNetwork neuralNetwork;

    public CharacterClassifier() {
        List<TrainingData> data = new ArrayList<>();
        try {
            data = Util.extractData("geste.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
        neuralNetwork = new NeuralNetwork(new int[]{20, 10, 5}, 0.1, new SigmoidFunction());
        neuralNetwork.train(1, data);

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
                double[] x = new double[2 * M];
                for (int i = 0; i < representation.size(); i++) {
                    x[2 * i] = representation.get(i).x;
                    x[2 * i + 1] = representation.get(i).y;
                }

                double[] y = neuralNetwork.evaluate(x);
                System.out.println(Util.getCharacterFromArray(y));
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
        SwingUtilities.invokeLater(() -> new CharacterClassifier().setVisible(true));
    }
}
