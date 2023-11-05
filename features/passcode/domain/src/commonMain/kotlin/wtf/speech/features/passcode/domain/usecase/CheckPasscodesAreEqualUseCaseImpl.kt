package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.usecases.UseCase

interface CheckPasscodesAreEqualUseCase :
    UseCase<CheckPasscodesAreEqualUseCase.Params, Boolean> {
    class Params(val currentPasscode: List<Int>, val enteredPasscode: List<Int>)
}

fun provideCheckPasscodesAreEqualUseCase(): CheckPasscodesAreEqualUseCase {
    return CheckPasscodesAreEqualUseCaseImpl()
}

internal class CheckPasscodesAreEqualUseCaseImpl : CheckPasscodesAreEqualUseCase {

    override fun invoke(input: CheckPasscodesAreEqualUseCase.Params): Boolean {
        return input.currentPasscode deepEqualTo input.enteredPasscode
    }

    private infix fun <T> Collection<T>.deepEqualTo(other: Collection<T>): Boolean {
        // check collections aren't same
        if (this !== other) {
            // fast check of sizes
            if (this.size != other.size) return false
            val areNotEqual = this.asSequence()
                .zip(other.asSequence())
                // check this and other contains same elements at position
                .map { (fromThis, fromOther) -> fromThis == fromOther }
                // searching for first negative answer
                .contains(false)
            if (areNotEqual) return false
        }
        // collections are same or they are contains same elements with same order
        return true
    }
}
