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
    private final Result result;

    private final Polygon cargoPoly;
    private final Polygon boundsPoly;
    private final Coordinate[][] gapCoords;
    private final ZoneModel[] zones;

    private RenderContext context;

    public DrawPanel(Result result, ZoneModel[] zones) {
        this.result = result;
        this.params = result.params();

        this.boundsPoly = result.bounds().getPoly();
        this.cargoPoly = result.cargo().getPoly();
        this.gapCoords = gapCoords(result.cargo().getCoords(), boundsPoly);

        this.zones = zones;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        context = calculateRenderContext(g2d);

        drawSideStructure();

        for (ZoneModel zone : zones) {
            drawPolygon(zone.getPoly(), Color.LIGHT_GRAY);
        }

        drawPolygon(boundsPoly, Color.BLUE);
        drawPolygon(cargoPoly, Color.BLACK);
        drawGapLines(Color.RED);

        for (double side : result.minPossibleInner()) {
            drawSideAllowed(side, 1);
        }

        for (double side : result.minPossibleOuter()) {
            drawSideAllowed(side, -1);
        }


    }

    private RenderContext calculateRenderContext(Graphics2D g2d) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        assert boundsPoly != null;
        Envelope envelope = boundsPoly.getEnvelopeInternal();
        assert cargoPoly != null;
        envelope.expandToInclude(cargoPoly.getEnvelopeInternal());

        double scaleX = (panelWidth - 2 * PADDING) / envelope.getWidth();
        double scaleY = (panelHeight - 2 * PADDING) / envelope.getHeight();
        double scale = Math.min(scaleX, scaleY);

        double offsetX = PADDING + (panelWidth - 2 * PADDING - envelope.getWidth() * scale) / 2;
        double offsetY = PADDING + (panelHeight - 2 * PADDING - envelope.getHeight() * scale) / 2;

        return new RenderContext(g2d, envelope, scale, offsetX, offsetY);
    }


    private Point worldToScreen(Coordinate coord) {
        int x = (int) Math.round(context.offX + (coord.x - context.envelope.getMinX()) * context.scale);
        int y = (int) Math.round(context.offY + (context.envelope.getMaxY() - coord.y) * context.scale);

        return new Point(x, y);
    }

    private void drawPolygon(Polygon poly, Color color) {
        context.g2d.setColor(color);

        Coordinate[] coords = poly.getCoordinates();
        int n = coords.length;
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            Point p = worldToScreen(coords[i]);
            xPoints[i] = p.x;
            yPoints[i] = p.y;
        }
        context.g2d.drawPolygon(xPoints, yPoints, n);
    }

    private void drawGapLines(Color color) {
        context.g2d.setColor(color);

        Coordinate[] coords = coordsOut(cargoPoly.getCoordinates(), boundsPoly);

        for (int i = 0; i < gapCoords.length; i++) {
            if (coords[i] != null) {
                Point start = worldToScreen(coords[i]);

                if (gapCoords[i][0] != null) {
                    Point endX = worldToScreen(gapCoords[i][0]);
                    context.g2d.drawLine(start.x, start.y, endX.x, endX.y);
                }

                if (gapCoords[i][1] != null) {
                    Point endY = worldToScreen(gapCoords[i][1]);
                    context.g2d.drawLine(start.x, start.y, endY.x, endY.y);
                }
            }
        }
    }

    private void drawSideStructure() {
        context.g2d.setColor(Color.RED);

        Coordinate innerSideStructureStart = new Coordinate(params.inner(), 0);
        Coordinate innerSideStructureEnd = new Coordinate(params.inner(), 5300);
        Point innerSideDrawStart = worldToScreen(innerSideStructureStart);
        Point innerSideDrawEnd = worldToScreen(innerSideStructureEnd);

        context.g2d.drawLine(innerSideDrawStart.x, innerSideDrawStart.y, innerSideDrawEnd.x, innerSideDrawEnd.y);

        Coordinate outSideStructureStart = new Coordinate(params.outer() * -1, 0);
        Coordinate outSideStructureEnd = new Coordinate(params.outer() * -1, 5300);
        Point outSideDrawStart = worldToScreen(outSideStructureStart);
        Point outSideDrawEnd = worldToScreen(outSideStructureEnd);

        context.g2d.drawLine(outSideDrawStart.x, outSideDrawStart.y, outSideDrawEnd.x, outSideDrawEnd.y);

    }

    private void drawSideAllowed(double side, int sign) {
        double BOTTOM = 2000;
        double TOP = 3000;

        assert sign == 1 || sign == -1: "sign должен быть 1 или -1, получено: " + sign;


        Coordinate start = new Coordinate(sign * side, BOTTOM);
        Coordinate end = new Coordinate(sign * side, TOP);
        Point dStart = worldToScreen(start);
        Point dEnd = worldToScreen(end);

        context.g2d.setColor(Color.GREEN);
        context.g2d.drawLine(dStart.x, dStart.y, dEnd.x, dEnd.y);
    }

    private static class RenderContext {
        Graphics2D g2d;
        Envelope envelope;
        double scale;
        double offX;
        double offY;

        public RenderContext(Graphics2D g2d, Envelope envelope, double scale, double offX, double offY) {
            this.g2d = g2d;
            this.envelope = envelope;
            this.scale = scale;
            this.offX = offX;
            this.offY = offY;
        }
    }
}
