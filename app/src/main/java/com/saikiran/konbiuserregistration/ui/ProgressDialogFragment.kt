package com.saikiran.konbiuserregistration.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.saikiran.konbiuserregistration.R

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_alert_custom_border)
        return inflater.inflate(R.layout.fragment_dialog_progress, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState)
        //onCreateDialog.setCancelable(false)
        onCreateDialog.setCanceledOnTouchOutside(false)
        return onCreateDialog
    }

    companion object {
        const val TAG = "ProgressDialogFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.5).toInt()
        dialog?.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}