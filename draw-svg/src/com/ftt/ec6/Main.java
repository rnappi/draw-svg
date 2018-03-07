package com.ftt.ec6;

import java.io.IOException;

import com.ftt.ec6.dol.DOLSystem;
import com.ftt.ec6.dol.SVGBuilder;

public class Main {

	public static void main(String[] args) {
		DOLSystem dol = new DOLSystem();
		String userDirectory = System.getProperty("user.dir");
		
		try {
			dol.loadFromFile(userDirectory + "\\dolSystem.txt");
			SVGBuilder svg = new SVGBuilder(dol.toString(), dol.getStepSize(), dol.getAngle());
			svg.writeFile(userDirectory + "\\result.svg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}