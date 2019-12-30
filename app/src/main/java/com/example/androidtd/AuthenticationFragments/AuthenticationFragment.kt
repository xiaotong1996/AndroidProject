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
import androidx.navigation.fragment.findNavController
import com.example.androidtd.Activities.MainActivity

import com.example.androidtd.R
import com.example.androidtd.SHARED_PREF_TOKEN_KEY

class AuthenticationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_authentication,container,false) as View
        val buttonLogin = view.findViewById<Button>(R.id.button_login)
        buttonLogin.setOnClickListener{
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }

        val buttonSignUP=view.findViewById<Button>(R.id.button_signup)
        buttonSignUP.setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }

        val TOKEN =  PreferenceManager.getDefaultSharedPreferences(context).getString(
            SHARED_PREF_TOKEN_KEY,"DEFAULT")
        Log.e("TOKEN",TOKEN)
        if(TOKEN!=null){
            val createMainActivity  = Intent(context, MainActivity::class.java)
            startActivity(createMainActivity)
        }

        return view
    }



}
