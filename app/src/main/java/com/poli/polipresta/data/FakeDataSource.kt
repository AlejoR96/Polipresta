package com.poli.polipresta.data

import com.poli.polipresta.model.User

object FakeDataSource {
    // Usuarios de prueba
    val mockUsers = listOf(
        User("123", "123", "Freddy Herrera"),
        User("0987654321", "claveSegura", "Jair Rivera")
    )

    // Datos simulados del usuario actual
    var currentUser: User? = null
    var currentDebt: Double = 750000.00

    val loanHistory = mutableListOf<Int>()
}