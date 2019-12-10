package com.unqUi.api

import com.google.gson.Gson
import com.unqUi.api.bootstrap.Bootstrap
import com.unqUi.api.controller.AccountController
import com.unqUi.api.controller.LogErrorcontroller
import com.unqUi.api.controller.LoginController
import com.unqUi.api.controller.TestController
import com.unqUi.api.model.FormularioDeTransferencia
import com.unqUi.api.controller.UserController
import com.unqUi.api.model.InfoDeTransacciones
import com.unqUi.api.model.Usuario
import io.javalin.Javalin
import io.javalin.core.util.RouteOverviewPlugin
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    var data = Bootstrap();
    val port = 7000

    val testcontroller = TestController();
    val logErrorcontroller = LogErrorcontroller();
    val logincontroller = LoginController(data.dw);
    val userController = UserController(data.dw);
    val accountcontroller = AccountController(data.dw);

    val app = Javalin.create{
        it.defaultContentType = "application/json"
        it.registerPlugin(RouteOverviewPlugin("/route"))
        it.enableCorsForAllOrigins()
    }.start(port)

    /*
    app.before {
        if(!listaDeEndPointPublicos.contains(it.req.pathInfo) && !logincontroller.estaLogeado()) {
            throw IllegalArgumentException("Requiere logeo previamente")
        }
    }
    */

    app.exception(FileNotFoundException::class.java) { e, ctx ->
        ctx.status(404)
    }.error(404) { ctx ->
        ctx.result("No se a podido encontrar la consulta")
    }

    app.exception(NullPointerException::class.java) { e, ctx ->
        logErrorcontroller.logNullPointerException(e)
        ctx.status(500)
        ctx.result("No se a podido procesar la consulta")
    }

    app.exception(IllegalArgumentException::class.java) { e, ctx ->
        logErrorcontroller.log(e)
        ctx.status(400)//ver errores
        e.message?.let { ctx.result(it) }
    }

    app.get("/") {
        val result = testcontroller.saludar(data)
        it.json(result)
    }

    app.post("/login") {
        val login = it.body<Usuario>()
        var usuario = logincontroller.login(login)
        it.status(200)
        if(usuario.isAdmin == null) {
            usuario.isAdmin = false
        }
        it.result(Gson().toJson(Usuario(usuario.idCard,usuario.firstName,usuario.lastName,usuario.email,usuario.password,usuario.isAdmin,usuario.account!!.cvu)))
    }

    app.post("/register") {
        val usuarioNuevo = it.body<Usuario>()
        val usuario = logincontroller.register(usuarioNuevo)
        it.status(200)
        if(usuario.isAdmin == null) {
            usuario.isAdmin = false
        }
        it.result(Gson().toJson(Usuario(usuario.idCard,usuario.firstName,usuario.lastName,usuario.email,usuario.password,usuario.isAdmin,usuario.account!!.cvu)))
    }

    app.post("/transfer") {
        val formularioDeTransferencia = it.body<FormularioDeTransferencia>()
        accountcontroller.transferir(formularioDeTransferencia )
        it.status(200)
        it.result("Transferencia existosa!")
    }

    app.post("/cashIn") {
        val formulario = it.body<FormularioDeTransferencia>()
        accountcontroller.cashIn(formulario)
        it.status(200)
        it.result("Transferencia existosa!")
    }

    app.get("/account/:cvu") {
        val efectivoEnLaCuenta : Double = accountcontroller.getEfectivoEnLaCuenta(it.pathParam("cvu"))
        it.status(200)
        it.json(efectivoEnLaCuenta)
    }

    app.get("/forgetPass/:email") {
        val a = it.body()
        val email = it.pathParam("email")
        val password : String = accountcontroller.getPass(email)
        it.status(200)
        it.json(password)
    }

    app.get("/transaccions/:cvu") {
        val cvu : MutableList<InfoDeTransacciones> = accountcontroller.getTransactions(it.pathParam("cvu"))
        it.status(200)
        it.json(cvu)
    }

    app.delete("/users/:cvu") {
        userController.deleteUser(it.pathParam("cvu"))
        it.status(200)
    }

    app.get("/getCVU/:email") {
        val email = it.pathParam("email")
        val cvu : String = accountcontroller.getCvuPorMail(email)
        it.status(200)
        it.json(cvu)
    }

}