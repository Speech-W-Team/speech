package wtf.speech.core.domain.usecases

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class GetCurrentDateTimestampUseCase actual constructor() :
    UseCase<Unit, Long>() {
    override fun invoke(input: Unit): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }
}