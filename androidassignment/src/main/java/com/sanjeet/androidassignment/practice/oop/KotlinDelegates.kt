package com.sanjeet.androidassignment.practice.oopimport kotlin.properties.Delegates/**A “Delegate” is just a class that provides the value of a property and handles its changes. Don’t Reinvent the Wheel, Delegate It!*/fun main() {}class KotlinDelegates {    //The lazy delegate allows the value of a property to be computed only on first access and then cache    val heavy by lazy {        HeavyClass()    }    // The observable delegate allows for a lambda to be triggered any time the value of the property changes.    var name by Delegates.observable(null) { _, oldValue, newValue ->        println("Old name $oldValue")        println("New name $newValue")    }    /*    Suppose a scenario, where you want to intercept the assignment of a variable value.        Like you have the condition until and unless        it is satisfied the value of the field will not get changed.*/    var age by Delegates.vetoable(18) { _, oldValue, newValue ->        println("Old age $oldValue")        println("New age $newValue")        newValue > 18    }}/*Other Delegate As of Kotlin 1.4, it is also possible to delegate directly to another property. For example, if we are renaming a property in an API class, we may leave the old one in place and simply delegate it to the new one. To delegate property to another property, use the :: the qualifier in the delegate name, for example, this::delegate or MyClass::delegate.*/class UseCase {    var newAPI: String = ""    var API: String by this::newAPI}class HeavyClass {    init {        println("Heavy class init")    }}