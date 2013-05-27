class BoilsCure < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Boils Antibiotic"
    description              "A medicine designed to cure and prevent the appearance of boils in the human body," <<
                             " even when caused by severe diseases."
    activationCost           8
    deactivationCost         5
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.00
    changeInfectiousness     -0.04
    changeNotoriety          -0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.00
    changeInfectiousness     0.04
    changeNotoriety          0.03
  end

end

# Add object to the traits list
BoilsCure.new.add