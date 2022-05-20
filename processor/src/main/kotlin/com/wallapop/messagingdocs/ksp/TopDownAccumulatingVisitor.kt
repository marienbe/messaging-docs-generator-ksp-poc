package com.wallapop.messagingdocs.ksp

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSCallableReference
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSClassifierReference
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSDeclarationContainer
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSParenthesizedReference
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSPropertyGetter
import com.google.devtools.ksp.symbol.KSPropertySetter
import com.google.devtools.ksp.symbol.KSReferenceElement
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSTypeParameter
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.visitor.KSDefaultVisitor

abstract class TopDownAccumulatingVisitor<TData, TResult>(
    private val defaultResult: TResult,
    private val operator: CombineOperator<TResult, TResult>
) : KSDefaultVisitor<TData, TResult>() {
    private fun Sequence<KSNode>.accept(data: TData): TResult {
        return map { it.accept(this@TopDownAccumulatingVisitor, data) }.reduceOrNull(operator) ?: defaultResult
    }

    private fun List<KSNode>.accept(data: TData): TResult {
        return map { it.accept(this@TopDownAccumulatingVisitor, data) }.reduceOrNull(operator) ?: defaultResult
    }

    override fun defaultHandler(node: KSNode, data: TData) = defaultResult

    private fun KSNode.accept(data: TData): TResult = accept(this@TopDownAccumulatingVisitor, data)

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: TData): TResult {
        return combine {
            add(property.type.accept(data))
            add(property.extensionReceiver?.accept(data))
            add(property.getter?.accept(data))
            add(property.setter?.accept(data))
            add(super.visitPropertyDeclaration(property, data))
        }
    }

    override fun visitAnnotated(annotated: KSAnnotated, data: TData): TResult {
        return combine {
            add(annotated.annotations.accept(data))
            add(super.visitAnnotated(annotated, data))
        }
    }

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: TData): TResult {
        return combine {
            add(classDeclaration.superTypes.accept(data))
            add(super.visitClassDeclaration(classDeclaration, data))
        }
    }

    override fun visitDeclaration(declaration: KSDeclaration, data: TData): TResult {
        return combine {
            add(declaration.typeParameters.accept(data))
            add(super.visitDeclaration(declaration, data))
        }
    }

    override fun visitDeclarationContainer(declarationContainer: KSDeclarationContainer, data: TData): TResult {
        return combine {
            add(declarationContainer.declarations.accept(data))
            add(super.visitDeclarationContainer(declarationContainer, data))
        }
    }

    override fun visitAnnotation(annotation: KSAnnotation, data: TData): TResult {
        return combine {
            add(annotation.annotationType.accept(data))
            add(annotation.arguments.accept(data))
            add(super.visitAnnotation(annotation, data))
        }
    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: TData): TResult {
        return combine {
            add(function.extensionReceiver?.accept(data))
            add(function.parameters.accept(data))
            add(function.returnType?.accept(data))
            add(super.visitFunctionDeclaration(function, data))
        }
    }

    override fun visitCallableReference(reference: KSCallableReference, data: TData): TResult {
        return combine {
            add(reference.functionParameters.accept(data))
            add(reference.receiverType?.accept(data))
            add(reference.returnType.accept(data))
            add(super.visitCallableReference(reference, data))
        }
    }

    override fun visitParenthesizedReference(reference: KSParenthesizedReference, data: TData): TResult {
        return combine {
            add(reference.element.accept(data))
            add(super.visitParenthesizedReference(reference, data))
        }
    }

    override fun visitPropertyGetter(getter: KSPropertyGetter, data: TData): TResult {
        return combine {
            add(getter.returnType?.accept(data))
            add(super.visitPropertyGetter(getter, data))
        }
    }

    override fun visitPropertySetter(setter: KSPropertySetter, data: TData): TResult {
        return combine {
            add(setter.parameter.accept(data))
            add(super.visitPropertySetter(setter, data))
        }
    }

    override fun visitReferenceElement(element: KSReferenceElement, data: TData): TResult {
        return combine {
            add(element.typeArguments.accept(data))
            add(super.visitReferenceElement(element, data))
        }
    }

    override fun visitTypeAlias(typeAlias: KSTypeAlias, data: TData): TResult {
        return combine {
            add(typeAlias.type.accept(data))
            add(super.visitTypeAlias(typeAlias, data))
        }
    }

    override fun visitTypeArgument(typeArgument: KSTypeArgument, data: TData): TResult {
        return combine {
            add(typeArgument.type?.accept(data))
            add(super.visitTypeArgument(typeArgument, data))
        }
    }

    override fun visitTypeParameter(typeParameter: KSTypeParameter, data: TData): TResult {
        return combine {
            add(typeParameter.bounds.accept(data))
            add(super.visitTypeParameter(typeParameter, data))
        }
    }

    override fun visitTypeReference(typeReference: KSTypeReference, data: TData): TResult {
        return combine {
            add(typeReference.element?.accept(data))
            add(super.visitTypeReference(typeReference, data))
        }
    }

    override fun visitClassifierReference(reference: KSClassifierReference, data: TData): TResult {
        return combine {
            add(reference.qualifier?.accept(data))
            add(super.visitClassifierReference(reference, data))
        }
    }

    override fun visitValueParameter(valueParameter: KSValueParameter, data: TData): TResult {
        return combine {
            add(valueParameter.type.accept(data))
            add(super.visitValueParameter(valueParameter, data))
        }
    }

    override fun visitFile(file: KSFile, data: TData): TResult {
        return combine {
            add(visitAnnotated(file, data))
            add(visitDeclarationContainer(file, data))
            add(super.visitFile(file, data))
        }
    }

    private fun combine(block: Combiner<TResult, TResult>.() -> Unit) = combine(defaultResult, operator, block)
}
