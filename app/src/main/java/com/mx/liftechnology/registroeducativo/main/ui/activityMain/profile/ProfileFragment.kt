package com.mx.liftechnology.registroeducativo.main.ui.activityMain.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mx.liftechnology.registroeducativo.databinding.FragmentProfileBinding
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.includeButton.btnAction.setOnClickListener {
            if(profileViewModel.closeSession()){
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
    }

}