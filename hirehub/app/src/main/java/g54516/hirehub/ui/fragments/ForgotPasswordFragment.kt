package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import g54516.hirehub.R
import g54516.hirehub.databinding.FragmentForgotPasswordBinding
import g54516.hirehub.model.factories.ForgotPasswordModelFactory
import g54516.hirehub.model.viewmodel.ForgotPasswordViewModel

class ForgotPasswordFragment : Fragment() {

    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_forgot_password, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ForgotPasswordModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory)[ForgotPasswordViewModel::class.java]

        viewModel.notification.observe(viewLifecycleOwner, Observer { message ->
            if (viewModel.displayToast.value == true) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                viewModel.setDisplayToast(false)
            }
        })

        viewModel.emailSent.observe(viewLifecycleOwner, Observer { isSent ->
            if (isSent) {
                findNavController().popBackStack()
            }
        })

        binding.resetPasswordButton.setOnClickListener {
            viewModel.sendResetPasswordEmail(binding.forgotPasswordEmail.text.toString())
        }

        return binding.root
    }

}