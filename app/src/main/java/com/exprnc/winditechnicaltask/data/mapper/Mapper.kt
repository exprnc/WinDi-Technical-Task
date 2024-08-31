package com.exprnc.winditechnicaltask.data.mapper

interface Mapper<From, To> {
    fun map(from: From): To
}