package g54516.hirehub.database.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthService {

    private val auth: FirebaseAuth = Firebase.auth

    fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun sendResetPasswordEmail(email: String): Task<Void> {
        return auth.sendPasswordResetEmail(email)
    }

    fun getCurrentUser(): String {
        return auth.currentUser?.email ?: ""
    }

    fun signOut() {
        auth.signOut()
    }

}