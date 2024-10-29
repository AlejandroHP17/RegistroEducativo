package com.mx.liftechnology.registroeducativo.framework

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/** SingleLiveEvent - Help the variable in viewModels
 * @author pelkidev
 * @since 1.0.0
 * */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    // only in case invoke a secondary thread
    override fun postValue(value: T?) {
        pending.set(true)
        super.postValue(value)
    }
}
