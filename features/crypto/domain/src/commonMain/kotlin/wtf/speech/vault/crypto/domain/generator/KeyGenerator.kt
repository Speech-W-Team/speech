package wtf.speech.vault.crypto.domain.generator

import wtf.speech.core.domain.models.KeyPair
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey


/**
 * KeyGenerator is an interface defining the contract for cryptographic key generation.
 * It provides methods for generating both private and public keys, as well as key pairs.
 */
interface KeyGenerator {

    /**
     * Generates a pair of private and public cryptographic keys.
     *
     * @param curveType The curveType on which should be based key pair.
     * @return KeyPair - A pair containing both private and public keys.
     *
     * @see KeyPair
     * @see CurveType
     */
    suspend fun generateKeyPair(): KeyPair

    /**
     * Generates a cryptographic private key.
     *
     * @return PrivateKey - A cryptographic private key.
     *
     * @see PrivateKey
     */
    suspend fun generatePrivateKey(): PrivateKey

    /**
     * Generates a cryptographic public key based on a provided private key.
     *
     * @param privateKey The private key based on which the public key will be generated.
     * @param curveType The curveType on which should be based key pair.
     *
     * @return PublicKey - A cryptographic public key.
     *
     * @see PrivateKey
     * @see PublicKey
     * @see CurveType
     */
    suspend fun generatePublicKey(privateKey: PrivateKey): PublicKey
}
