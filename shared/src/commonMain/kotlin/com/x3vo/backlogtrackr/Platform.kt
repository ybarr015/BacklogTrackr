package com.x3vo.backlogtrackr

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform