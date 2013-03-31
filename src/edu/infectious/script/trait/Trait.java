package edu.infectious.script.trait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public abstract class Trait {

	/*
	 * Constants
	 */
	private static final String		TRAIT_PATH			= "script/trait/rb/";
	private static final String		TRAIT_UTILS_PATH	= "script/trait/utils.rb";
	
	/*
	 * Class fields
	 */
	private static ArrayList<Trait>	traitList			= new ArrayList<Trait>();
	
	/*
	 * Instance fields
	 */
	private int						activationCost;
	private int						deactivationCost;
	private String					description;
	private boolean					inEffect;
	private String					name;
	private TraitType				type;

	/*
	 * Class methods
	 */
	public static ArrayList<Trait> getTraitList() {
		return traitList;
	}

	public static void initTrait() {
		// Load script engine
		ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");

		// Compile scripts
		try {
			jruby.eval(new FileReader(new File(TRAIT_UTILS_PATH)));
			File folder = new File(TRAIT_PATH);
			System.err.println("Loading traits...");
			for (File file : folder.listFiles()) {
				if (file.getName().endsWith(".rb")) {
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

	public static void resetTraits() {
		for (Trait t : traitList) {
			t.resetTrait();
		}
	}

	/*
	 * Constructor
	 */
	public Trait() {
		inEffect = false;
		activationCost = 0;
		deactivationCost = 0;
		name = "";
		description = "";
		type = TraitType.VIRUS;
	}

	/*
	 * Instance methods
	 */
	public abstract void applyEffect();

	public int getActivationCost() {
		return activationCost;
	}

	public int getDeactivationCost() {
		return deactivationCost;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public TraitType getType() {
		return type;
	}

	public boolean isInEffect() {
		return inEffect;
	}

	public abstract void removeEffect();

	public void setActivationCost(int activationCost) {
		this.activationCost = activationCost;
	}

	public void setDeactivationCost(int deactivationCost) {
		this.deactivationCost = deactivationCost;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setInEffect(boolean inEffect) {
		this.inEffect = inEffect;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(TraitType type) {
		this.type = type;
	}

	private void resetTrait() {
		inEffect = false;
	}

}
