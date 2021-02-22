package br.com.personal.karina.applealdesafio.data

import java.util.*

data class Exercise(
    var id:String?=null,
    var name: String = "",
    var image: String? = null,
    val date: Date = Date(),
    var observation: String= ""
)
