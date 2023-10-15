use std::str::FromStr;

use bip39::Mnemonic;
use k256::ecdsa::{SigningKey, VerifyingKey};
use k256::elliptic_curve::rand_core::OsRng;
use sha3::{Digest, Keccak256};

use crate::blockchains::abstract_blockchain::Blockchain;

pub struct BitcoinBlockchain {}

impl Blockchain for BitcoinBlockchain {
    fn new() -> BitcoinBlockchain {
        BitcoinBlockchain {}
    }

    ///generate random private key with [`k256`]
    ///
    /// https://docs.rs/k256/latest/k256/ecdsa/
    fn generate_private_key(&self) -> Result<Vec<u8>, &'static str> {
        let mut csprng = OsRng;
        let private_key = SigningKey::random(&mut csprng);
        Ok(private_key.to_bytes().to_vec())
    }

    ///generate public key using private key with [`k256`]
    ///
    /// https://docs.rs/k256/latest/k256/ecdsa/
    fn generate_public_key(&self, private_key_bytes: &Vec<u8>) -> Result<Vec<u8>, &'static str> {
        let private_key = SigningKey::from_slice(private_key_bytes).unwrap();

        let public_key = VerifyingKey::from(&private_key);

        Ok(public_key.to_encoded_point(false).as_bytes().to_vec())
    }

    ///generate address using public key with [`sha3`]
    ///
    /// [`sha3`]: https://docs.rs/sha3/0.10.8/sha3
    fn get_address(&self, public_key_bytes: &Vec<u8>) -> String {
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
    fn get_mnemonics(&self, private_key_bytes: &Vec<u8>) -> Result<String, &'static str> {
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
    fn recover_wallet(&self, mnemonic_phrase: &str) -> Result<Vec<u8>, &'static str> {
        let mnemonic = Mnemonic::from_str(mnemonic_phrase)
            .map_err(|_| "Invalid mnemonic phrase")?;
        let private_key_bytes = mnemonic.to_entropy();
        Ok(private_key_bytes.to_vec())
    }
}

impl BitcoinBlockchain {
    pub fn generate_private_key(&self) -> Result<Vec<u8>, &'static str> {
        <Self as Blockchain>::generate_private_key(self)
    }
    pub fn generate_public_key(&self, private_key_bytes: &Vec<u8>) -> Result<Vec<u8>, &'static str> {
        <Self as Blockchain>::generate_public_key(self, private_key_bytes)
    }
    pub fn get_address(&self, public_key_bytes: &Vec<u8>) -> String {
        <Self as Blockchain>::get_address(self, public_key_bytes)
    }
    pub fn get_mnemonics(&self, private_key_bytes: &Vec<u8>) -> Result<String, &'static str> {
        <Self as Blockchain>::get_mnemonics(self, private_key_bytes)
    }
    pub fn recover_wallet(&self, mnemonic_phrase: &str) -> Result<Vec<u8>, &'static str> {
        <Self as Blockchain>::recover_wallet(self, mnemonic_phrase)
    }
}
