class Antihemorrhaging < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Antihemorrhaging"
    description              "A new and revolutionary medicine that stops the risk of hemorrhaging to occur." 
    activationCost           25
    deactivationCost         12
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.08
    changeInfectiousness     -0.06
    changeNotoriety          -0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.08
    changeInfectiousness     0.06
    changeNotoriety          0.05
  end

end

# Add object to the traits list
Antihemorrhaging.new.add