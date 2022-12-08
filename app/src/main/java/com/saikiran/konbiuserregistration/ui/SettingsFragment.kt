package com.saikiran.konbiuserregistration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saikiran.konbiuserregistration.PreferenceHelper.clientID
import com.saikiran.konbiuserregistration.PreferenceHelper.clientSecret
import com.saikiran.konbiuserregistration.PreferenceHelper.cloudURL
import com.saikiran.konbiuserregistration.PreferenceHelper.customPreference
import com.saikiran.konbiuserregistration.R
import com.saikiran.konbiuserregistration.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    val CUSTOM_PREF_NAME = "user_settings"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = customPreference(requireContext(), CUSTOM_PREF_NAME)
        _binding?.txtInputCloudEditText?.setText(prefs.cloudURL)
        _binding?.txtInputClientIdEditText?.setText(prefs.clientID)
        _binding?.txtInputClientSecretEditText?.setText(prefs.clientSecret)

        _binding?.btnSaveSync?.setOnClickListener {
            prefs.cloudURL = _binding?.txtInputCloudEditText?.text.toString()
            prefs.clientID = _binding?.txtInputClientIdEditText?.text.toString()
            prefs.clientSecret = _binding?.txtInputClientSecretEditText?.text.toString()
            Toast.makeText(requireContext(), getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
        }
    }
}