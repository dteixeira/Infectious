class HeartFailure < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Heart Failure"
    description              "Heart failure is a condition that impedes the structure and function of ones heart," <<
                             "  resulting in the inability to deliver a sufficient amount of blood throughout the body." <<
                             " Several health threat to the infected."
    activationCost           24
    deactivationCost         12
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.06
    changeInfectiousness     0.00
    changeNotoriety          0.00
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.06
    changeInfectiousness     -0.00
    changeNotoriety          -0.00
  end

end

# Add object to the traits list
HeartFailure.new.add