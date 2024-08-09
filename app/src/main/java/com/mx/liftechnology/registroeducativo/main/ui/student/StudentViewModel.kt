package com.mx.liftechnology.registroeducativo.main.ui.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelStudentForm
import com.mx.liftechnology.registroeducativo.model.usecase.StudentUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class StudentViewModel (
    private val studentUseCase: StudentUseCase
) : ViewModel() {
    private val coroutine = CoroutineScopeManager()

    private val _studentName = MutableLiveData<String>()
    val studentName: LiveData<String> get() = _studentName

    private val _studentLastName = MutableLiveData<String>()
    val studentLastName: LiveData<String> get() = _studentLastName

    private val _studentSecondLastName = MutableLiveData<String>()
    val studentSecondLastName: LiveData<String> get() = _studentSecondLastName

    private val _studentPhone = MutableLiveData<String>()
    val studentPhone: LiveData<String> get() = _studentPhone

    private val _studentCurp = MutableLiveData<String>()
    val studentCurp: LiveData<String> get() = _studentCurp

    private val _studentBirthday = MutableLiveData<String>()
    val studentBirthday: LiveData<String> get() = _studentBirthday


    // Función para limpiar el texto basado en un patrón Regex
    fun cleanText(position: Int, input: String): String {
        val patron = Regex("^[A-ZÁÉÍÓÚÑa-záéíóúñ ]$")
        val patronCurp = Regex("^[A-Z0-9]$")
        val patronNumber = Regex("^[0-9]$")

        return when(position){
            3 -> {validateText(input,patronNumber)}
            4 -> {validateText(input,patronCurp)}
            else -> { validateText(input,patron)}
        }

    }
    private fun validateText(input: String, patron: Regex): String {
        return input.replace(Regex("\\s+"), " ") // Reemplaza múltiples espacios por uno
            .replace(Regex("^\\s+"), "")
            .filter { patron.matches(it.toString()) } // Filtra caracteres no válidos
    }

     fun saveData(data: ModelStudentForm) {
         coroutine.scopeIO.launch {
             data.birthday?.takeIf { it.size >= 3 }?.let { birthday ->
                 val age = restarFechas(birthday[0], birthday[1], birthday[2])
                 val studentData = StudentEntity(
                     idCurp = data.curp,
                     name = data.name,
                     lastName = data.lastName,
                     secondLastName = data.secondLastName,
                     dateBirthday = ("${data.birthday[0]}/${data.birthday[1]}/${data.birthday[2]}"),
                     age = age.toInt(),
                     listNumber = 0,
                     phoneNumber = data.phoneNumber,
                 )
                 studentUseCase.insertStudent(studentData)
                 _studentBirthday.postValue(age.toString())
             } ?: run {
                 // Manejar el caso en que `birthday` es `null` o no tiene al menos 3 elementos
                 println("Fecha de cumpleaños no válida")
             }
         }

    }

    fun restarFechas(day1: Int, month1: Int, year1: Int): Long {
        // Convertir las fechas de DatePicker a LocalDate
        val fecha1 = LocalDate.of(year1, month1 + 1, day1) // +1 porque el mes en DatePicker es 0-based
        val fecha2 = LocalDate.of(2024, 9, 1)

        // Calcular la diferencia en días
        return ChronoUnit.DAYS.between(fecha1, fecha2)
    }
}