class BlindnessCure < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Blindness Cure"
    description              "A revolutionary drug, found by a portuguese researcher, that reverses any blindness problems in humans." 
    activationCost           4
    deactivationCost         2
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.01
    changeInfectiousness     -0.00
    changeNotoriety          -0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.01
    changeInfectiousness     0.00
    changeNotoriety          0.05
  end

end

# Add object to the traits list
BlindnessCure.new.add