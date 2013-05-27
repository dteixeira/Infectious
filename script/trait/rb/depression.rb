class Depression < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Depression"
    description              "Low mood or lost of interest in general activities. In a deep state can lead to suicide." <<
                             " It's a fairly noticeable symptom."
    activationCost           5
    deactivationCost         7
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.01
    changeInfectiousness     0.00
    changeNotoriety          0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.01
    changeInfectiousness     -0.00
    changeNotoriety          -0.03
  end

end

# Add object to the traits list
Depression.new.add