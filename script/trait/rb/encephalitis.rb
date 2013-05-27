class Vomiting < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Vomiting"
    description              "Vomiting is the expulsion of the stomach's contents through the mouth. It has" <<
                             " many causes, from stomach inflammation to brain tumors. It's a very" <<
                             " noticeable symptom."
    activationCost           6
    deactivationCost         3
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.02
    changeInfectiousness     0.03
    changeNotoriety          0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.02
    changeInfectiousness     -0.03
    changeNotoriety          -0.05
  end

end

# Add object to the traits list
Vomiting.new.add