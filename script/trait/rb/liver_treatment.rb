class LiverTreatment < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Liver Treatment"
    description              "A new and revolutionary medicine that repairs and reverts any liver" <<
                             "damage and prevents liver failure."
    activationCost           20
    deactivationCost         10
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.05
    changeInfectiousness     -0.00
    changeNotoriety          -0.02
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.05
    changeInfectiousness     0.00
    changeNotoriety          0.02
  end

end

# Add object to the traits list
LiverTreatment.new.add