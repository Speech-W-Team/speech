use k256::ecdsa::{SigningKey, Signature};
use crate::transactions::abstract_signer::Signer;

pub struct BitcoinSigner;

impl Signer for BitcoinSigner {
    fn sign(&self, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        let signing_key = SigningKey::from_slice(private_key).expect("32 bytes");
        let sig: Signature = signing_key.sign(data_to_sign);

        sig.to_bytes().to_vec()
    }
}