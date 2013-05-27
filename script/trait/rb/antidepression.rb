class Antidepression < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Antidepressant"
    description              "Anti-depressive medicine are drugs used for the treatment of depression." 
    activationCost           7
    deactivationCost         2
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.02
    changeInfectiousness     -0.02
    changeNotoriety          -0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.02
    changeInfectiousness     0.02
    changeNotoriety          0.03
  end

end

# Add object to the traits list
Antidepression.new.add