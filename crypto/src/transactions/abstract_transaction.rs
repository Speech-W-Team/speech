extern crate k256;
extern crate sha3;

use k256::ecdsa::{SigningKey, Signature, signature::Signer};

pub trait TransactionTrait {
    fn new(from: String, to: String, gas_limit: u64, max_fee_per_gas: u64,
           max_priority_fee_per_gas: u64, nonce: u64, value: u64) -> Self;

    fn sign(&self, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8>;
}