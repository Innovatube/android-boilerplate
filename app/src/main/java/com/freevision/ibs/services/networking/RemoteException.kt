package com.freevision.ibs.services.networking

import retrofit2.Response

class RemoteException(val response: Response<*>) : Exception()
