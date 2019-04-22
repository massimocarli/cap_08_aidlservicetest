package uk.co.massimocarli.aidlservicetest

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ChronoBoundService : Service() {

  private lateinit var chronoService: ChronoServiceImpl

  override fun onCreate() {
    super.onCreate()
    chronoService = ChronoServiceImpl()
  }

  override fun onBind(intent: Intent?): IBinder? = chronoService

  override fun onDestroy() {
    chronoService.stop()
    super.onDestroy()
  }
}