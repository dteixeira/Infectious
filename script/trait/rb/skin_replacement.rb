class SkinReplacement < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Skin Replacement"
    description              "A new technique that mitigates severe necrosis problems." 
    activationCost           25
    deactivationCost         20
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.06
    changeInfectiousness     -0.02
    changeNotoriety          -0.04
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.06
    changeInfectiousness     0.02
    changeNotoriety          0.04
  end

end

# Add object to the traits list
SkinReplacement.new.add