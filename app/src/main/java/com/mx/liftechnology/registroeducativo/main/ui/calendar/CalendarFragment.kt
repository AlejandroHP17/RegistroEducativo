package com.mx.liftechnology.registroeducativo.main.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mx.liftechnology.registroeducativo.databinding.FragmentCalendarBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/** CalendarFragment - Order all the information by date
 * @author pelkidev
 * @since 1.0.0
 */
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val calendarViewModel: CalendarViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }
}