package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.R
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.dto.UserDto
import g54516.hirehub.database.entity.User
import g54516.hirehub.database.repository.DeveloperRepository
import g54516.hirehub.database.repository.UserRepository
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.model.Utils.isEmailValid
import g54516.hirehub.model.Utils.isPasswordSame
import g54516.hirehub.model.Utils.isPhoneNumberBelgian
import kotlinx.coroutines.launch
import java.util.Date

class RegisterViewModel(
    private val database: UserDao,
    application: Application
) : AndroidViewModel(application) {

    private val application = application

    private var _userRepository: UserRepository

    private var _developerRepository: DeveloperRepository

    private var _domain: String

    private var _experience: String

    private var _avatar: String

    private var _gender: String

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

    private var _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private var _isRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean>
        get() = _isRegistered

    private var _isDeveloper = MutableLiveData<Boolean>()
    val isDeveloper: LiveData<Boolean>
        get() = _isDeveloper

    init {
        _displayToast.value = false
        _isRegistered.value = false
        _userRepository = UserRepository()
        _developerRepository = DeveloperRepository()
        _avatar = "default_avatar_male"
        _gender = ""
        _domain = ""
        _experience = ""
    }

    fun register(
        email: String,
        firstname: String,
        lastname: String,
        password: String,
        confirmPassword: String,
        phone: String
    ) {
        _displayToast.value = true
        if (isFormCompleted(email, firstname, lastname, password, confirmPassword, phone)) {
            AuthService.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _notification.value =
                        application.getString(R.string.signup_successful_notification)
                    _isRegistered.value = true
                    AuthService.signIn(email, password)
                    if (_isDeveloper.value == true) {
                        insertDeveloper(
                            DeveloperDto(
                                email, firstname, lastname,
                                _domain, _experience, _gender,
                                phone.toInt(), 1, _avatar
                            ), Date()
                        )
                    } else {
                        insertUser(
                            UserDto(
                                email, firstname, lastname, _gender,
                                phone.toInt()
                            ), Date()
                        )
                    }
                } else {
                    _notification.value =
                        application.getString(R.string.signup_unsuccessful_notification)
                }
            }
        }
    }

    fun setDisplayToast(isDisplayed: Boolean) {
        _displayToast.value = isDisplayed
    }

    fun setDeveloper(isDeveloper: Boolean) {
        _isDeveloper.value = isDeveloper
    }

    fun setGender(gender: String) {
        _gender = gender
    }

    fun setDomain(domain: String) {
        _domain = domain
    }

    fun setAvatar(avatar: String) {
        _avatar = avatar
    }

    fun setExperience(experience: String) {
        _experience = experience
    }

    private fun insertUser(user: UserDto, date: Date) {
        viewModelScope.launch {
            _userRepository.addUser(user)
            database.insert(
                User(
                    email = user.email,
                    firstName = user.firstName, lastName = user.lastName,
                    gender = user.gender, phoneNumber = user.phoneNumber, date = date
                )
            )
        }
    }

    private fun insertDeveloper(developer: DeveloperDto, date: Date) {
        viewModelScope.launch {
            _developerRepository.addDeveloper(developer)
            database.insert(
                User(
                    email = developer.email, firstName = developer.firstName,
                    lastName = developer.lastName, gender = developer.gender,
                    phoneNumber = developer.phoneNumber, date = date
                )
            )
        }
    }

    private fun isFormCompleted(
        email: String,
        firstname: String,
        lastname: String,
        password: String,
        confirmPassword: String,
        phone: String
    ): Boolean {
        val errorMessage = when {
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()
                    || firstname.isEmpty() || lastname.isEmpty() ->
                R.string.form_not_completed

            !isEmailValid(email) -> R.string.signin_invalide_email
            !isPasswordSame(password, confirmPassword) -> R.string.signup_password_not_same
            !isPhoneNumberBelgian(phone) -> R.string.signup_phone_number
            _gender.isEmpty() -> R.string.signup_gender_unsuccessful
            _isDeveloper.value == null -> R.string.signup_role_unsuccessful
            else -> null
        }
        errorMessage?.let {
            _notification.value = application.getString(it)
            return false
        }
        return true
    }

}