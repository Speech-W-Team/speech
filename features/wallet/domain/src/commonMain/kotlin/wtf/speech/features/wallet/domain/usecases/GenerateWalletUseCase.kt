package wtf.speech.features.wallet.domain.usecases

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.wallet.domain.generator.WalletFactory
import wtf.speech.features.wallet.domain.models.Wallet
import wtf.speech.features.wallet.domain.models.blockchains.Blockchain
import wtf.speech.features.wallet.domain.repository.WalletRepository

abstract class GenerateWalletUseCase(
    private val walletFactory: WalletFactory,
    private val walletRepository: WalletRepository
) : CoroutineUseCase<GenerateWalletUseCase.Param, Wallet> {
    override suspend fun invoke(input: Param): Wallet {
        val wallet = walletFactory.createWallet(input.blockchain)
        walletRepository.storeWallet(wallet)

        return wallet
    }

    data class Param(val blockchain: Blockchain)
}