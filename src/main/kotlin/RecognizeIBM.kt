import com.ibm.cloud.sdk.core.http.HttpConfigOptions
import com.ibm.cloud.sdk.core.http.HttpMediaType
import com.ibm.cloud.sdk.core.security.Authenticator
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.speech_to_text.v1.SpeechToText
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions
import java.io.File

object RecognizeIBM {

    fun recognize (audio: File): String {
        val authenticator: Authenticator = IamAuthenticator("insert your API key")
        val service = SpeechToText(authenticator)
        service.serviceUrl = "insert your URL service"
        val configOptions = HttpConfigOptions.Builder()
            .disableSslVerification(true)
            .build()
        service.configureClient(configOptions)

        val options = RecognizeOptions.Builder()
            .audio(audio)
            .contentType(HttpMediaType.AUDIO_MP3)
            .build()

        return service.recognize(options).execute().result.results[0].alternatives[0].transcript
    }
}