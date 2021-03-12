package com.maciel.murillo.musales.core.extensions

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow


@ExperimentalCoroutinesApi
fun Query.snapshotAsFlow() = callbackFlow<QuerySnapshot> {

    val registration = addSnapshotListener { querySnapshot, exception ->
        exception?.let {
            close(it)
        }
        if (querySnapshot == null) {
            close()
        }
        querySnapshot?.let {
            sendBlocking(it)
        }
    }

    awaitClose {
        registration.remove()
    }
}

//suspend inline fun <reified T : Any> Task<T>.await(): T {
//
//    return suspendCancellableCoroutine { continuation ->
//        addOnSuccessListener { continuation.resume(it) }
//        addOnFailureListener { continuation.resumeWithException(it) }
//        addOnCanceledListener { continuation.cancel() }
//    }
//}

@ExperimentalCoroutinesApi
fun DocumentReference.snapshotAsFlow() = callbackFlow<DocumentSnapshot> {

    val registration = addSnapshotListener { documentSnapshot, exception ->
        exception?.let {
            close(it)
        }
        if (documentSnapshot == null) {
            close()
        }
        documentSnapshot?.let {
            sendBlocking(it)
        }
    }

    awaitClose {
        registration.remove()
    }
}