package com.weredev.ktorUtils.exception

class NetworkException(val code: Int,val description: String = "") : Exception() {
}