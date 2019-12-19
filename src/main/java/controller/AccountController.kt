package com.unqUi.api.controller

import com.unqUi.api.model.FormularioDeTransferencia
import com.unqUi.api.model.InfoDeTransacciones
import wallet.DigitalWallet

class AccountController(dw: DigitalWallet) : BaseController(dw){

    fun transferir(formulario: FormularioDeTransferencia) {
        require(!formulario.cvuTO.isEmpty()) {"Falta un CVU de destino"}
        require(!formulario.cvuFROM.isEmpty()) {"Falta tu CVU"}
        require(!(formulario.monto < 0)) {"No se puede transferir este monto"}

        super.tansferirDinero(formulario)
    }

    fun cashIn(formulario: FormularioDeTransferencia) {
        require(!formulario.cvuTO.isEmpty()) {"Falta un CVU de destino"}
        require(!formulario.cvuFROM.isEmpty()) {"Falta tu CVU"}
        require(!(formulario.monto < 0)) {"No se puede transferir este monto"}

        super.ingresarPlata(formulario)
    }

    fun getEfectivoEnLaCuenta(cvu: String): Double {
        require(!cvu.isEmpty()) {"Falta un tu CVU"}

        return super.getEfectivoDe(cvu)
    }

    fun getTransactions(cvu: String): List<InfoDeTransacciones> {
        require(!cvu.isEmpty()) {"Falta un tu CVU"}

        return super.getTransaccionesPorCVU(cvu)
    }

    fun getCvuPorMail(email : String): String {
        require(!email.isEmpty()) {"Debe tener un email para esta operacion"}

        return super.getCvu(email)
    }

    fun getPass(email: String): String {
        require(!email.isEmpty()) {"Debe tener un email para esta operacion"}

        return super.getPasswordByEmail(email);
    }

    fun getCvuByCreditCard(creditCard: String): String {
        require(!creditCard.isEmpty()) {"Debe tener una tarjeta de credito para esta operacion"}
        return super.getCvuByCard(creditCard)

    }

}
