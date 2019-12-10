package com.unqUi.api.controller

import com.unqUi.api.bootstrap.Bootstrap
import com.unqUi.api.bootstrap.test

class TestController() {

    fun saludar(data: Bootstrap): test {
        return data.msg;
    }
}
