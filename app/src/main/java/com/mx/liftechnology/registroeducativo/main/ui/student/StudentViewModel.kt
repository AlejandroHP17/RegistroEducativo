package com.mx.liftechnology.registroeducativo.main.ui.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelStudentForm
import com.mx.liftechnology.registroeducativo.model.usecase.StudentUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class StudentViewModel (
    private val studentUseCase: StudentUseCase
) : ViewModel() {
    private val coroutine = CoroutineScopeManager()

    private val _insertData = SingleLiveEvent<Boolean>()
    val insertData: LiveData<Boolean> get() = _insertData

    private val _listStudents = SingleLiveEvent<List<StudentEntity>>()
    val listStudents: LiveData<List<StudentEntity>> get() = _listStudents


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
                 try{
                     studentUseCase.insertStudent(studentData)
                     _insertData.postValue(true)
                 }catch (e:Exception){
                     _insertData.postValue(false)
                 }

             } ?: run {
                 // Manejar el caso en que `birthday` es `null` o no tiene al menos 3 elementos
                 _insertData.postValue(false)
             }
         }

    }

    fun editData(data: ModelStudentForm) {
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
                try{
                    studentUseCase.insertStudent(studentData)
                    _insertData.postValue(true)
                }catch (e:Exception){
                    _insertData.postValue(false)
                }

            } ?: run {
                // Manejar el caso en que `birthday` es `null` o no tiene al menos 3 elementos
                _insertData.postValue(false)
            }
        }

    }

    private fun restarFechas(day1: Int, month1: Int, year1: Int): Long {
        // Convertir las fechas de DatePicker a LocalDate
        val fecha1 = LocalDate.of(year1, month1 + 1, day1) // +1 porque el mes en DatePicker es 0-based
        val fecha2 = LocalDate.of(2024, 9, 1)

        // Calcular la diferencia en días
        return ChronoUnit.DAYS.between(fecha1, fecha2)
    }

    fun getData(){
        coroutine.scopeIO.launch {
            studentUseCase.getAllStudents().also {
                _listStudents.postValue(it)
            }
        }
    }
}