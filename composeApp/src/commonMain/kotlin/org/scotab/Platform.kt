package org.scotab

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform