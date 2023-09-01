package wtf.speech.vault.crypto.domain.usecases

import wtf.speech.shared.core.domain.usecases.CoroutineUseCase
import wtf.speech.vault.crypto.domain.models.blockchains.Blockchain
import wtf.speech.vault.crypto.domain.models.Wallet

abstract class GenerateWalletUseCase(
    private val walletFactory: WalletFactory
) : CoroutineUseCase<GenerateWalletUseCase.Param, Wallet>() {
    override suspend fun invoke(input: Param): Wallet {
        return walletFactory.createWallet(input.blockchain)
    }

    data class Param(val blockchain: Blockchain)
}