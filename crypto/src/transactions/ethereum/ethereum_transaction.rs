use k256::ecdsa::{SigningKey, Signature, signature::Signer};

use crate::transactions::abstract_transaction::Transaction;

pub struct EthereumTransaction {
    from: String,
    to: String,
    gas_limit: u64,
    max_fee_per_gas: u64,
    max_priority_fee_per_gas: u64,
    nonce: u64,
    value: u64,
    data: Vec<u8>,
}

impl Transaction for EthereumTransaction {
    type NewTransactionParameters = (String, String, u64, u64, u64, u64, u64, Vec<u8>);

    fn new(params: Self::NewTransactionParameters) -> EthereumTransaction {
        EthereumTransaction {
            from: params.0,
            to: params.1,
            gas_limit: params.2,
            max_fee_per_gas: params.3,
            max_priority_fee_per_gas: params.4,
            nonce: params.5,
            value: params.6,
            data: params.7,
        }
    }

    fn sign(&self, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        let signing_key = SigningKey::from_slice(private_key).expect("32 bytes");
        let sig: Signature = signing_key.sign(data_to_sign);

        sig.to_bytes().to_vec()
    }
}