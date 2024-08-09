package com.mx.liftechnology.registroeducativo.main.ui.student.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentFormStudenBinding
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.main.ui.student.DatePickerDialog
import com.mx.liftechnology.registroeducativo.main.ui.student.StudentViewModel
import com.mx.liftechnology.registroeducativo.main.viewextensions.toastFragment
import com.mx.liftechnology.registroeducativo.main.viewextensions.verify
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelStudentForm
import com.mx.liftechnology.registroeducativo.model.util.ModelSelectorForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FormStudenFragment : Fragment() {

    private var _binding: FragmentFormStudenBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by sharedViewModel()

    private var arrInputs: MutableList<TextInputEditText> = mutableListOf()
    private var datePicker: DatePickerDialog? = null
    private var dates: MutableList<Int>? = null
    private var flagAfterAdd = false

    private var coroutineScopeManager = CoroutineScopeManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dates = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormStudenBinding.inflate(inflater, container, false)
        initView()
        initListeners()
        initInputText()
        initObservers()
        return binding.root
    }

    private fun initView(){
        imageCalendar()
    }

    private fun imageCalendar(){
        val etBirthday = binding.etBirthday
        val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_calendar)
        drawable?.setBounds(-8, -8, 56, 56) // Ajusta el tamaño aquí (izquierda, arriba, derecha, abajo)
        drawable?.let {
            val resizedDrawable = it.mutate()
            etBirthday.setCompoundDrawables(resizedDrawable, null, null, null)
        }
    }

    private fun initListeners(){
        binding.apply {
            etBirthday.setOnClickListener {
                datePicker = DatePickerDialog { day, month, year -> onDateSelected(day, month, year) }
                datePicker?.show(childFragmentManager, "datePicker")
            }

            btnAdd.setOnClickListener {
                coroutineScopeManager.scopeIO.launch {
                    val isValid = validateData()
                    if (isValid) {
                        withContext(Dispatchers.Main) {
                            val data = ModelStudentForm(
                                name = binding.etName.text.toString(),
                                lastName = binding.etLastName.text.toString(),
                                secondLastName = binding.etSecondLastName.text.toString(),
                                curp = binding.etCurp.text.toString(),
                                phoneNumber = binding.etPhone.text.toString().toLongOrNull(),
                                birthday = dates
                            )
                            studentViewModel.saveData(data)
                        }
                    }
                }
            }
        }
    }

    private suspend fun validateData(): Boolean = withContext(Dispatchers.IO) {
        coroutineScope {
            val results = listOf(
                async { withContext(Dispatchers.Main) { binding.etName.verify(binding.inputName, requireContext(), ModelSelectorForm.NAME) } },
                async { withContext(Dispatchers.Main) { binding.etLastName.verify(binding.inputLastName, requireContext(), ModelSelectorForm.LASTNAME) } },
                async { withContext(Dispatchers.Main) { binding.etSecondLastName.verify(binding.inputSecondLastName, requireContext(), ModelSelectorForm.SECONDLASTNAME) } },
                async { withContext(Dispatchers.Main) { binding.etPhone.verify(binding.inputPhone, requireContext(), ModelSelectorForm.PHONE) } },
                async { withContext(Dispatchers.Main) { binding.etCurp.verify(binding.inputCurp, requireContext(), ModelSelectorForm.CURP) } },
                async { withContext(Dispatchers.Main) { binding.etBirthday.verify(binding.inputBirthday, requireContext(), ModelSelectorForm.BIRTHDAY) } }
            ).awaitAll()

            flagAfterAdd = true
            return@coroutineScope results.all { it }
        }
    }

    private fun initObservers(){
        studentViewModel.insertData.observe(viewLifecycleOwner){ flag ->
            if (flag){
                toastFragment("El alumno se guardo correctamente")
                val navigation = FormStudenFragmentDirections.actionFormStudenFragmentToStudentFragment()
                findNavController().navigate(navigation)
            }else{
                toastFragment("Ha ocurrido en error al guardar al alumno")
            }
        }
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val showDay = day.toString().padStart(2, '0')
        val showMonth = month.toString().padStart(2, '0')
        val date = "$showDay/$showMonth/$year"
        dates?.add(day)
        dates?.add(month)
        dates?.add(year)
        binding.etBirthday.setText(date)
        coroutineScopeManager.scopeIO.launch { withContext(Dispatchers.Main){ binding.etBirthday.verify(binding.inputBirthday,requireContext(),ModelSelectorForm.BIRTHDAY) }}

    }


    private fun initInputText(){
        if(arrInputs.isEmpty()){
            binding.apply {
                /* Array para controlar todas las entradas de texto con un solo TextWatcher */
                arrInputs.add(etName)
                arrInputs.add(etLastName)
                arrInputs.add(etSecondLastName)
                arrInputs.add(etPhone)
                arrInputs.add(etCurp)
            }
        }
        // Agregar el TextWatcher a cada campo
        val textWatcher = createTextWatcher(arrInputs)
        arrInputs.forEach { editText ->
            editText.addTextChangedListener(textWatcher)
        }
    }
    private fun createTextWatcher(editTexts: MutableList<TextInputEditText>) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No necesitamos hacer nada aquí
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            // Limpiar el texto mientras cambia
            val currentEditText = editTexts.find { it.hasFocus() }
            // Encontrar la posición del EditText en el array
            val position = editTexts.indexOf(currentEditText)
            currentEditText?.let {
                val cleanedText = studentViewModel.cleanText(position, s.toString())
                if (s.toString() != cleanedText) {
                    it.setText(cleanedText)
                    it.setSelection(cleanedText.length)  // Mover el cursor al final
                }
            }
            if(flagAfterAdd){
                    coroutineScopeManager.scopeIO.launch {
                        when(position){
                            0 -> { withContext(Dispatchers.Main){ binding.etName.verify(binding.inputName,requireContext(),ModelSelectorForm.NAME)} }
                            1 -> { withContext(Dispatchers.Main){ binding.etLastName.verify(binding.inputLastName,requireContext(),ModelSelectorForm.LASTNAME)}}
                            2 -> { withContext(Dispatchers.Main){ binding.etSecondLastName.verify(binding.inputSecondLastName,requireContext(),ModelSelectorForm.SECONDLASTNAME)}}
                            3 -> { withContext(Dispatchers.Main){ binding.etPhone.verify(binding.inputPhone,requireContext(),ModelSelectorForm.PHONE)}}
                            4 -> { withContext(Dispatchers.Main){ binding.etCurp.verify(binding.inputCurp,requireContext(),ModelSelectorForm.CURP)}}
                            else -> { }
                        }
                    }
                
            }

        }
        override fun afterTextChanged(s: Editable?) {
            // No necesitamos hacer nada aquí
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}