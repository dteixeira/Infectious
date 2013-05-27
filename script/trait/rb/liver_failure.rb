class LiverFailure < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Liver Failure"
    description              "Liver failure results in the liver being unable to perform normal synthetic" <<
                             " and metabolic function. Can easily cause death to the infected person and it's" <<
                             " fairly noticeable."
    activationCost           20
    deactivationCost         10
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.05
    changeInfectiousness     0.00
    changeNotoriety          0.02
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.05
    changeInfectiousness     -0.00
    changeNotoriety          -0.02
  end

end

# Add object to the traits list
LiverFailure.new.add