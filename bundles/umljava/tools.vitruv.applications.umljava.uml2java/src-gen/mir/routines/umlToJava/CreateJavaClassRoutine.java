package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Classifier umlClassifier, final org.emftext.language.java.classifiers.Class javaClassifier) {
      return umlClassifier;
    }
    
    public EObject getElement2(final Classifier umlClassifier, final org.emftext.language.java.classifiers.Class javaClassifier) {
      return javaClassifier;
    }
    
    public void updateJavaClassifierElement(final Classifier umlClassifier, final org.emftext.language.java.classifiers.Class javaClassifier) {
      javaClassifier.setName(umlClassifier.getName());
    }
    
    public void callRoutine1(final Classifier umlClassifier, final org.emftext.language.java.classifiers.Class javaClassifier, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaCompilationUnit(umlClassifier, javaClassifier, umlClassifier.getNamespace());
    }
  }
  
  public CreateJavaClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier umlClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlClassifier = umlClassifier;
  }
  
  private Classifier umlClassifier;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaClassRoutine with input:");
    getLogger().debug("   Classifier: " + this.umlClassifier);
    
    org.emftext.language.java.classifiers.Class javaClassifier = ClassifiersFactoryImpl.eINSTANCE.createClass();
    userExecution.updateJavaClassifierElement(umlClassifier, javaClassifier);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClassifier, javaClassifier), userExecution.getElement2(umlClassifier, javaClassifier), "");
    
    userExecution.callRoutine1(umlClassifier, javaClassifier, actionsFacade);
    
    postprocessElements();
  }
}
