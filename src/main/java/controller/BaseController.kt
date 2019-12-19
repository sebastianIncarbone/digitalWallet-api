package com.unqUi.api.controller

import com.unqUi.api.model.FormularioDeTransferencia
import com.unqUi.api.model.InfoDeTransacciones
import com.unqUi.api.model.Usuario
import wallet.Account
import wallet.DigitalWallet
import wallet.DigitalWallet.Support.generateNewCVU
import wallet.Transactional
import wallet.User

open class BaseController(dw : DigitalWallet) {
    var digitalWallet = dw

    fun logearUsuario(usuario: Usuario): User {
        var user = digitalWallet.login(usuario.email, usuario.password)

        if (user.account == null) {
            digitalWallet.assignAccount(user, Account(user, generateNewCVU()))
        }

        return user;
    }

    fun registarUsuario(usuarioNuevo: Usuario): User {
        digitalWallet.register(
            User(
                usuarioNuevo.tarjetaDeCredito!!,
                usuarioNuevo.nombre!!,
                usuarioNuevo.apellido!!,
                usuarioNuevo.email,
                usuarioNuevo.password,
                usuarioNuevo.esAdmin!!
            )
        )
        var usuario = digitalWallet.users.find { u -> u.idCard == usuarioNuevo.tarjetaDeCredito }

        digitalWallet.assignAccount(usuario!!, Account(usuario!!, generateNewCVU()))

        return usuario;
    }

    fun tansferirDinero(formulario: FormularioDeTransferencia) {
        digitalWallet.transfer(formulario.cvuFROM, formulario.cvuTO, formulario.monto)
    }

    fun ingresarPlata(formulario: FormularioDeTransferencia) {
        digitalWallet.transfer(formulario.cvuFROM, formulario.cvuTO , formulario.monto)
    }

    fun getEfectivoDe(cvu: String): Double {
        if(cvu.isEmpty()){
            return 0.0
        }
        return digitalWallet.accountByCVU(cvu).balance
    }

    fun deleteUsuarioByCVU(cvu: String) {
        digitalWallet.deleteUser(digitalWallet.users.find { usuario -> usuario.account!!.cvu.equals(cvu) }!!)
    }

    fun getTransaccionesPorCVU(cvu: String): List<InfoDeTransacciones> {

       return digitalWallet.accountByCVU(cvu).transactions.map {
            transaccion ->
           InfoDeTransacciones (
                    transaccion.amount,
                    transaccion.dateTime,
                    transaccion.description(),
                    transaccion.fullDescription(),
                    transaccion.isCashOut()
            )
        }


    }

    fun getCvu(email: String): String {
        val account : Account? = digitalWallet.users.filter { user ->  user.email == email }.first().account

        if(account != null) {
            return account.cvu
        }

        throw IllegalArgumentException("No se econtro una cuenta")
    }

    fun getPasswordByEmail(email: String): String {

        if(digitalWallet.users.isNullOrEmpty()){
            throw IllegalArgumentException("No se econtro un usuario");
        }

        val user : User = digitalWallet.users.filter { user ->  user.email == email }.firstOrNull()
            ?: throw IllegalArgumentException("No se econtro un usuario")

        if(user.password.isEmpty()) {
            throw IllegalArgumentException("No se econtro una contraseña")
        }

        return user.password
    }

    fun getCvuByCard(creditCard: String): String {
        if(digitalWallet.users.isNullOrEmpty()){
            throw IllegalArgumentException("No se econtro un usuario");
        }

        val user : User = digitalWallet.users.filter { user ->  user.idCard == creditCard }.firstOrNull()
            ?: throw IllegalArgumentException("No se econtro un usuario")

        if(user.account!!.cvu.isNullOrEmpty()) {
            throw IllegalArgumentException("No se econtro una cuenta")
        }

        return user.account!!.cvu

    }
}