package com.example.seccraft_app.googleSignIn

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.User.DataUser
import com.example.seccraft_app.collection.User.DataUserGoogle
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

val LocalGoogleAuthUiClient = staticCompositionLocalOf<GoogleAuthUiClient> { error("GoogleAuthUiClient not provided") }

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {

    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender?{
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e:Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent:Intent): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {

            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    DataUser(
                        image = photoUrl.toString(),
                        name = displayName.toString(),
                        email = email.toString(),
                        number = phoneNumber.toString(),
                        role = "user"
                    )
                },
                errorMessage = null
            )

        }catch (e:Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut(){
        try {

        }catch (e:Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): DataUserGoogle? = auth.currentUser?.run {
        DataUserGoogle(
            id = uid,
            image = photoUrl.toString(),
            name = displayName.toString(),
            email = email.toString(),
            number = if (phoneNumber != null) phoneNumber.toString() else "nomor belum diisi",
            role = "user"
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }



}