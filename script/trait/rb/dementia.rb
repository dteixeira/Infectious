class Dementia < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Dementia"
    description              "Progressive decline in mental capacity and function due to brain damage." <<
                             " It's a fairly noticeable symptom."
    activationCost           3
    deactivationCost         6
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.01
    changeInfectiousness     0.01
    changeNotoriety          0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.01
    changeInfectiousness     -0.01
    changeNotoriety          -0.03
  end

end

# Add object to the traits list
Dementia.new.add