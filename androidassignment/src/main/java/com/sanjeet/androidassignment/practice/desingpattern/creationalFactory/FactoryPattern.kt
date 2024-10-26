package com.sanjeet.androidassignment.practice.desingpattern.creationalFactory/** Factory Pattern:The Factory Pattern is a creation design pattern that provides an interface for creating objects in a superclass,but it allows subclasses to alter the type of objects that will be created.It’s widely used in scenarios where a class cannot anticipate the type of object it needs to createor when a class wants to delegate responsibility for creating objects to its subclasses.*//**Example of the Factory Method PatternIn this example, we’ll use a factory method to create different types ofNotification objects (EmailNotification, SMSNotification, etc.),each implementing a common Notification interface. */fun main() {    val notificationFactory = CreateNotificationFactory()    val smsNotification = notificationFactory.createNotification("email")    smsNotification?.notifyUser()}interface Notification {    fun notifyUser()}class EmailNotification() : Notification {    init {        println("email class initiated")    }    override fun notifyUser() {        println("Email notification created")    }}class SMSNotification() : Notification {    init {        println("sms class initiated")    }    override fun notifyUser() {        println("Sms notification created")    }}class CreateNotificationFactory {    fun createNotification(type: String): Notification? {        return when (type.lowercase()) {            "email" -> EmailNotification()            "sms" -> SMSNotification()            else -> null        }    }}