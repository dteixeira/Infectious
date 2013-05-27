class Blindness < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Blindness"
    description              "Blindness is the lack of visual perception due to physiological or neurological complications." <<
                             " It's a very noticeable symptom." 
    activationCost           4
    deactivationCost         10
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.00
    changeInfectiousness     0.00
    changeNotoriety          0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.00
    changeInfectiousness     -0.00
    changeNotoriety          -0.05
  end

end

# Add object to the traits list
Blindness.new.add