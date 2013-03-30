package edu.infectious.script.trait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public abstract class Trait {
	
	private static final String TRAIT_PATH = "script/trait/rb/";
	private static final String TRAIT_UTILS_PATH = "script/trait/utils.rb";
	private static ArrayList<Trait> traitList = new ArrayList<Trait>();

	private boolean inEffect;
	private int activationCost;
	private int deactivationCost;
	private TraitType type;
	private String name;
	private String description;
	
	public Trait() {
		inEffect = false;
		activationCost = 0;
		deactivationCost = 0;
		name = "";
		description = "";
		type = TraitType.VIRUS;
	}
	
	private void resetTrait() {
		inEffect = false;
	}
	
	public static void resetTraits() {
		for(Trait t : traitList) {
			t.resetTrait();
		}
	}
	
	public static void initTrait() {
		// Load script engine
		ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");
		
		// Compile scripts
		try {
			jruby.eval(new FileReader(new File(TRAIT_UTILS_PATH)));
			File folder = new File(TRAIT_PATH);
			System.err.println("Loading traits...");
			for(File file : folder.listFiles()) {
				if(file.getName().endsWith(".rb")) {
					FileReader reader = new FileReader(file);
					jruby.eval(reader);
					reader.close();
					System.err.println(" * " + traitList.get(traitList.size() - 1).getName());
				}
			}
		} catch (ScriptException | IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
	}
	
	public abstract void applyEffect();
	
	public abstract void removeEffect();

	public boolean isInEffect() {
		return inEffect;
	}

	public void setInEffect(boolean inEffect) {
		this.inEffect = inEffect;
	}

	public int getActivationCost() {
		return activationCost;
	}

	public void setActivationCost(int activationCost) {
		this.activationCost = activationCost;
	}

	public int getDeactivationCost() {
		return deactivationCost;
	}

	public void setDeactivationCost(int deactivationCost) {
		this.deactivationCost = deactivationCost;
	}

	public static ArrayList<Trait> getTraitList() {
		return traitList;
	}

	public TraitType getType() {
		return type;
	}

	public void setType(TraitType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
