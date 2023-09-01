package wtf.speech.core.core.domain.usecases

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import wtf.speech.core.domain.usecases.UseCase

actual class GetCurrentDateTimestampUseCase : UseCase<Unit, Long>() {

    override fun invoke(input: Unit): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }
}