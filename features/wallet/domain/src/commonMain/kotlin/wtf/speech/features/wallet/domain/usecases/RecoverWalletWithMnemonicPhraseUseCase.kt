package wtf.speech.features.wallet.domain.usecases

import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.wallet.domain.generator.WalletFactory
import wtf.speech.features.wallet.domain.generator.mnemonicToSeed
import wtf.speech.features.wallet.domain.models.Wallet
import wtf.speech.features.wallet.domain.models.blockchains.Blockchain

class RecoverWalletWithMnemonicPhraseUseCase(
    private val walletFactory: WalletFactory
) : CoroutineUseCase<RecoverWalletWithMnemonicPhraseUseCase.Param, Wallet> {
    override suspend fun invoke(input: Param): Wallet {
        val privateKey = mnemonicToSeed()
        return walletFactory.recoverWallet(input.blockchain, PrivateKey(privateKey))
    }

    data class Param(val blockchain: Blockchain, val mnemonic: List<String>, val passphrase: String)
}