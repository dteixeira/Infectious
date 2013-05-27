class Necrosis < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Necrosis"
    description              "Necrosis results in cell and tissue death. Cells that die due to necrosis " <<
                             " are difficult for the body to remove and recycle. poses a large risk to the health of those " <<
                             " it affects. It also allows any infected to expose others to your disease very easily. It's also very" <<
                             " noticeable due to the appearance and smell of dead flesh."
    activationCost           25
    deactivationCost         20
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.05
    changeInfectiousness     0.05
    changeNotoriety          0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.05
    changeInfectiousness     -0.05
    changeNotoriety          -0.05
  end

end

# Add object to the traits list
Necrosis.new.add