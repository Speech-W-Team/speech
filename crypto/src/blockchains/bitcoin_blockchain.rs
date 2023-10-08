use secp256k1::{PublicKey, Secp256k1, SecretKey};
use bip39::Mnemonic;
use sha3::{Digest, Keccak256};
use rand::Rng;
use std::str::FromStr;
use crate::blockchains::abstract_blockchain::AbstractBlockchain;

pub struct BitcoinBlockchain {}

impl AbstractBlockchain for BitcoinBlockchain {

    ///generate random private key with [`secp256k1`]
    ///
    /// [`secp256k1`]: https://docs.rs/secp256k1/0.27.0/secp256k1
    fn generate_private_key() -> Result<Vec<u8>, &'static str> {
        let mut rng = rand::thread_rng();
        let private_key_bytes: [u8; 32] = rng.gen();

        let private_key =
            SecretKey::from_slice(&private_key_bytes).map_err(|_| "Invalid private key")?;

        Ok(private_key[..].to_vec())
    }

    ///generate public key using private key with [`secp256k1`]
    ///
    /// [`secp256k1`]: https://docs.rs/secp256k1/0.27.0/secp256k1
    fn generate_public_key(private_key_bytes: &Vec<u8>) -> Result<Vec<u8>, &'static str> {
        let private_key =
            SecretKey::from_slice(private_key_bytes).map_err(|_| "Invalid private key")?;

        let secp = Secp256k1::new();
        let public_key = PublicKey::from_secret_key(&secp, &private_key);

        Ok(public_key.serialize_uncompressed().to_vec())
    }

    ///generate address using public key with [`sha3`]
    ///
    /// [`sha3`]: https://docs.rs/sha3/0.10.8/sha3
    fn get_address(public_key_bytes: &Vec<u8>) -> String {
        let mut hasher = Keccak256::new();
        hasher.update(&public_key_bytes[1..]);
        let hash = hasher.finalize();
        let hash = &hash[12..];

        let address = hex::encode(hash);

        address
    }

    ///generate mnemonic phrase using private key with [`bip39`]
    ///
    /// [`bip39`]: https://docs.rs/bip39/2.0.0/bip39
    fn get_mnemonics(private_key_bytes: &Vec<u8>) -> Result<String, &'static str> {
        let mnemonic = Mnemonic::from_entropy(private_key_bytes)
            .map_err(|_| "Invalid private key")?;
        let phrase: Vec<String> = mnemonic.word_iter().map(|s| s.to_string()).collect();
        Ok(phrase.join(" "))
    }

    ///Recover a wallet converting the mnemonic back to the entropy used to generate it
    ///
    ///# Returns
    ///
    /// private key
    fn recover_wallet(mnemonic_phrase: &str) -> Result<Vec<u8>, &'static str>{
        let mnemonic = Mnemonic::from_str(mnemonic_phrase)
            .map_err(|_| "Invalid mnemonic phrase")?;
        let private_key_bytes = mnemonic.to_entropy();
        Ok(private_key_bytes.to_vec())
    }
}

impl BitcoinBlockchain {
    pub fn generate_private_key() -> Result<Vec<u8>, &'static str> {
        <Self as AbstractBlockchain>::generate_private_key()
    }
    pub fn generate_public_key(private_key_bytes: &Vec<u8>) -> Result<Vec<u8>, &'static str> {
        <Self as AbstractBlockchain>::generate_public_key(private_key_bytes)
    }
    pub fn get_address(public_key_bytes: &Vec<u8>) -> String {
        <Self as AbstractBlockchain>::get_address(public_key_bytes)
    }
    pub fn get_mnemonics(private_key_bytes: &Vec<u8>) -> Result<String, &'static str> {
        <Self as AbstractBlockchain>::get_mnemonics(private_key_bytes)
    }
    pub fn recover_wallet(mnemonic_phrase: &str) -> Result<Vec<u8>, &'static str> {
        <Self as AbstractBlockchain>::recover_wallet(mnemonic_phrase)
    }
}
