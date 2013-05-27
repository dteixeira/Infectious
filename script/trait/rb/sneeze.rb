class Sneeze < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Sneeze"
    description              "Nausea is a sensation of unease and discomfort in the upper stomach with an involuntary urge to vomit" <<
                             " and introduces a risk of dehydration. It's a" <<
                             " fairly noticeable symptom."
    activationCost           7
    deactivationCost         2
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.03
    changeInfectiousness     0.03
    changeNotoriety          0.05
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.03
    changeInfectiousness     -0.03
    changeNotoriety          -0.05
  end

end

# Add object to the traits list
Sneeze.new.add