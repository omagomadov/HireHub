package g54516.hirehub.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.R
import g54516.hirehub.database.HireHubDB
import g54516.hirehub.databinding.FragmentLoginBinding
import g54516.hirehub.model.LoginViewModel
import g54516.hirehub.model.LoginViewModelFactory
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginButton.setOnClickListener {
            saveEmail()
        }

        // Get the application context.
        val application = requireNotNull(this.activity).application

        // Get instance of UserDAO.
        val database = HireHubDB.getInstance(application).userDao
        // Get instance of LoginViewModel.
        val viewModelFactory = LoginViewModelFactory(database, application)

        // Initialize the LoginViewModel.
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        val loginView = binding.loginEmail

        binding.lifecycleOwner = this

        viewModel.isEmailValid.observe(viewLifecycleOwner, Observer { isEmailValid ->
            if (viewModel.displayToast.value == true) {
                toast = if (isEmailValid) {
                    Toast.makeText(activity, "Valid email !", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(activity, "Invalid email !", Toast.LENGTH_SHORT)
                }
                toast.show()
                // Hide 'virtual keyboard' after finished
                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        })

        viewModel.emails.observe(viewLifecycleOwner, Observer { list ->
            var adapter = ArrayAdapter(application,
                android.R.layout.simple_dropdown_item_1line, list)
            loginView.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    private fun saveEmail() {
        viewModel.setDisplayToast(true)
        viewModel.checkEmail(binding.loginEmail.text.toString(), Date())
        viewModel.setDisplayToast(false)
    }

}