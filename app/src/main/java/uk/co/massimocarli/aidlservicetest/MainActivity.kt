package uk.co.massimocarli.aidlservicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  lateinit var chronoService: ChronoService
  private var bounded = false

  private val connection = object : ServiceConnection {

    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      bounded = true
      chronoService = ChronoService.Stub.asInterface(service);
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      bounded = false
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onStart() {
    super.onStart()
    Intent(this, ChronoBoundService::class.java).also { intent ->
      bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
  }

  override fun onStop() {
    super.onStop()
    unbindService(connection)
  }


  fun startChrono(view: View) {
    if (bounded) {
      chronoService.start()
    }
  }

  fun stopChrono(view: View) {
    if (bounded) {
      chronoService.stop()
    }
  }

  fun resetChrono(view: View) {
    if (bounded) {
      chronoService.reset()
    }
  }
}
