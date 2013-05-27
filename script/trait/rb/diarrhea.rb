class Diarrhea < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Diarrhea"
    description              "Frequent loose bowel movements often caused by gastroenteritis. It can" <<
                             " cause severe dehydration and increase the chances of infecting other people."
    activationCost           8
    deactivationCost         4
    type                     :virus
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         0.03
    changeInfectiousness     0.03
    changeNotoriety          0.03
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         -0.03
    changeInfectiousness     -0.03
    changeNotoriety          -0.03
  end

end

# Add object to the traits list
Diarrhea.new.add