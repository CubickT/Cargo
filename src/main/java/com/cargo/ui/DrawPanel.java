package com.cargo.ui;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Polygon;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    static double PADDING = 30;

    private final Polygon cargoPoly;
    private final Polygon boundsPoly;

    public DrawPanel(Polygon boundsPoly, Polygon cargoPoly) {
        this.boundsPoly = boundsPoly;
        this.cargoPoly = cargoPoly;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        Envelope envelope = boundsPoly.getEnvelopeInternal();
        envelope.expandToInclude(cargoPoly.getEnvelopeInternal());

        double scaleX = (panelWidth - 2 * PADDING) / envelope.getWidth();
        double scaleY = (panelHeight - 2 * PADDING) / envelope.getHeight();
        double scale = Math.min(scaleX, scaleY);

        double offsetX = PADDING + (panelWidth - 2 * PADDING - envelope.getWidth() * scale) / 2;
        double offsetY = PADDING + (panelHeight - 2 * PADDING - envelope.getHeight() * scale) / 2;


        drawPolygon(g2d, boundsPoly, Color.BLUE, envelope, scale, offsetX, offsetY);
        drawPolygon(g2d, cargoPoly, Color.BLACK, envelope, scale, offsetX, offsetY);
    }


    private Point worldToScreen(Coordinate coord, Envelope envelope, double scale, double offX, double offY) {
        int x = (int) Math.round(offX + (coord.x - envelope.getMinX()) * scale);
        int y = (int) Math.round(offY + (envelope.getMaxY() - coord.y) * scale);

        return new Point(x, y);
    }


    private void drawPolygon(Graphics2D g2d, Polygon poly, Color color, Envelope envelope, double scale, double offX, double offY) {
        g2d.setColor(color);

        Coordinate[] coords = poly.getCoordinates();
        int n = coords.length;
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            Point p = worldToScreen(coords[i], envelope, scale, offX, offY);
            xPoints[i] = p.x;
            yPoints[i] = p.y;
        }
        g2d.drawPolygon(xPoints, yPoints, n);
    }
}
