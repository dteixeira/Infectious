class Fatigue < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Fatigue"
    description              "Fatigue is a subjective feeling of tiredness which is distinct from weakness, and has a gradual onset." <<
                             "It is a fairly noticeable symptom and can cause death when associated with other symptoms."
    activationCost           3
    deactivationCost         5
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.03
    changeInfectiousness     0.01
    changeNotoriety          0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.03
    changeInfectiousness     -0.01
    changeNotoriety          -0.05
  end

end

# Add object to the traits list
Fatigue.new.add