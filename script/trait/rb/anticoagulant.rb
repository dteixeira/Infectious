class Anticoagulant < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Anticoagulant"
    description              "An anticoagulant is a drug that prevents blood from clotting, preventing things" <<
                             " such as an heart failure."
    activationCost           5
    deactivationCost         7
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.01
    changeInfectiousness     -0.00
    changeNotoriety          -0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.01
    changeInfectiousness     0.00
    changeNotoriety          0.03
  end

end

# Add object to the traits list
Anticoagulant.new.add