package tools.vitruv.applications.pcmjava.tests.pcm2java.repository;

import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder

class BasicComponentMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddBasicComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		changePcmView [
			modifySingleRepository [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]
		
		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}
	
	@Test
	def void testDeleteBasicComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				EcoreUtil.delete(component)
			]
		]
		
		validateJavaView [
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of())
		]
	}
	
	@Test
	def void testRenameBasicComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME).entityName = Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}
	
	@Test
	def void testAddTowBasicComponentAndDeleteOne() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository[
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME_SEC)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val secondComponent = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME_SEC)
				it.components__Repository -= secondComponent
			]
		]
		
		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}
}
