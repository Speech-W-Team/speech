use secp256k1::{PublicKey, Secp256k1, SecretKey};
use sha3::{Digest, Keccak256};
use hex::encode;
use rand::Rng;

pub fn generate_keypair() -> Result<(SecretKey, PublicKey), &'static str> {
    let mut rng = rand::thread_rng();
    let private_key_bytes: [u8; 32] = rng.gen();

    let private_key =
        SecretKey::from_slice(&private_key_bytes).map_err(|_| "Invalid private key")?;

    let secp = Secp256k1::new();
    let public_key = PublicKey::from_secret_key(&secp, &private_key);

    Ok((private_key, public_key))
}

pub fn get_address(public_key_bytes: &Vec<u8>) -> String {
    let mut hasher = Keccak256::new();
    hasher.update(&public_key_bytes[1..]);
    let hash = hasher.finalize();
    let hash = &hash[12..];

    let address = encode(hash);

    address
}
