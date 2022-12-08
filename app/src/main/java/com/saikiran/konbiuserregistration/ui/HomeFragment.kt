package com.saikiran.konbiuserregistration.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.saikiran.konbiuserregistration.PreferenceHelper
import com.saikiran.konbiuserregistration.PreferenceHelper.clientID
import com.saikiran.konbiuserregistration.PreferenceHelper.clientSecret
import com.saikiran.konbiuserregistration.PreferenceHelper.cloudURL
import com.saikiran.konbiuserregistration.R
import com.saikiran.konbiuserregistration.databinding.FragmentHomeBinding
import com.saikiran.konbiuserregistration.retrofit.AccessTokenRequest
import com.saikiran.konbiuserregistration.retrofit.ApiService
import com.saikiran.konbiuserregistration.retrofit.CreateUserRequest
import com.saikiran.konbiuserregistration.retrofit.RetrofitHelper
import kotlinx.coroutines.*
import org.json.JSONObject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val CUSTOM_PREF_NAME = "user_settings"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.btnSave?.setOnClickListener {
            if (validate()) {
                registerUser()
            }
        }

        _binding?.etEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                _binding?.txtEmail?.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        _binding?.etCcwid1?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                _binding?.txtCcwid1?.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        _binding?.btnSettings?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }

        _binding?.cardUser?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                lifecycleScope.launch {
                    _binding?.txtCcwid1?.visibility = View.VISIBLE
                    _binding?.txtEmail?.visibility = View.GONE
                    _binding?.desc2?.visibility = View.VISIBLE
                    _binding?.txtUserName?.visibility = View.VISIBLE
                    _binding?.btnSave?.visibility = View.VISIBLE
                    _binding?.txtCcwid1A?.visibility = View.GONE
                    _binding?.txtCcwid1?.isErrorEnabled = false
                    _binding?.txtEmail?.isErrorEnabled = false
                    _binding?.etCcwid1?.requestFocus()
                }
            }
        }

        _binding?.webUser?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                lifecycleScope.launch {
                    _binding?.txtCcwid1?.visibility = View.GONE
                    _binding?.txtEmail?.visibility = View.VISIBLE
                    _binding?.desc2?.visibility = View.VISIBLE
                    _binding?.txtUserName?.visibility = View.VISIBLE
                    _binding?.txtCcwid1A?.visibility = View.VISIBLE
                    _binding?.btnSave?.visibility = View.VISIBLE
                    _binding?.txtEmail?.isErrorEnabled = false
                    _binding?.txtCcwid1?.isErrorEnabled = false
                    _binding?.etEmail?.requestFocus()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        _binding?.cardUser?.isChecked = false
        _binding?.webUser?.isChecked = false
        _binding?.userSettings?.clearCheck()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun registerUser() {
        try {
            val prefs = PreferenceHelper.customPreference(requireContext(), CUSTOM_PREF_NAME)
            val apiService: ApiService? = prefs.cloudURL?.let { it1 ->
                RetrofitHelper.getInstance(it1).create(ApiService::class.java)
            }

            val progressDialogFragment = ProgressDialogFragment()
            progressDialogFragment.show(childFragmentManager, ProgressDialogFragment.TAG)
            GlobalScope.launch {
                val accessTokenRequest = AccessTokenRequest(
                    grant_type = "client_credentials",
                    client_id = prefs.clientID ?: "",
                    client_secret = prefs.clientSecret ?: ""
                )

                val accessTokenResult = apiService?.getAccessToken(accessTokenRequest)
                if (accessTokenResult?.isSuccessful == true) {
                    val accessTokenRes = accessTokenResult.body().toString()
                    Log.d(HomeFragment::class.java.simpleName, accessTokenRes)
                    val jsonObject = JSONObject(accessTokenRes)
                    val accessToken = jsonObject.optString("access_token")

                    val ccwId1 = if (_binding?.webUser?.isChecked == true) {
                        if (_binding?.etCcwid1A?.text.isNullOrEmpty()) "" else _binding?.etCcwid1A?.text.toString()
                    } else {
                        if (_binding?.etCcwid1?.text.isNullOrEmpty()) "" else _binding?.etCcwid1?.text.toString()
                    }

                    val createUserBody = CreateUserRequest(
                        access_token = accessToken,
                        username = if (_binding?.etUserName?.text.isNullOrEmpty()) "" else _binding?.etUserName?.text.toString(),
                        email = if (_binding?.etEmail?.text.isNullOrEmpty()) "" else _binding?.etEmail?.text.toString(),
                        password = if (_binding?.etPassword?.text.isNullOrEmpty()) "" else _binding?.etPassword?.text.toString(),
                        user_role = if (_binding?.etUserRole?.text.isNullOrEmpty()) "" else _binding?.etUserRole?.text.toString(),
                        ccw_id1 = ccwId1,
                    )
                    Log.i("HomeFragment", "Create User Payload $createUserBody")
                    val createUserResponse = apiService.createUser(createUserBody)
                    if (createUserResponse.isSuccessful) {
                        progressDialogFragment.dismiss()
                        val createUserRes = createUserResponse.body().toString()
                        Log.d(HomeFragment::class.java.simpleName, createUserRes)
                        val createUserJson = JSONObject(createUserRes)
                        val message = createUserJson.optString("message")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            resetUI()
                        }
                    } else {
                        progressDialogFragment.dismiss()
                        val createUserErrorRes = createUserResponse.errorBody()?.string()
                        if (createUserErrorRes != null) {
                            Log.d(HomeFragment::class.java.simpleName, createUserErrorRes)
                            val createUserJson = JSONObject(createUserErrorRes)
                            val message = createUserJson.optString("message")
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    progressDialogFragment.dismiss()
                    accessTokenResult?.errorBody()?.string()?.let { it1 ->
                        Log.d(
                            HomeFragment::class.java.simpleName, it1
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun resetUI() {
        _binding?.cardUser?.isChecked = false
        _binding?.webUser?.isChecked = false
        _binding?.etCcwid1?.setText("")
        _binding?.etUserName?.setText("")
        _binding?.etEmail?.setText("")
        _binding?.etCcwid1A?.setText("")
        _binding?.txtCcwid1?.visibility = View.GONE
        _binding?.txtEmail?.visibility = View.GONE
        _binding?.desc2?.visibility = View.GONE
        _binding?.txtUserName?.visibility = View.GONE
        _binding?.txtCcwid1A?.visibility = View.GONE
        _binding?.userSettings?.clearCheck()
    }

    private fun validate(): Boolean {
        if (_binding?.cardUser?.isChecked == false && _binding?.webUser?.isChecked == false) {
            Toast.makeText(requireContext(), getText(R.string.select_user_type), Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (_binding?.cardUser?.isChecked == true && _binding?.etCcwid1?.text.isNullOrEmpty()) {
            _binding?.txtCcwid1?.isErrorEnabled = true
            _binding?.txtCcwid1?.error = getString(R.string.card_number_mandatory)
            return false
        }

        if (_binding?.webUser?.isChecked == true && _binding?.etEmail?.text.isNullOrEmpty()) {
            _binding?.txtEmail?.isErrorEnabled = true
            _binding?.txtEmail?.error = getString(R.string.email_mandatory)
            return false
        }
        return true
    }
}