class AtaxiaCure < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Ataxia Cure"
    description              "A new kind of medicine that repairs cerebellum damage and prevents ataxia." 
    activationCost           7
    deactivationCost         4
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.04
    changeInfectiousness     -0.00
    changeNotoriety          -0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.04
    changeInfectiousness     0.00
    changeNotoriety          0.03
  end

end

# Add object to the traits list
AtaxiaCure.new.add