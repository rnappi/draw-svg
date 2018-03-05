package com.ftt.ec6.dol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DOLSystem {
	private String axiom;
	private int angle;
	private int derivationSteps;
	private int currentDerivationStep;
	private HashMap<String, String> productionRules = new HashMap<String, String>();
	private int stepSize = 10;
	
	public int getStepSize() {
		return stepSize;
	}

	private void setAxiom(String axiom) {
		if(axiom.isEmpty() || axiom == null)
			throw new RuntimeException ("O termo inicial não pode ser vazio.");
		
		this.axiom = axiom;
	}
	
	public int getAngle() {
		return this.angle;
	}
	
	private void setAngle(String angle) {
		if(angle.equals(""))
			this.angle = 90;
		else
			this.angle = Integer.valueOf(angle);
	}

	private void setDerivationSteps(String derivationSteps) {
		if(derivationSteps.equals(""))
			this.derivationSteps = 0;
		else
			this.derivationSteps = Integer.valueOf(derivationSteps);
	}

	public DOLSystem() {
		super();
	}
	
	public void loadFromFile(String file) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(file));
		
		if(lines.isEmpty() || lines.size() == 0)
			throw new IOException("O arquivo txt está vazio.");
		
		this.setDerivationSteps(this.formatLine(lines.get(0))[1]);
		this.setAngle(this.formatLine(lines.get(1))[1]);
		this.setAxiom(this.formatLine(lines.get(2))[1]);
		
		lines.subList(3, lines.size()).forEach(line -> {
			this.addProductionRule(this.formatLine(line));
		});
	}
	
	public String render() {
		//https://stackoverflow.com/questions/7661460/replace-multiple-substrings-at-once
		String input = this.axiom;
		String regex = this.getRegex();
		
		while(currentDerivationStep < derivationSteps) {
			StringBuffer sb = new StringBuffer();
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(input);

			while (m.find())
			    m.appendReplacement(sb, productionRules.get(m.group()));
			m.appendTail(sb);
			
			input = sb.toString();
			currentDerivationStep ++;
		}
		return input;
	}
	
	private String[] formatLine(String line)  {
		if(line.isEmpty() || line == null)
			throw new RuntimeException ("O arquivo de texto não pode conter linhas em branco.");
		
		String[] data = line.split("=");
		if(data.length != 2 || data[0].isEmpty() || data[1].isEmpty())
			throw new RuntimeException ("Propriedade inválida: " + data.toString() + ". O formato correto é key=value.");
		
		return data;
	}
	
	private void addProductionRule(String [] rule) {
		productionRules.put(rule[0], rule[1]);
	}
	
	private String getRegex() {
		return String.join("|", productionRules.keySet());
	}
}