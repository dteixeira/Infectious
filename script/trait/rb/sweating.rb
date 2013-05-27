class Sweating < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Sweating"
    description              "Sweating is described as being the loss of water through sweat glands in the human body. It's purpose is" <<
                             " to mantain the body temperature within the normal bounds, but can cause dehydration."
    activationCost           2
    deactivationCost         3
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.01
    changeInfectiousness     0.01
    changeNotoriety          0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.01
    changeInfectiousness     -0.01
    changeNotoriety          -0.03
  end

end

# Add object to the traits list
Sweating.new.add