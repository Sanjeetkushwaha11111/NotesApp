package com.sanjeet.androidassignment.practicefun main() {//   nullable and and null safety//    var name: String? = null//    print("name is $name")//    print("name length is ${name?.length}")//    High order function//    Takes another function as a parameter, or//    Returns a function.//    println(addAndMultiply(4, 6, ::mutliply))//    lambda function//    it is anonymous  literal function which means it is not declared but passed as an expression    val divide = { a: Int, add: Int -> a / add }    println(divide(8, 2))}// higher order function accepting function as parameterfun addAndMultiply(a: Int, b: Int, multiply: (Int, Int) -> String) {    val result = multiply(a, b)    println("addition of $a $b is: ${a + b}")    println(result)}//normal functionfun mutliply(a: Int, b: Int): String {    return "multiplication of $a $b is: ${a * b}"}//normal functionfun add(a: Int, b: Int): Int {    return a + b}