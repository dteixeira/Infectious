class Antipyretic < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Antipyretic"
    description              "A drug that prevents and minimizes fever and regulates body temperature." 
    activationCost           5
    deactivationCost         3
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.02
    changeInfectiousness     -0.01
    changeNotoriety          -0.02
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.02
    changeInfectiousness     0.01
    changeNotoriety          0.02
  end

end

# Add object to the traits list
Antipyretic.new.add