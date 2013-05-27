class Hemorrhaging < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Hemorrhaging"
    description              "Loss of blood through the circulatory system. Can be internal or external," <<
                             " depending from the cause. Promotes the infection of other people via contact with" <<
                             " infected blood and poses a serious health risk for the infected."
    activationCost           22
    deactivationCost         12
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.06
    changeInfectiousness     0.05
    changeNotoriety          0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.06
    changeInfectiousness     -0.05
    changeNotoriety          -0.05
  end

end

# Add object to the traits list
Hemorrhaging.new.add