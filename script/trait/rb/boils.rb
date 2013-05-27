class Boils < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Boils"
    description              "Boils are caused by inflamed hair follicles causing dead tissue to accumulate" <<
                             " in the inflamed area. It's very noticeable and allows infected people to quickly expose others" <<
                             " to the disease."
    activationCost           8
    deactivationCost         5
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.00
    changeInfectiousness     0.04
    changeNotoriety          0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.00
    changeInfectiousness     -0.04
    changeNotoriety          -0.03
  end

end

# Add object to the traits list
Boils.new.add