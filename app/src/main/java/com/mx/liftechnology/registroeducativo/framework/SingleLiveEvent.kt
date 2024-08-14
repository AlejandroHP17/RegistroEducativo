package com.mx.liftechnology.registroeducativo.framework

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    // Solo en caso de que necesites llamarlo desde un hilo secundario
    override fun postValue(value: T?) {
        pending.set(true)
        super.postValue(value)
    }

    // Método simple para llamar cuando no hay datos que necesites pasar
    fun call() {
        value = null
    }
}
