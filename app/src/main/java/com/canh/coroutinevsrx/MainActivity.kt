package com.canh.coroutinevsrx;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.canh.coroutinevsrx.util.log
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController = navController)

//        compareThreadVsCoroutines()
//        launchAndAsync()
//         handleExpWithTryCatch()
//        runSupervisorScope()
//        runScopeHierarchy()
//        cancelCoroutine()
//        handleExpWithHandler()
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
     * coroutine does not blocking thread, by comment repeatByThread/repeatByCoroutines => main safety
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

    /**
     * Compare launch vs async
     */
    fun launchAndAsync() = runBlocking {
        val a = launch {
            1 + 1
        }

        val b = async {
            1 + 1
        }
        log("${b.await()}")
    }

    /**
     * handle exception with try-catch block
     */
    fun handleExpWithTryCatch() = runBlocking {
        GlobalScope.launch {
            try {
                log("Throwing exception from launch")
                throw IndexOutOfBoundsException()
                log("Unreached")
            } catch (e: IndexOutOfBoundsException) {
                log("Caught IndexOutOfBoundsException")
            } finally {
                log("Finally I die")
            }
        }

        val deferred = GlobalScope.async {
            log("Throwing exception from async")
            throw ArithmeticException()
            log("Unreached")
        }
        try {
            deferred.await()
            log("Unreached")
        } catch (e: ArithmeticException) {
            log("Caught ArithmeticException")
        }
    }

    /**
     * handle exception with handler exception block
     */
    fun handleExpWithHandler() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            log("Caught $exception")
        }
        val job = GlobalScope.launch(handler) {
                log("Throwing exception from launch")
                throw IndexOutOfBoundsException()
            }

        val deferred = GlobalScope.async(handler){
            log("Throwing exception from async")
            throw ArithmeticException()
        }
        joinAll(job, deferred)
    }

    /**
     * supervisorScope
     * if a coroutines is canncel, other continue running
     */
    fun runSupervisorScope() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            log("Caught $exception")
        }
        supervisorScope {
            val first = launch(handler) {
                log("Child throws an exception")
                throw AssertionError()
            }
            val second = launch {
                delay(100)
                log("Scope is completing")
            }
        }
        log("Scope is completed")
        log("$handler")
    }


    /**
     * Child coroutine use parent properties
     * parent wait until all child complete
     */
    fun runScopeHierarchy() = runBlocking {
        // scope 1
        launch {
            // coroutine 1
            delay(200L)
            log("Task from runBlocking")
        }

        coroutineScope {
            // coroutine 2   // scope 2
            launch {
                // coroutine 3
                delay(500L)
                log("Task from nested launch")
            }

            delay(100L)
            log("Task from coroutine scope")
        }

        log("Coroutine scope is over")
    }

    /**
     * cancelCoroutine
     */
    fun cancelCoroutine() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    log("I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                // Tranh thủ close resource trong này đi nha :D
                log("I'm running finally")
            }
        }
        delay(1300L) // delay a bit
        log("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        log("main: Now I can quit.")
    }
}
