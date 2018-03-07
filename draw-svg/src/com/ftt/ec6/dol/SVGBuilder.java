package com.ftt.ec6.dol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SVGBuilder {
	private int currentX;
	private int currentY;
	private double currentAngle;
	private double stepAngle;
	private int stepSize;
	private String dolStr;
	private int initialX;
	private int initialY;
	private int finalX;
	private int finalY;
	
	private void setCurrentX(int x) {
		this.currentX = x;		
		if(this.currentX < this.initialX) 
			this.initialX = this.currentX;
		else if(this.currentX > this.finalX)
			this.finalX = this.currentX;
	}
	
	private void setCurrentY(int y) {
		this.currentY = y;
		if(this.currentY < this.initialY) 
			this.initialY = this.currentY;
		else if(this.currentY > this.finalY)
			this.finalY = this.currentY;
	}
	
	public SVGBuilder(String dolStr, int stepSize, int angle) {
		super();
		this.currentAngle = this.stepAngle = Math.toRadians(angle);
		this.stepSize = stepSize;
		this.dolStr = dolStr;
		this.initialX = this.initialY = this.finalX = this.finalY = 0;
	}
	
	public void writeFile(String filePath) throws IOException {
		System.out.println(filePath);
		
		StringBuilder svg = new StringBuilder();
		ArrayList<Polyline> polylines = getPolylines();
		svg.append(getSvgHeader());
		polylines.forEach(p -> svg.append(p.toString()));
		svg.append("</svg>");
		
		try (PrintWriter out = new PrintWriter(filePath)) {
		    out.println(svg);
		}
	}
	
	private String getSvgHeader() {
		StringBuilder header = new StringBuilder();
		header.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ");
		header.append("viewBox=\"" + this.initialX + " " + this.initialY + " " + this.getWidth() + " " + this.getHeight() + "\" ");
		header.append("preserveAspectRatio=\"xMinYMin meet\" ");
		header.append(">");
		return header.toString();
	}
	
	private int getWidth() {
		if(this.initialX <= this.finalX)
			return Math.abs(this.finalX - this.initialX);
		else
			return Math.abs(this.initialX - this.finalX);
	}

	private int getHeight() {
		if(this.initialY <= this.finalY)
			return Math.abs(this.finalY - this.initialY);
		else
			return Math.abs(this.initialY - this.finalY);
	}
	
	private ArrayList<Polyline> getPolylines() {
		ArrayList<Polyline> polylines = new ArrayList<Polyline>();
		String[] chars = this.dolStr.split("");
		String prevChar = "";
		Polyline poly = new Polyline();
		setCurrentX(0);
		setCurrentY(0);
		poly.addPoint(this.currentX, this.currentY);
		
		for(String c : chars) {
			switch (c) {
			case "F":
				if (prevChar.equals("f")) {
					polylines.add(poly);
					poly = new Polyline();
					poly.addPoint(this.currentX, this.currentY);
				}
				this.updateCurrentCoordinates();
				poly.addPoint(this.currentX, this.currentY);
				prevChar = c;
				break;
			case "f":
				this.updateCurrentCoordinates();
				prevChar = c;
				break;
			case "+":
				this.currentAngle += this.stepAngle;
				break;
			case "-":
				this.currentAngle -= this.stepAngle;
				break;
			default:
				break;
			}
		}
		polylines.add(poly);
		return polylines;
	}
	
	private void updateCurrentCoordinates() {
		setCurrentX(getNextX());
		setCurrentY(getNextY());
	}
	
	private int getNextX() {
		return this.currentX + (this.stepSize * (int)Math.cos(currentAngle));
	}
	
	private int getNextY() {
		return this.currentY - (this.stepSize * (int)Math.sin(currentAngle));
	}
}