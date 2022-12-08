package com.saikiran.konbiuserregistration.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saikiran.konbiuserregistration.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val REQUEST_BIND_BACKEND_SERVICE_PERMISSION: Int = 101
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*
        if (PackageManagerQuery().isCardManagerAppInstalled(this)) {
            // check if app has permission to bind card service
            if (ContextCompat.checkSelfPermission(this, PERMISSION_TO_BIND_BACKEND_SERVICE) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // bind service if permission granted - see code below
                CardService.getInstance(this, object : ServiceConnection {
                    override fun onServiceConnected(
                        componentName: ComponentName,
                        iBinder: IBinder
                    ) {
                        // service connected, JSR-268 available
                        try {
                            // use terminal factory as entry point to JSR-268
                            // ex. list readers
                            terminalFactory?.terminals()?.list()
                        } catch (e: Exception) {
                            // handle exceptions
                        }
                    }

                    override fun onServiceDisconnected(componentName: ComponentName) {
                        // service disconnected
                    }
                })
            } else {
                // request permission to bind service and expect result in onRequestPermissionsResult
                ActivityCompat.requestPermissions(
                    this, arrayOf(PERMISSION_TO_BIND_BACKEND_SERVICE),
                    REQUEST_BIND_BACKEND_SERVICE_PERMISSION
                )
            }
        } else {
            // show dialog that CardReaderManager app is not installed
            Toast.makeText(
                this,
                getString(R.string.card_reader_app_is_not_installed),
                Toast.LENGTH_SHORT
            ).show()
        }*/
    }
}