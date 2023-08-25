package wtf.speech.vault.crypto.domain.models.transaction
sealed class TransactionFee {

    data class Choosable(
        val minimum: Fee,
        val normal: Fee,
        val priority: Fee,
    ) : TransactionFee()

    data class Single(
        val normal: Fee,
    ) : TransactionFee()
}