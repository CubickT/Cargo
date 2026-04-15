package com.cargo.ui;

import com.cargo.IO.CalculationParameters;
import com.cargo.model.Result;
import com.cargo.model.ZoneModel;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Polygon;

import javax.swing.*;
import java.awt.*;

import static com.cargo.util.GeometryUtils.coordsOut;
import static com.cargo.util.GeometryUtils.gapCoords;

public class DrawPanel extends JPanel {

    static double PADDING = 30;

    private final CalculationParameters params;

    private final Polygon cargoPoly;
    private final Polygon boundsPoly;
    private final Coordinate[][] gapCoords;
    private final ZoneModel[] zones;

    public DrawPanel(Result result, ZoneModel[] zones) {
        this.params = result.params();

        this.boundsPoly = result.bounds().getPoly();
        this.cargoPoly = result.cargo().getPoly();
        this.gapCoords = gapCoords(result.cargo().getCoords(),boundsPoly);
        this.zones = zones;
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


        drawSideStructure(g2d, envelope,scale,offsetX,offsetY);

        for (ZoneModel zone : zones) {
            drawPolygon(g2d, zone.getPoly(), Color.LIGHT_GRAY, envelope, scale, offsetX, offsetY);
        }

        drawPolygon(g2d, boundsPoly, Color.BLUE, envelope, scale, offsetX, offsetY);
        drawPolygon(g2d, cargoPoly, Color.BLACK, envelope, scale, offsetX, offsetY);
        drawGapLines(g2d, Color.RED, envelope, scale, offsetX, offsetY);


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

    private void drawGapLines(Graphics2D g2d, Color color, Envelope envelope, double scale, double offX, double offY) {
        g2d.setColor(color);

        Coordinate[] coords = coordsOut(cargoPoly.getCoordinates(), boundsPoly);

        for (int i = 0; i < gapCoords.length; i++) {
            if (coords[i] != null) {
                Point start = worldToScreen(coords[i], envelope, scale, offX, offY);

                if (gapCoords[i][0] != null) {
                    Point endX = worldToScreen(gapCoords[i][0], envelope, scale, offX, offY);
                    g2d.drawLine(start.x, start.y, endX.x, endX.y);
                }

                if (gapCoords[i][1] != null) {
                    Point endY = worldToScreen(gapCoords[i][1], envelope, scale, offX, offY);
                    g2d.drawLine(start.x, start.y, endY.x, endY.y);
                }
            }
        }
    }

    private void drawSideStructure(Graphics2D g2d, Envelope envelope, double scale, double offX, double offY){
        g2d.setColor(Color.RED);

        Coordinate innerSideStructureStart = new Coordinate(params.inner(),0);
        Coordinate innerSideStructureEnd = new Coordinate(params.inner(), 5300);
        Point innerSideDrawStart = worldToScreen(innerSideStructureStart,envelope,scale,offX,offY);
        Point innerSideDrawEnd = worldToScreen(innerSideStructureEnd,envelope,scale,offX,offY);

        g2d.drawLine(innerSideDrawStart.x , innerSideDrawStart.y, innerSideDrawEnd.x, innerSideDrawEnd.y);

        Coordinate outSideStructureStart = new Coordinate(params.outer() * -1,0);
        Coordinate outSideStructureEnd = new Coordinate(params.outer() * -1, 5300);
        Point outSideDrawStart = worldToScreen(outSideStructureStart,envelope,scale,offX,offY);
        Point outSideDrawEnd = worldToScreen(outSideStructureEnd,envelope,scale,offX,offY);

        g2d.drawLine(outSideDrawStart.x , outSideDrawStart.y, outSideDrawEnd.x, outSideDrawEnd.y);

    }
}
