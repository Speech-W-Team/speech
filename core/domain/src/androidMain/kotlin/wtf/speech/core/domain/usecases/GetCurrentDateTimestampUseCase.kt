package wtf.speech.core.domain.usecases

import java.util.Date

actual class GetCurrentDateTimestampUseCase : UseCase<Unit, Long> {
    override fun invoke(input: Unit): Long {
        return Date().time
    }
}