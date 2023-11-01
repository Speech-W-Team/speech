use k256::ecdsa::{SigningKey, Signature, signature::Signer};

use crate::transactions::abstract_transaction::Transaction;

pub struct BitcoinTransaction {
    version: i32,
    input: Vec<u8>,
    output: Vec<u8>,
    locktime: u32,
}

impl Transaction for BitcoinTransaction {
    type NewTransactionParameters = (i32, Vec<u8>, Vec<u8>, u32);

    fn new(params: Self::NewTransactionParameters) -> BitcoinTransaction {
        BitcoinTransaction {
            version: params.0,
            input: params.1,
            output: params.2,
            locktime: params.3,
        }
    }

    fn sign(&self, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        let signing_key = SigningKey::from_slice(private_key).expect("32 bytes");
        let sig: Signature = signing_key.sign(data_to_sign);

        sig.to_bytes().to_vec()
    }
}