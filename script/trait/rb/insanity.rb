class Insanity < TraitKlass

  def initialize
    # Calls super class constructor and resets
    # all fields
    super
    
    # Define all need values
    name                     "Insanity"
    description              "Insanity results in an individual acting in a behavior outside of the normally" <<
                             " accepted. Insane people often pose a risk to themselves and others. Insanity is noticeable, " <<
                             " but it also allows the infected to expose others to your virus. Insanity increases the chance " <<
                             " of death in the infected."
    activationCost           12
    deactivationCost         8
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
Insanity.new.add