package com.forhad_naim.domain.utils

object DomainConstants {
    enum class STATUS {
        ACTIVE,
        SUPER_ACTIVE,
        BORED
    }

    const val DATE_FORMAT = "yyyy-MM-dd"
    const val ACTIVE_THRESHOLD = 5
    const val SUPER_ACTIVE_THRESHOLD = 10
}