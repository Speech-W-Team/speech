package wtf.speech.features.crypto.domain.models.new

import wtf.speech.vault.crypto.domain.models.new.CurveType

abstract class Blockchain(
    val id: String,
    val fullName: String,
    val baseUrl: String,
    val currency: String,
    val decimals: Int,
    val curveType: CurveType,
    val isTestnet: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Blockchain) return false

        if (id != other.id) return false
        if (fullName != other.fullName) return false
        if (baseUrl != other.baseUrl) return false
        if (currency != other.currency) return false
        if (decimals != other.decimals) return false
        if (curveType != other.curveType) return false
        return isTestnet == other.isTestnet
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + baseUrl.hashCode()
        result = 31 * result + currency.hashCode()
        result = 31 * result + decimals
        result = 31 * result + curveType.hashCode()
        result = 31 * result + isTestnet.hashCode()
        return result
    }
}

abstract class BlockchainProvider {

    abstract fun get(isTestnet: Boolean = false): Blockchain
}