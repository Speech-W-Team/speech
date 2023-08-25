package wtf.speech.shared.core.domain.usecases

import platform.Foundation.*

actual class GetCurrentDateTimestampUseCase: UseCase<Unit, Long>() {

    override fun invoke(input: Unit): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }
}