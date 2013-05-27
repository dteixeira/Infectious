class Fever < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Fever"
    description              "It occurs in the human body when a threat is found, resulting in an higher than normal" <<
                             " body temperature."
    activationCost           5
    deactivationCost         3
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.02
    changeInfectiousness     0.01
    changeNotoriety          0.02
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.02
    changeInfectiousness     -0.01
    changeNotoriety          -0.01
  end

end

# Add object to the traits list
Fever.new.add