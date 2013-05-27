class Cysts < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Cysts"
    description              "Cysts are closed sacs containing air or fluids. Once they are formed they can only " <<
                             " be removed through surgery or medication. Cysts may cause health risks depending on their location."
    activationCost           7
    deactivationCost         3
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.02
    changeInfectiousness     0.03
    changeNotoriety          0.02
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.02
    changeInfectiousness     -0.03
    changeNotoriety          -0.02
  end

end

# Add object to the traits list
Cysts.new.add