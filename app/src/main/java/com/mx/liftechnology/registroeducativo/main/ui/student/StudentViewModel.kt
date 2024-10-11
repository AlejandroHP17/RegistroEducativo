package com.mx.liftechnology.registroeducativo.main.ui.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.data.model.StudentEntity
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.core.model.ModelStudentForm
import com.mx.liftechnology.domain.usecase.StudentUseCase
import com.mx.liftechnology.core.util.ModelRegex
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/** StudentViewModel - Control the data of the student
 * @author pelkidev
 * @since 1.0.0
 * @param studentUseCase Access to UseCase with DI
 */
class StudentViewModel(
    private val studentUseCase: StudentUseCase
) : ViewModel() {

    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Help to know when the student was save, and post a notification
    private val _insertData = SingleLiveEvent<Boolean>()
    val insertData: LiveData<Boolean> get() = _insertData

    // List of the students in the back
    private val _listStudents = SingleLiveEvent<List<StudentEntity>>()
    val listStudents: LiveData<List<StudentEntity>> get() = _listStudents

    /** cleanText - Clean text with Regex
     * @author pelkidev
     * @since 1.0.0
     * @param position what the input text is called
     * @param input the text to validate
     * @return the correct text
     */
    fun cleanText(position: Int, input: String): String {
        return when (position) {
            3 -> {
                validateText(input, ModelRegex.PHONE_NUMBER)
            }

            4 -> {
                validateText(input, ModelRegex.CURP)
            }

            else -> {
                validateText(input, ModelRegex.SIMPLE_TEXT)
            }
        }
    }

    /** validateText - check the string, and if found space replace, also with regex
     * @author pelkidev
     * @since 1.0.0
     * @param input the text to validate
     * @param patron is the type of regex
     * @return the validate text
     */
    private fun validateText(input: String, patron: Regex): String {
        return input.replace(Regex("\\s+"), " ") // Replace multiple space
            .replace(Regex("^\\s+"), "")
            .filter { patron.matches(it.toString()) } //Filter characters with regex
    }

    /** saveData - structure the information and then, send the data
     * @author pelkidev
     * @since 1.0.0
     * @param data the information that belong to UI
     */
    fun saveData(data: ModelStudentForm) {
        coroutine.scopeIO.launch {
            /* Verify the birthday is complete, and the fill the object in order to send */
            data.birthday?.takeIf { it.size >= 3 }?.let { birthday ->
                val age = restDate(birthday[0], birthday[1], birthday[2])
                val studentData = StudentEntity(
                    idCurp = data.curp,
                    name = data.name,
                    lastName = data.lastName,
                    secondLastName = data.secondLastName,
                    dateBirthday = ("${birthday[0]}/${birthday[1]}/${birthday[2]}"),
                    age = age.toInt(),
                    listNumber = 0,
                    phoneNumber = data.phoneNumber,
                )
                try {
                    studentUseCase.insertStudent(studentData)
                    _insertData.postValue(true)
                } catch (e: Exception) {
                    _insertData.postValue(false)
                }

            } ?: run {
                /* In case of mistake , post a false */
                _insertData.postValue(false)
            }
        }
    }

    /** editData - Edit the student
     * @author pelkidev
     * @since 1.0.0
     * @param data the information that belong to UI
     */
    fun editData(data: ModelStudentForm) {
        coroutine.scopeIO.launch {
            data.birthday?.takeIf { it.size >= 3 }?.let { birthday ->
                val age = restDate(birthday[0], birthday[1], birthday[2])
                val studentData = StudentEntity(
                    idCurp = data.curp,
                    name = data.name,
                    lastName = data.lastName,
                    secondLastName = data.secondLastName,
                    dateBirthday = ("${birthday[0]}/${birthday[1]}/${birthday[2]}"),
                    age = age.toInt(),
                    listNumber = 0,
                    phoneNumber = data.phoneNumber,
                )
                try {
                    studentUseCase.insertStudent(studentData)
                    _insertData.postValue(true)
                } catch (e: Exception) {
                    _insertData.postValue(false)
                }

            } ?: run {
                // Manejar el caso en que `birthday` es `null` o no tiene al menos 3 elementos
                _insertData.postValue(false)
            }
        }

    }

    /** restDate - Rest dates in format of dates and obtain the age
     * @author pelkidev
     * @since 1.0.0
     * @param day of the birthday
     * @param month of the birthday
     * @param year of the birthday
     * @return the age
     */
    private fun restDate(day: Int, month: Int, year: Int): Long {
        /* Convert the date and make the operation to get the age */
        val fecha1 = LocalDate.of(year, month + 1, day) // +1 because DatePicker is 0-based
        val fecha2 = LocalDate.of(2024, 9, 1)
        return ChronoUnit.DAYS.between(fecha1, fecha2)
    }

    /** getData - Bring all the students from the back
     * @author pelkidev
     * @since 1.0.0
     */
    fun getData() {
        coroutine.scopeIO.launch {
            studentUseCase.getAllStudents().also {
                _listStudents.postValue(it)
            }
        }
    }
}