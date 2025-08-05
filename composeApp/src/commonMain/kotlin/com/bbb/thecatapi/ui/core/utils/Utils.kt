package com.bbb.thecatapi.ui.core.utils

fun String.isEmail(): Boolean {
    //val emailRegex = "^[a-z0-9+_.-]+@[a-z0-9.-]+\\.[a-z0-9]+\$"
    val emailRegex = "^[a-zA-Z0-9]+([._%+-][a-zA-Z0-9]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+\$"
    return this.matches(emailRegex.toRegex())
}

/**
Explicación de la expresión regular:
(?=.*[a-z]) → al menos una minúscula.
(?=.*[A-Z]) → al menos una mayúscula.
(?=.*\\d) → al menos un número.
(?=.*[^A-Za-z\\d]) → al menos un símbolo (no letra ni número).
[A-Za-z\\d\\W]{8,} → longitud mínima 8, acepta letras, números y símbolos
 */
fun String.isValidPassword(): Boolean {
    val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\W]{8,}$"
    return this.matches(passwordRegex.toRegex())
}
