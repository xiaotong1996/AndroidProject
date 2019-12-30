package com.example.androidtd.AuthenticationFragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.edit
import com.example.androidtd.Activities.MainActivity
import com.example.androidtd.Data.SignUpForm

import com.example.androidtd.R
import com.example.androidtd.SHARED_PREF_TOKEN_KEY
import com.example.androidtd.network.Api
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class SignupFragment : Fragment() {

    private val coroutineScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_signup, container, false)


        view?.findViewById<Button>(R.id.signup_button)?.setOnClickListener {

            if(signup_firstname.text.any()&&signup_lastname.text.any()&&signup_email.text.any()&&signup_password.text.any()
                &&signup_password.text.toString().equals(signup_password_confirmation.text.toString())) {

                coroutineScope.launch {
                    val token = Api.INSTANCE.userService.signUp(
                        SignUpForm(signup_firstname.text.toString(),
                            signup_lastname.text.toString(),
                            signup_email.text.toString(),
                            signup_password.text.toString(),
                            signup_password_confirmation.text.toString())
                    )

                    Log.e("token", token.body()?.token?:"no token")
                    if(token.body()?.token!=null){
                        PreferenceManager.getDefaultSharedPreferences(context).edit {
                            putString(SHARED_PREF_TOKEN_KEY, token.body()?.token)
                            val createMainActivity  = Intent(context, MainActivity::class.java)
                            startActivity(createMainActivity)
                        }
                    }else{
                        Toast.makeText(context, "text", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
