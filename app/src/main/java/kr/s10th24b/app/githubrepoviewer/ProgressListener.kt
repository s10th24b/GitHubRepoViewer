package kr.s10th24b.app.githubrepoviewer

import io.reactivex.rxjava3.subjects.PublishSubject

interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean, observer: PublishSubject<Boolean>)
}