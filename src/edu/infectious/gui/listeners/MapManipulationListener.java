package edu.infectious.gui.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import edu.infectious.gui.utilities.Hexagon;
import edu.infectious.gui.windows.MapPanel;

public class MapManipulationListener implements MouseListener,
		MouseMotionListener, MouseWheelListener {

	private Point currentPoint = null;
	private Point lastPoint = null;
	private int moveX = 0;
	private int moveY = 0;
	private int currentX = 0;
	private int currentY = 0;

	public MapManipulationListener() {
		currentPoint = new Point(0, 0);
		lastPoint = new Point(0, 0);
		moveX = 0;
		moveY = 0;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MapPanel panel = (MapPanel) e.getComponent();
		if (panel.isZoomed()) {
			adjustXBoundary(panel, e.getPoint());
			adjustYBoundary(panel, e.getPoint());
			panel.setScrollX(currentX);
			panel.setScrollY(currentY);
			panel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MapPanel panel = (MapPanel) e.getComponent();
		Point p = e.getPoint();
		if (!panel.isZoomed())
			panel.getTransform().transform(e.getPoint(), p);
		else {
			try {
				panel.getTransform().transform(panel.getUnderMousePoint(), p);
				AffineTransform at = new AffineTransform();
				at.translate(-p.getX(), -p.getY());
				at.translate(panel.getUnderMousePoint().getX(), panel
						.getUnderMousePoint().getY());
				at.translate(moveX, moveY);
				at.scale(1.0, panel.getScreenRatioCorrectionFactor());
				at.inverseTransform(e.getPoint(), p);
			} catch (NoninvertibleTransformException e1) {
				e1.printStackTrace();
			}
		}
		for (Hexagon hex : panel.getGridMap()) {
			if (hex.getHexagon().contains(p)) {
				panel.setPointer(hex);
				panel.repaint();
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		MapPanel panel = (MapPanel) e.getComponent();
		if (panel.isZoomed()) {
			lastPoint = e.getPoint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		MapPanel panel = (MapPanel) e.getComponent();
		if (panel.isZoomed()) {
			adjustXBoundary(panel, e.getPoint());
			adjustYBoundary(panel, e.getPoint());
			panel.setScrollX(currentX);
			panel.setScrollY(currentY);
			moveX += (currentPoint.getX() - lastPoint.getX());
			moveY += (currentPoint.getY() - lastPoint.getY());
			panel.repaint();
		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		MapPanel panel = (MapPanel) e.getComponent();
		if (e.getWheelRotation() < 0) {
			if (!panel.isZoomed()) {
				panel.setUnderMousePoint(e.getPoint());
				panel.setScrollX(0);
				panel.setScrollY(0);
				currentPoint = e.getPoint();
				lastPoint = e.getPoint();
				moveX = 0;
				moveY = 0;
			}
			panel.setZoomed(true);
			panel.repaint();
		} else if (e.getWheelRotation() > 0) {
			if (panel.isZoomed())
				panel.setUnderMousePoint(new Point(0, 0));
			panel.setZoomed(false);
			panel.repaint();
		}
	}

	private void adjustXBoundary(MapPanel panel, Point newPoint) {
		Point p = new Point();
		int dx = (int) (newPoint.getX() - lastPoint.getX()) + moveX;
		panel.getTransform().transform(panel.getUnderMousePoint(), p);
		if (dx > 0 && p.getX() - panel.getUnderMousePoint().getX() - dx < 0)
			return;
		else if (dx < 0
				&& panel.getScreenSize().getWidth() + p.getX()
						- panel.getUnderMousePoint().getX() - dx > panel
						.getBackgroundDimensions().getWidth())
			return;
		else {
			currentPoint = new Point(newPoint.x, currentPoint.y);
			currentX = dx;
		}
	}

	private void adjustYBoundary(MapPanel panel, Point newPoint) {
		Point p = new Point();
		int dy = (int) (newPoint.getY() - lastPoint.getY()) + moveY;
		panel.getTransform().transform(panel.getUnderMousePoint(), p);
		if (dy > 0 && p.getY() - panel.getUnderMousePoint().getY() - dy < 0)
			return;
		else if (dy < 0
				&& panel.getScreenSize().getHeight() + p.getY()
						- panel.getUnderMousePoint().getY() - dy > panel
						.getBackgroundDimensions().getHeight())
			return;
		else {
			currentPoint = new Point(currentPoint.x, newPoint.y);
			currentY = dy;
		}
	}

}