class BowelRegulator < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Bowel Regulator"
    description              "Drugs and natural products that renovate and replace the bowel flora, preventing" <<
                             " dehydration and stopping diarrhea."
    activationCost           8
    deactivationCost         4
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.03
    changeInfectiousness     -0.03
    changeNotoriety          -0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.03
    changeInfectiousness     0.03
    changeNotoriety          0.03
  end

end

# Add object to the traits list
BowelRegulator.new.add