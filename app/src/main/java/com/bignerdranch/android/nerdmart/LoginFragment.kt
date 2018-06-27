package com.bignerdranch.android.nerdmart

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bignerdranch.android.nerdmart.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : NerdMartAbstractFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val fragmentLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        fragmentLoginBinding.setLoginButtonClickListener {
            val username = fragmentLoginBinding.fragmentLoginUsername.text.toString()
            val password = fragmentLoginBinding.fragmentLoginPassword.text.toString()

            addDisposable(mNerdMartServiceManager
                    .authenticate(username, password)
                    .compose(loadingTransformer())
                    .subscribe({ authenticated ->
                        Toast.makeText(context, R.string.auth_success_toast, Toast.LENGTH_SHORT).show()

                        val intent = ProductsActivity.newIntent(context).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }

                        startActivity(intent)
                        activity?.finish()
                    })
            )
        }


        return fragmentLoginBinding.root
    }
}
