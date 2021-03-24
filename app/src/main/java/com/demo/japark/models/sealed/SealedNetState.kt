package com.demo.japark.models.sealed

sealed class SealedNetState {

    object Available : SealedNetState()
    sealed class NotAvailable : SealedNetState() {
        object Lost : NotAvailable()
        object UnAvailable : NotAvailable()
    }

}