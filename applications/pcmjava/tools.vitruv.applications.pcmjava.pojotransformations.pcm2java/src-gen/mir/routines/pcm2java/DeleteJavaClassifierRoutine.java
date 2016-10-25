package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaClassifierRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
      return javaClassifier;
    }
    
    public EObject getElement2(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
      return compilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaClassifier(final NamedElement sourceElement) {
      return sourceElement;
    }
    
    public EObject getCorrepondenceSourceCompilationUnit(final NamedElement sourceElement, final ConcreteClassifier javaClassifier) {
      return sourceElement;
    }
  }
  
  public DeleteJavaClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.DeleteJavaClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.sourceElement = sourceElement;
  }
  
  private NamedElement sourceElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaClassifierRoutine with input:");
    getLogger().debug("   NamedElement: " + this.sourceElement);
    
    ConcreteClassifier javaClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClassifier(sourceElement), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (javaClassifier == null) {
    	return;
    }
    initializeRetrieveElementState(javaClassifier);
    CompilationUnit compilationUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompilationUnit(sourceElement, javaClassifier), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (compilationUnit == null) {
    	return;
    }
    initializeRetrieveElementState(compilationUnit);
    deleteObject(userExecution.getElement1(sourceElement, javaClassifier, compilationUnit));
    
    deleteObject(userExecution.getElement2(sourceElement, javaClassifier, compilationUnit));
    
    postprocessElementStates();
  }
}
