package com.marketai.core

sealed class Resource<T>(val data:T,val error:String? = null){
    class Success<T>( data:T):Resource<T>(data)
}
