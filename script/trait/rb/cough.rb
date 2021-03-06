class Cough < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Cough"
    description              "A cough will increase virus transmission among infected people," <<
                             " and also introduce a risk of respiratory passage damage. It's a" <<
                             " fairly noticeable symptom."
    activationCost           5
    deactivationCost         2
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.01
    changeInfectiousness     0.05
    changeNotoriety          0.02
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.01
    changeInfectiousness     -0.05
    changeNotoriety          -0.02
  end

end

# Add object to the traits list
Cough.new.add