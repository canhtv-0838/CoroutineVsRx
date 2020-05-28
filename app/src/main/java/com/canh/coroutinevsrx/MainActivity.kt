package com.canh.coroutinevsrx;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.canh.coroutinevsrx.util.log
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController = navController)

//        compareThreadVsCoroutines()
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.navHostFragment).navigateUp()

    fun repeatByThread() {
        repeat(10000) {
            thread(start = true) {
                log("By Thread : $it")
            }
        }
    }

    fun repeatByCoroutines() = runBlocking {
        repeat(10000) {
            launch {
                log("By Coroutines : $it")

            }
        }
    }

    /**
     * Prove coroutine is a light-weight thread
     * coroutine does not blocking thread, by comment repeatByThread/repeatByCoroutines
     */
    fun compareThreadVsCoroutines() {
        val threadTimeMeasure = measureTimeMillis {
            repeatByThread()
        }

        val coroutineTimeMeasure = measureTimeMillis {
            repeatByCoroutines()
        }

        log("Compare Thread vs Coroutines : ${threadTimeMeasure} <> ${coroutineTimeMeasure}")
    }
}
