require 'java'

java_import 'edu.infectious.script.trait.Trait'

module CModule
  # Java imported packages
  include_package 'edu.infectious.script.trait'
  include_package 'edu.infectious.logic'
end

class TraitKlass < Trait
  include CModule
  
  def name val
    self.name = val
  end
  
  def description val
    self.description = val
  end
  
  def activationCost val
    self.activationCost = val
  end
  
  def deactivationCost val
    self.deactivationCost = val
  end
  
  def type val
    self.type = CModule::PlayerType.valueOf(val.to_s.upcase)
  end
    
  def add
    Trait.getTraitList().add(self)
  end
  
  def changeDeadliness val
    CModule::VirusStatistics.applyDeadlinessVariation(val)
  end
  
  def changeNotoriety val
    CModule::VirusStatistics.applyNotorietyVariation(val)
  end
  
  def changeInfectiousness val
    CModule::VirusStatistics.applyInfectiousnessVariation(val)
  end
end

  