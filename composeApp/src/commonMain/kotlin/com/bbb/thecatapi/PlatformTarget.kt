package com.bbb.thecatapi

expect fun getCurrentPlatformTarget(): PlatformTarget

enum class PlatformTarget { Android, iOS, Desktop }

fun isDesktop() = getCurrentPlatformTarget() == PlatformTarget.Desktop
fun isAndroid() = getCurrentPlatformTarget() == PlatformTarget.Android
