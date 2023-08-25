package wtf.speech.shared.core.domain.usecases

abstract class CoroutineUseCase<in Input, out Output> {

    abstract suspend operator fun invoke(input: Input): Output

}

abstract class UseCase<in Input, out Output> {

    abstract operator fun invoke(input: Input): Output

}