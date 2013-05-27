class Lobotomy < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Lobotomy"
    description              "A new type of non-invasive surgery of this kind that is effective against dementia and" <<
                             "other brain-related diseases"
    activationCost           7
    deactivationCost         4
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.05
    changeInfectiousness     -0.05
    changeNotoriety          -0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.05
    changeInfectiousness     0.05
    changeNotoriety          0.05
  end

end

# Add object to the traits list
Lobotomy.new.add