package com.unqUi.api.controller

import com.unqUi.api.model.Usuario
import wallet.DigitalWallet

class UserController(dw: DigitalWallet) : BaseController(dw) {

    fun deleteUser(cvu: String) {
        require(!cvu.isNullOrEmpty()) {"Debe tener un CVU para esta operacion"}

        super.deleteUsuarioByCVU(cvu)
    }
}
