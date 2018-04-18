package com.kotlin.boilerplate.services.networking

import retrofit2.Response

class RemoteException(val response: Response<*>) : Exception()
