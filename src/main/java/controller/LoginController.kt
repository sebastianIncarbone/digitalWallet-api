package com.unqUi.api.controller

import com.unqUi.api.model.Usuario
import wallet.DigitalWallet
import wallet.User
import java.lang.Exception

class LoginController(dw: DigitalWallet) : BaseController(dw) {

    fun login(usuario: Usuario): User {
        require(!usuario.email.isEmpty()) { "Email incorrecto!" }
        require("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$".toRegex().matches(usuario.email)) { "Email incorrecto!" }
        require(!usuario.password.isEmpty()) {"Contraseña invalida!"}
        try {
            return super.logearUsuario(usuario)
        }catch (e: Exception) {
            throw IllegalArgumentException("No se encontro un usuario")
        }
    }

    fun register(usuarioNuevo: Usuario): User {
        require(!usuarioNuevo.tarjetaDeCredito.isNullOrEmpty()) {"Falta una tarjeta de credito"}
        require(!usuarioNuevo.nombre.isNullOrEmpty()) {"Falta un nombre"}
        require(!usuarioNuevo.apellido.isNullOrEmpty()) {"Falta un apellido"}
        require(!usuarioNuevo.email.isNullOrEmpty()){"Falta un email"}
        require("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$".toRegex().matches(usuarioNuevo.email)) { "Email incorrecto!" }
        require(!usuarioNuevo.password.isNullOrEmpty()) {"Falta una contraseña"}

        if(usuarioNuevo.esAdmin == null) {
            usuarioNuevo.esAdmin = false
        }

        return super.registarUsuario(usuarioNuevo)
    }

}
