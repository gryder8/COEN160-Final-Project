import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSliderUI extends BasicSliderUI {

    private BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f);

    public CustomSliderUI(JSlider b) {
        super(b);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(12, 16);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(stroke);
        g2d.setPaint(Color.BLACK);
        if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
            g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2,
                    trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
        } else {
            g2d.drawLine(trackRect.x + trackRect.width / 2, trackRect.y,
                    trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
        }
        g2d.setStroke(old);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x;
        int x2 = thumbRect.x + thumbRect.width;
        int width = thumbRect.width - 4;
        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3;
        Ellipse2D inner = new Ellipse2D.Double(x1+1, thumbRect.y, 13, 13);
        Ellipse2D outer = new Ellipse2D.Double(x1, thumbRect.y-1, 15, 15);
//        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
//        shape.moveTo(x1, topY);
//        shape.lineTo(x2, topY);
//        shape.lineTo((x1 + x2) / 2, topY + width);
//        shape.closePath();
        g2d.setPaint(new Color(0, 0, 0));
        g2d.fill(outer);
//        g2d.fill(shape);
        Stroke old = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2f));
        g2d.setPaint(new Color(250, 240, 38));
        g2d.fill(inner);
        //g2d.draw(shape);
        g2d.setStroke(old);
    }
}