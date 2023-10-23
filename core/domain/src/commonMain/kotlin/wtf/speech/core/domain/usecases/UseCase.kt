package wtf.speech.core.domain.usecases

import kotlinx.coroutines.flow.Flow

abstract class UseCase<in Input, out Output> {

    abstract operator fun invoke(input: Input): Output
}

abstract class CoroutineUseCase<in Input, out Output> {

    abstract suspend operator fun invoke(input: Input): Output
}

abstract class FlowUseCase<in Input, out Output> {

    abstract suspend operator fun invoke(input: Input): Flow<Output>
}

abstract class BiFlowUseCase<in Input, out Output> {

    abstract suspend operator fun invoke(input: Flow<Input>): Flow<Output>
}
