package com.unqUi.api.model

class Usuario(tarjetaDeCredito: String?, nombre: String?, apellido: String?, email: String, password: String, esAdmin: Boolean?, cvu: String?) {
    var tarjetaDeCredito: String? = tarjetaDeCredito
    var nombre: String? = nombre
    var apellido: String? = apellido
    var email: String = email
    var password: String = password
    var esAdmin: Boolean? = esAdmin
    var CVU: String? = cvu
}

