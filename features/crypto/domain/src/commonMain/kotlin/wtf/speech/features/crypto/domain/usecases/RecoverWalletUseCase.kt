package wtf.speech.features.crypto.domain.usecases

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.crypto.domain.models.Wallet
import wtf.speech.features.crypto.domain.models.blockchains.Blockchain

abstract class RecoverWalletUseCase(
    private val walletFactory: WalletFactory
) : CoroutineUseCase<RecoverWalletUseCase.Param, Wallet>() {
    override suspend fun invoke(input: Param): Wallet {
        return walletFactory.createWallet(input.blockchain)
    }

    data class Param(val blockchain: Blockchain)
}