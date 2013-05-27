class PulmonaryEdema < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Pulmonary Edema"
    description              "Consists in the build-up of fluids in the human lungs." <<
                             " Serious health concern, and also increases the chance of exposing others to the disease. "
    activationCost           9
    deactivationCost         5
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.04
    changeInfectiousness     0.05
    changeNotoriety          0.01
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.04
    changeInfectiousness     -0.05
    changeNotoriety          -0.01
  end

end

# Add object to the traits list
PulmonaryEdema.new.add