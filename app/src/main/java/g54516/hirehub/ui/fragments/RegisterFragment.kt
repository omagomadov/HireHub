package g54516.hirehub.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        viewModel.notification.observe(viewLifecycleOwner, Observer { message ->
            if (viewModel.displayToast.value == true) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                viewModel.setDisplayToast(false)
            }
        })

        viewModel.isRegistered.observe(viewLifecycleOwner, Observer { isRegistered ->
            if (isRegistered) {
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
        })

        binding.registerButton.setOnClickListener {
            signUp()
        }

        return binding.root
    }

    private fun signUp() {
        val gender = when (val checkedButton = binding.genderRadioGroup.checkedRadioButtonId) {
            -1 -> ""
            else -> binding.root.findViewById<RadioButton>(checkedButton).text.toString()
        }
        viewModel.register(
            binding.inputRegisterMail.text.toString(),
            binding.inputFirstName.text.toString(),
            binding.inputLastName.text.toString(),
            binding.inputRegisterPassword.text.toString(),
            binding.inputRegisterConfirmPassword.text.toString(),
            binding.inputRegisterPhone.text.toString(),
            gender
        )
    }

}