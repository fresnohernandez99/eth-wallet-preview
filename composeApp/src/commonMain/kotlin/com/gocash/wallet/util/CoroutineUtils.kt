package com.gocash.wallet.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun CoroutineScope.launchWithContext(
    context: CoroutineDispatcher = Dispatchers.IO,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    func: suspend () -> Unit,
) = this.launch(context = context + Job(), start = start) {
    withContext(context + Job()) {
        func.invoke()
    }
    return@launch
}

fun CoroutineScope.launchPeriodic(
    context: CoroutineDispatcher = Dispatchers.IO,
    repeatMillis: Long = 4000,
    action: suspend () -> Unit,
) = this.launch(context + Job()) {
    while (isActive) {
        delay(repeatMillis)
        action()
    }
    return@launch
}

fun CoroutineScope.launchThrottling(
    context: CoroutineDispatcher = Dispatchers.IO,
    startPoint: Long = 4000,
    step: Long = 500,
    action: suspend () -> Unit,
) = this.launch(context + Job()) {
    var repeatMillis = startPoint

    while (isActive) {
        delay(repeatMillis)
        action()
        repeatMillis += step
    }
    return@launch
}

fun CoroutineScope.launchUntil(
    context: CoroutineDispatcher = Dispatchers.IO,
    delay: Long,
    action: () -> Unit,
) = this.launch(context + Job()) {
    delay(delay)
    action()
    return@launch
}


@OptIn(DelicateCoroutinesApi::class)
suspend fun <E> ProducerScope<E>.sendWithPrecautions(element: E) {
    try {
        if (!channel.isClosedForSend) send(element)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

