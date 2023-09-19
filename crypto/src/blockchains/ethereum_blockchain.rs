use secp256k1::{PublicKey, Secp256k1, SecretKey};
use bip39::{Error, Mnemonic};
use sha3::{Digest, Keccak256};
use hex::encode;
use rand::Rng;

pub fn generate_private_key() -> Result<Vec<u8>, &'static str> {
    let mut rng = rand::thread_rng();
    let private_key_bytes: [u8; 32] = rng.gen();

    let private_key =
        SecretKey::from_slice(&private_key_bytes).map_err(|_| "Invalid private key")?;

    Ok(private_key[..].to_vec())
}

pub fn generate_public_key(private_key_bytes: &Vec<u8>) -> Result<Vec<u8>, &'static str> {
let private_key =
        SecretKey::from_slice(&private_key_bytes).map_err(|_| "Invalid private key")?;
    let secp = Secp256k1::new();
    let public_key = PublicKey::from_secret_key(&secp, &private_key);

    Ok(public_key.serialize_uncompressed().to_vec())
}
pub fn get_address(public_key_bytes: &Vec<u8>) -> String {
    let mut hasher = Keccak256::new();
    hasher.update(&public_key_bytes[1..]);
    let hash = hasher.finalize();
    let hash = &hash[12..];

    let address = encode(hash);

    address
}

pub fn get_mnemonics(private_key_bytes: &Vec<u8>) -> Result<String, Error> {
    let mnemonic = Mnemonic::from_entropy(&private_key_bytes)?;
    let phrase: Vec<String> = mnemonic.word_iter().map(|s| s.to_string()).collect();
    Ok(phrase.join(" "))
}