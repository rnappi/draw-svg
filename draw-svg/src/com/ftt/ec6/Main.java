package com.ftt.ec6;

import java.awt.Point;
import java.io.IOException;

import com.ftt.ec6.dol.DOLSystem;
import com.ftt.ec6.dol.Polyline;
import com.ftt.ec6.dol.SVGBuilder;

public class Main {

	public static void main(String[] args) {
		DOLSystem dol = new DOLSystem();
		
		try {
			dol.loadFromFile(System.getProperty("user.dir") + "\\file.txt");
			//System.out.println(dol.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SVGBuilder svg = new SVGBuilder(dol.render(), dol.getStepSize(), dol.getAngle());
		svg.export("ddd", "ewewe");
	}
}