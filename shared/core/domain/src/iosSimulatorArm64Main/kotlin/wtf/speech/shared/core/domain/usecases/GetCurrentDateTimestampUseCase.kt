package wtf.speech.shared.core.domain.usecases

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class GetCurrentDateTimestampUseCase : UseCase<Unit, Long>() {

    override fun invoke(input: Unit): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }
}