class CoughSyrup < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Cough Syrup"
    description              "Really? You don't know what this is?"
    activationCost           5
    deactivationCost         2
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.05
    changeInfectiousness     -0.03
    changeNotoriety          -0.01
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.05
    changeInfectiousness     0.03
    changeNotoriety          0.01
  end

end

# Add object to the traits list
CoughSyrup.new.add