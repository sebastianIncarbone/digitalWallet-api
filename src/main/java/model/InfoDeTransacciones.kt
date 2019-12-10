package com.unqUi.api.model

import java.time.LocalDateTime

class InfoDeTransacciones(amount: Double, datetime: LocalDateTime?,description: String, fullDescription: String, cashOut: Boolean) {
    var amount: Double = amount
    var dateTime: LocalDateTime? = datetime
    var description : String = description
    var fullDescription : String = fullDescription
    var cashOut : Boolean = cashOut
}
