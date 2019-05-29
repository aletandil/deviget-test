package com.projecttesting.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 *
 * Scopes TL;DR:
 * "In Dagger, a component can be associated with a scope by annotating it with a @Scope annotation.
 * In that case, the component implementation holds references to all scoped objects so they can be reused.
 * Modules with @Provides methods annotated with a scope may only be installed into a
 * component annotated with the same scope."
 *
 * Subcomponents cannot have the same scope as the parent
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface FragmentScoped {
}
