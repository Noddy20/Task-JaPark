package com.demo.japark.models.sealed

sealed class SealedNetState {

    //To prevent livedata observer from receiving data again and again

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): SealedNetState? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            this
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): SealedNetState = this

    object Available : SealedNetState()
    sealed class NotAvailable : SealedNetState() {
        object Lost : NotAvailable()
        object UnAvailable : NotAvailable()
    }

}