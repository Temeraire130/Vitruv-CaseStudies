package mir.reactions.reactionsUMLTo5_1.umlToPcm;

import com.google.common.base.Objects;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ChangedParameterDirectionReaction extends AbstractReactionRealization {
  public ChangedParameterDirectionReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<Parameter, ParameterDirectionKind> typedChange = (ReplaceSingleValuedEAttribute<Parameter, ParameterDirectionKind>)change;
    Parameter affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    ParameterDirectionKind oldValue = typedChange.getOldValue();
    ParameterDirectionKind newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterDirectionReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterDirectionReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<Parameter, ParameterDirectionKind> relevantChange = (ReplaceSingleValuedEAttribute<Parameter, ParameterDirectionKind>)change;
    if (!(relevantChange.getAffectedEObject() instanceof Parameter)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("direction")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof ParameterDirectionKind)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof ParameterDirectionKind)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    ReplaceSingleValuedEAttribute<Parameter, ParameterDirectionKind> typedChange = (ReplaceSingleValuedEAttribute<Parameter, ParameterDirectionKind>)change;
    Parameter affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    ParameterDirectionKind oldValue = typedChange.getOldValue();
    ParameterDirectionKind newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, oldValue, newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final Parameter affectedEObject, final EAttribute affectedFeature, final ParameterDirectionKind oldValue, final ParameterDirectionKind newValue) {
    boolean _notEquals = (!Objects.equal(newValue, ParameterDirectionKind.RETURN_LITERAL));
    return _notEquals;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Parameter affectedEObject, final EAttribute affectedFeature, final ParameterDirectionKind oldValue, final ParameterDirectionKind newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeParameterDirection(affectedEObject);
    }
  }
}
