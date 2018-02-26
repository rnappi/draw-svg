package com.ftt.ec6;

import java.io.IOException;

import com.ftt.ec6.dol.DOLSystem;

public class Main {

	public static void main(String[] args) {
		DOLSystem dol = new DOLSystem();
		
		try {
			dol.loadFromFile(System.getProperty("user.dir") + "\\file.txt");
			System.out.println(dol.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}