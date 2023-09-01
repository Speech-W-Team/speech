package wtf.speech.vault.crypto.domain.models.transaction

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.datetime.LocalDateTime
import wtf.speech.vault.crypto.domain.models.Amount
import wtf.speech.vault.crypto.domain.models.AmountType

/**
 * Represents the data for a blockchain transaction.
 *
 * @param amount The transaction's amount details.
 * @param fee The fee associated with this transaction, if any.
 * @param sourceAddress The source address from where the funds are being sent.
 * @param destinationAddress The destination address to which the funds are being sent.
 * @param status The current status of the transaction. Defaults to [TransactionStatus.Unconfirmed].
 * @param date The date and time when the transaction occurred, if known.
 * @param hash The unique hash associated with the transaction, if available.
 * @param extras Any additional data related to the transaction that doesn't fit in the predefined fields.
 */
data class Transaction(
    val amount: Amount,
    val fee: Fee?,
    val sourceAddress: String,
    val destinationAddress: String,
    var status: TransactionStatus = TransactionStatus.Unconfirmed,
    var date: LocalDateTime? = null,
    var hash: String? = null,
    val extras: TransactionExtras? = null,
) {
    /**
     * Contract address associated with the transaction.
     * Only present if the amount type is a token.
     */
    val contractAddress: String? = (amount.type as? AmountType.AmountToken)?.token?.contractAddress
}

/**
 * Enum to represent the possible statuses of a transaction.
 */
enum class TransactionStatus {
    Confirmed,
    Unconfirmed
}

/**
 * Enum to represent possible errors that can occur during a transaction process.
 */
enum class TransactionError {
    AmountExceedsBalance,
    AmountLowerExistentialDeposit,
    FeeExceedsBalance,
    TotalExceedsBalance,
    InvalidAmountValue,
    InvalidFeeValue,
    DustAmount,
    DustChange,
    TezosSendAll,
}

/**
 * Enum to define the direction of the transaction.
 */
enum class TransactionDirection {
    Incoming,
    Outgoing
}

/**
 * Represents the basic transaction data without extra details.
 *
 * @param balanceDif Change in the balance due to the transaction.
 * @param hash Unique hash associated with the transaction.
 * @param date Date and time when the transaction occurred.
 * @param isConfirmed Boolean flag indicating if the transaction is confirmed.
 * @param destination The address to which funds were sent.
 * @param source The address from which funds were sent.
 */
data class BasicTransactionData(
    val balanceDif: BigDecimal,
    val hash: String,
    val date: LocalDateTime?,
    val isConfirmed: Boolean,
    val destination: String = "unknown",
    val source: String = "unknown",
)

/**
 * Interface for providing extra data related to a transaction.
 * Implementations of this interface can be added to [Transaction.extras] to provide more context.
 */
interface TransactionExtras

