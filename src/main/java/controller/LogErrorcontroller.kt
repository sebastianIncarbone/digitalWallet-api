package com.unqUi.api.controller

import java.util.*

class LogErrorcontroller {
    var listaDeErrores : MutableList<IllegalArgumentException> = mutableListOf()
    var listaDeNullPointerException : MutableList<NullPointerException> = mutableListOf()

    fun log(err: IllegalArgumentException){
        listaDeErrores.add(err)
        println("error: ===============>")
        println(Date().toLocaleString())
        println("-----------------------")
        println(err.message!!)
        println("<=====================>")
    }

    fun logNullPointerException(err: NullPointerException){
        listaDeNullPointerException.add(err)

        println("error: ===============>")
        println(Date().toLocaleString())
        println("-----------------------")
        println(err)
        println("<=====================>")
    }
}
