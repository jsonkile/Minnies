package com.demo.minnies.shared.utils

enum class Currency {
    USD {
        override val perDollar: Double = 1.0
        override val sign: Char = '$'
    },
    EUR {
        override val perDollar: Double = 0.94
        override val sign: Char = '€'
    },
    NGN {
        override val perDollar: Double = 445.96
        override val sign: Char = '₦'
    },
    GBP {
        override val perDollar: Double = 0.82
        override val sign: Char = '£'
    };

    abstract val perDollar: Double
    abstract val sign: Char
}