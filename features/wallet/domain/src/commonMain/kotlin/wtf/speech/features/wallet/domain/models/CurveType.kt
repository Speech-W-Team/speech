package wtf.speech.features.wallet.domain.models

/**
 * Enumeration of cryptographic curve types commonly used in cryptocurrencies.
 *
 * @property curveName The name representation of the curve (e.g., "secp256k1").
 *
 * @see <a href="https://en.wikipedia.org/wiki/Elliptic-curve_cryptography">Elliptic-curve cryptography</a>
 */
enum class CurveType(val curveName: String) {
    SECP256K1("secp256k1"),
    SECP256R1("secp256r1")
    // Add other curves as necessary
}