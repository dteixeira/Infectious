class KidneyFailure < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Kidney Failure"
    description              "Kidney failure results in slower filtering capabilities and abnormal levels of" <<
                             " acids and minerals in the body. Causes complications resulting in death for the infected." 
    activationCost           22
    deactivationCost         11
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
    changeDeadliness         -0.05
    changeInfectiousness     -0.00
    changeNotoriety          -0.00
  end

end

# Add object to the traits list
KidneyFailure.new.add