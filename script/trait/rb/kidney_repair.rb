class KidneyRepair < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Kidney Repair"
    description              "A revolutionary medicine that prevents kidney failure and restores their normal function." 
    activationCost           22
    deactivationCost         11
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.05
    changeInfectiousness     -0.00
    changeNotoriety          -0.00
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.05
    changeInfectiousness     0.00
    changeNotoriety          0.00
  end

end

# Add object to the traits list
KidneyRepair.new.add