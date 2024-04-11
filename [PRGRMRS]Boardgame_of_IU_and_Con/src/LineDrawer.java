import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class LineDrawer extends JFrame {
    private static final int GRID_SIZE = 25; // 그리드 사이즈 10픽셀
    private Color currentColor = Color.BLACK; // 초기 선 색상은 검정
    private DrawingPanel drawingPanel;

    public LineDrawer() {
        super("Grid Line Drawer with Color Palette");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        drawingPanel = new DrawingPanel();
        getContentPane().add(drawingPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // 색상 이름과 해당 색상을 매핑
        Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, new Color(128, 0, 128)};
        String[] colorNames = {"Black", "Red", "Blue", "Green", "Purple"};

        // 각 색상에 대한 버튼 생성
        for (int i = 0; i < colors.length; i++) {
            JButton button = new JButton(colorNames[i]);
            Color color = colors[i];
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentColor = color; // 선택된 색상으로 현재 색상 변경
                    drawingPanel.setCurrentColor(currentColor);
                }
            });
            buttonPanel.add(button);
        }
    }

    class DrawingPanel extends JPanel {
        private Point pointStart = null;
        private Point pointEnd = null;
        private List<ColoredLine> lines = new ArrayList<>();

        public void setCurrentColor(Color color) {
            currentColor = color;
        }

        public DrawingPanel() {
            setPreferredSize(new Dimension(400, 400));
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    pointStart = snapToGrid(e.getPoint());
                }

                public void mouseReleased(MouseEvent e) {
                    pointEnd = snapToGrid(e.getPoint());
                    lines.add(new ColoredLine(new Line2D.Double(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y), currentColor));
                    pointStart = null;
                    repaint();
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    pointEnd = snapToGrid(e.getPoint());
                    repaint();
                }
            });
        }

        private Point snapToGrid(Point p) {
            int x = (p.x + GRID_SIZE / 2) / GRID_SIZE * GRID_SIZE;
            int y = (p.y + GRID_SIZE / 2) / GRID_SIZE * GRID_SIZE;
            return new Point(x, y);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            float thickness = 3f;
            g2.setStroke(new BasicStroke(thickness));

            // 그리드 그리기
            g2.setColor(Color.GRAY);
            for (int i = 0; i < getWidth(); i += GRID_SIZE) {
                for (int j = 0; j < getHeight(); j += GRID_SIZE) {
                    g2.drawLine(i, j, i, j); // 작은 점 그리기
                }
            }
            

            // 선분들 그리기
            for (ColoredLine line : lines) {
                g2.setColor(line.color);
                g2.draw(line.line);
            }
        }
    }

    class ColoredLine {
        Line2D line;
        Color color;

        public ColoredLine(Line2D line, Color color) {
            this.line = line;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LineDrawer().setVisible(true);
            }
        });
    }
}
