import java.net.URL
import java.net.URLConnection
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object FileDownloader {

    fun downloadFile(fileUrl: String) {
        var os: OutputStream? = null
        var `is`: InputStream? = null
        val userDir = System.getProperty("user.dir")
        val outputPath = "${userDir}\\downloaded.mp3"
        try {
            // create a url object 
            val url = URL(fileUrl)
            // connection to the file 
            val connection: URLConnection = url.openConnection()
            // get input stream to the file 
            `is` = connection.getInputStream()
            // get output stream to download file 
            os = FileOutputStream(outputPath)
            val b = ByteArray(2048)
            var length: Int
            // read from input stream and write to output stream 
            while (`is`.read(b).also { length = it } != -1) {
                os.write(b, 0, length)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            // close streams 
            if (os != null) os.close()
            if (`is` != null) `is`.close()
        }
    }
}