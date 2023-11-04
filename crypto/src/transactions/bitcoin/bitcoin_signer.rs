use k256::ecdsa::{SigningKey, Signature, signature::Signer};
use crate::transactions::abstract_signer::AbstractSigner;

pub struct BitcoinSigner;

impl AbstractSigner for BitcoinSigner {
    fn sign(&self, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        let signing_key = SigningKey::from_slice(private_key).expect("32 bytes");
        let sig: Signature = signing_key.sign(data_to_sign);

        sig.to_bytes().to_vec()
    }
}