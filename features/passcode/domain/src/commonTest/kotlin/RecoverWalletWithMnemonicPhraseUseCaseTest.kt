
import kotlinx.coroutines.runBlocking
import wtf.speech.features.wallet.domain.generator.WalletFactory
import wtf.speech.features.wallet.domain.models.blockchains.BitcoinBlockchainProvider
import wtf.speech.features.wallet.domain.usecases.RecoverWalletWithMnemonicPhraseUseCase
import kotlin.test.assertEquals

class RecoverWalletWithMnemonicPhraseUseCaseTest {

    // A mock wallet factory that returns a wallet with the same blockchain and private key as the input
    private val mockWalletFactory = WalletFactory.create(BitcoinBlockchainProvider)

    // The use case under test
    private val useCase = RecoverWalletWithMnemonicPhraseUseCase(mockWalletFactory)

    // A valid mnemonic phrase and passphrase
    private val mnemonic = listOf(
        "abandon", "abandon", "abandon", "abandon", "abandon", "abandon", "abandon", "abandon", "abandon", "abandon", "abandon", "about"
    )

    private val passphrase = "TREZOR"

    // The expected private key for the mnemonic and passphrase
    private val expectedPrivateKey =
        "xprv9s21ZrQH143K2SKJ4XQyWzJLbXzELJq7u4ngt1ZngG7oWZ7Qv7Qx9atLbEtC5ek9kuvA9ZQpREZ12KXfxsHLLJZS5rFz8LQNf4sME1TP1d"

//    @Test
    fun `invoke should return a wallet with the correct blockchain and private key`() = runBlocking {
        // Given a blockchain
        val blockchain = BitcoinBlockchainProvider.get(false)

        // When invoking the use case with the blockchain, mnemonic and passphrase
        val wallet = useCase(RecoverWalletWithMnemonicPhraseUseCase.Param(blockchain, mnemonic, passphrase))

        // Then the wallet should have the same blockchain and private key as the expected ones
        assertEquals(blockchain, wallet.blockchain)
//        assertEquals(expectedPrivateKey, wallet.)
    }
}
