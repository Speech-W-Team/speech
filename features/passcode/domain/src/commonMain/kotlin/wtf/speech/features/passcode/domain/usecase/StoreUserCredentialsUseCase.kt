package wtf.speech.features.passcode.domain.usecase

import wtf.speech.core.domain.usecases.UseCase

public abstract class StoreUserCredentialsUseCase : UseCase<StoreUserCredentialsUseCase.Params, Unit> {

    override fun invoke(input: Params) {
        println("store user credentials")
    }

    public class Params
}
