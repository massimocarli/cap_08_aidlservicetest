package uk.co.massimocarli.aidlservicetest

import android.os.SystemClock
import android.util.Log
import java.util.concurrent.atomic.AtomicLong

class ChronoServiceImpl : ChronoService.Stub() {

  companion object {
    const val TAG = "ChronoService"
  }

  private val chrono = Chrono()

  class Chrono : Runnable {
    @Volatile
    private var running: Boolean = false
    private var thread: Thread? = null
    private val currentChronoTime = AtomicLong()
    private var mLastMeasuredTime: Long = 0

    var time: Long
      get() = currentChronoTime.get()
      set(value) {
        currentChronoTime.set(value)
      }

    fun start() {
      Log.d(TAG, "Chrono Started!")
      if (!running) {
        time = 0L
        mLastMeasuredTime = SystemClock.uptimeMillis();
        running = true
        thread = Thread(this).apply {
          start()
        }
      }
    }

    fun stop() {
      if (running) {
        running = false
        thread = null
        Log.d(TAG, "Chrono Stopped!")
      }
    }

    override fun run() {
      while (running) {
        Thread.sleep(100)
        val now = SystemClock.uptimeMillis()
        currentChronoTime.addAndGet(now - mLastMeasuredTime);
        mLastMeasuredTime = now
        Log.d(TAG, "Chrono -> $time")
      }
    }
  }

  override fun start() = chrono.start()

  override fun stop() = chrono.stop()

  override fun reset() = setTime(0L)

  override fun setTime(time: Long) {
    chrono.time = time
  }

  override fun getTime(): Long = chrono.time
}