class CystsCure < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Cysts Medication"
    description              "A new drug that causes internal cysts to disappear and be expelled by the blood flow." 
    activationCost           8
    deactivationCost         4
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.03
    changeInfectiousness     -0.02
    changeNotoriety          -0.00
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.03
    changeInfectiousness     0.02
    changeNotoriety          0.00
  end

end

# Add object to the traits list
CystsCure.new.add