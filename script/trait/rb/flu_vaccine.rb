class FluVaccine < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Flu Vaccine"
    description              "Effective against flu-like symptoms like sneazing and pulmonary edema, " <<
                             "by cleaning the respiratory ways."
    activationCost           12
    deactivationCost         6
    type                     :cure
  end

  # Defines what to do when this trait's effect
  # is applied
  def applyEffect
    changeDeadliness         -0.07
    changeInfectiousness     -0.06
    changeNotoriety          -0.06
  end
  
  # Defines what to do when this trait's effect
  # is removed
  def removeEffect
    changeDeadliness         0.07
    changeInfectiousness     0.06
    changeNotoriety          0.06
  end

end

# Add object to the traits list
FluVaccine.new.add