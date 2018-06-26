package com.bignerdranch.android.nerdmart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : NerdMartAbstractFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.fragment_login_login_button.setOnClickListener {
            val username = fragment_login_username.text.toString()
            val password = fragment_login_password.text.toString()

            mNerdMartServiceManager
                    .authenticate(username, password)
                    .subscribe({ authenticated ->
                        Toast.makeText(context, R.string.auth_success_toast, Toast.LENGTH_SHORT).show()

                        val intent = ProductsActivity.newIntent(context).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }

                        startActivity(intent)
                        activity?.finish()
                    })
        }

        return view
    }
}
