package g54516.hirehub.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.R
import g54516.hirehub.database.HireHubDB
import g54516.hirehub.databinding.FragmentRegisterBinding
import g54516.hirehub.model.factories.RegisterViewModelFactory
import g54516.hirehub.model.viewmodel.RegisterViewModel
import g54516.hirehub.ui.activities.MainActivity

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_register, container, false)

        val application = requireNotNull(this.activity).application

        val database = HireHubDB.getInstance(application).userDao

        val viewModelFactory = RegisterViewModelFactory(database, application)

        val domainSpinner = binding.domainSpinner
        val experienceSpinner = binding.experienceSpinner

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.domain_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                domainSpinner.adapter = adapter
            }
        }

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.experience_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                experienceSpinner.adapter = adapter
            }
        }

        domainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setDomain(p0?.getItemAtPosition(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.setDomain(p0?.getItemAtPosition(0).toString())
            }

        }

        experienceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setExperience(p0?.getItemAtPosition(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.setExperience(p0?.getItemAtPosition(0).toString())
            }
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        binding.fragment = this

        viewModel.notification.observe(viewLifecycleOwner) {
            if (viewModel.displayToast.value == true) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                viewModel.setDisplayToast(false)
            }
        }

        viewModel.isRegistered.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
        }

        viewModel.isDeveloper.observe(viewLifecycleOwner) {
            if (it) {
                binding.developerInformation.visibility = View.VISIBLE
            } else {
                binding.developerInformation.visibility = View.GONE
            }
        }

        binding.registerButton.setOnClickListener {
            signUp()
        }

        return binding.root
    }

    fun onRadioButtonGenderClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.gender_male ->
                    if (checked) {
                        activity?.let {
                            viewModel.setGender(it.getString(R.string.radio_button_male))
                            viewModel.setAvatar("default_avatar_male")
                        }
                    }

                R.id.gender_female ->
                    if (checked) {
                        activity?.let {
                            viewModel.setGender(it.getString(R.string.radio_button_female))
                            viewModel.setAvatar("default_avatar_female")
                        }
                    }
            }
        }
    }

    fun onRadioButtonRoleClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.user_radio ->
                    if (checked) {
                        viewModel.setDeveloper(false)
                    }

                R.id.developer_radio ->
                    if (checked) {
                        viewModel.setDeveloper(true)
                    }
            }
        }
    }

    private fun signUp() {
        viewModel.register(
            binding.inputRegisterMail.text.toString(),
            binding.inputFirstName.text.toString(),
            binding.inputLastName.text.toString(),
            binding.inputRegisterPassword.text.toString(),
            binding.inputRegisterConfirmPassword.text.toString(),
            binding.inputRegisterPhone.text.toString()
        )
    }

}