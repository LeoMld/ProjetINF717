package com.example.projetinf717.ui.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.projetinf717.AppActivity
import com.example.projetinf717.Application
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentLoginBinding
import com.example.projetinf717.databinding.FragmentRegisterBinding
import com.example.projetinf717.ui.login.LoginViewModel
import android.widget.EditText




class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var name: EditText
    private lateinit var mail: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        btnRegister = binding.btnRegister
        btnRegister.isEnabled = false
        password = binding.etPassword
        repassword= binding.etRepassword
        mail = binding.etEmail
        name = binding.etName

        password.addTextChangedListener(textWatcher)
        repassword.addTextChangedListener(textWatcher)
        mail.addTextChangedListener(textWatcher)
        name.addTextChangedListener(textWatcher)



        registerViewModel.getAction().observe(viewLifecycleOwner,
            { action -> action?.let { handleAction(it) } })
        

        btnRegister.setOnClickListener {
            registerViewModel.userWantToRegister(name.text.toString(),mail.text.toString(),password.text.toString(),repassword.text.toString())
        }

        return root
    }


    private fun handleAction(action: Action) {
        when (action.value) {
            Action.REGISTERED -> {
                Toast.makeText(context,"Account created", Toast.LENGTH_SHORT).show();
            }
            Action.INVALID_ARGUMENTS -> {
                Toast.makeText(context,"Bad arguments try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            checkFieldsForEmptyValues()
        }

        override fun afterTextChanged(editable: Editable) {}
    }
    private fun checkFieldsForEmptyValues() {
        val s1: String = name.getText().toString()
        val s2: String = mail.getText().toString()
        val s3: String = password.getText().toString()
        val s4: String = repassword.getText().toString()
        btnRegister.isEnabled = s1.isNotEmpty() && s2.isNotEmpty() && s3.isNotEmpty() && s4.isNotEmpty()
    }


}