class Encephalitis < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Encephalitis"
    description              "Encephalitis is acute inflammation of the brain. Brain damage occurs as" <<
                             " the brain pushes against the skull, and eventually results in death."
    activationCost           21
    deactivationCost         12
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.05
    changeInfectiousness     0.00
    changeNotoriety          0.00
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.00
    changeInfectiousness     -0.00
    changeNotoriety          -0.00
  end

end

# Add object to the traits list
Encephalitis.new.add