package com.ftt.ec6.dol;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class Polyline {
	private ArrayList<Point> points;
	
	public Polyline() {
		super();
		points = new ArrayList<Point>();
	}
	
	public void addPoint(int x, int y) {
		points.add(new Point(x, y));
	};
	
	public String toString() {
		StringBuilder poly = new StringBuilder();
		poly.append("<polyline points=\"" + getPoints() + "\"");
		poly.append(" />");
		return poly.toString();
	}
	
	private String getPoints() {
		StringBuilder str = new StringBuilder();
		for(int i=0; i < points.size(); i++) {
			str.append(points.get(i).x + "," + points.get(i).y);
			if(i < points.size() - 1) str.append(" ");
		}
		return str.toString();
	}
}