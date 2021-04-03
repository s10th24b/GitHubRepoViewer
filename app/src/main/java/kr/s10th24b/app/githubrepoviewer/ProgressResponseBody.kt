package kr.s10th24b.app.githubrepoviewer

import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

class ProgressResponseBody(private val responseBody: ResponseBody, val progressListener: ProgressListener, val observer: PublishSubject<Boolean>) : ResponseBody(){
//    private val bufferedSource = Okio.buffer(source(responseBody.source())) // Older Version
    private val bufferedSource = source(responseBody.source()).buffer() // New Version
    override fun contentLength(): Long = responseBody.contentLength()

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun source(): BufferedSource = bufferedSource

    fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)

                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                progressListener.update(totalBytesRead,responseBody.contentLength(),bytesRead == -1L, observer)
                return bytesRead
            }
        }


    }
}