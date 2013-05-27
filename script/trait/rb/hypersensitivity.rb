class Hypersensitivity < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Hypersensitivity"
    description              "Hypersensitivity is a condition where the immune system begins to cause damage" <<
                             " to the bodys own cells. Increases the chance for health complications to occur in the infected."
    activationCost           7
    deactivationCost         3
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.04
    changeInfectiousness     0.01
    changeNotoriety          0.02
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.04
    changeInfectiousness     -0.01
    changeNotoriety          -0.02
  end

end

# Add object to the traits list
Hypersensitivity.new.add