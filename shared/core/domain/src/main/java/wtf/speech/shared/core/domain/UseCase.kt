package wtf.speech.shared.core.domain

abstract class CoroutineUseCase<in Input, out Output> {

    abstract suspend fun execute(input: Input): Output

}

abstract class UseCase<in Input, out Output> {

    abstract fun execute(input: Input): Output

}