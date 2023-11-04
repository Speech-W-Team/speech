use crate::transactions::abstract_signer::AbstractSigner;

pub trait Transaction {
    type NewTransactionParameters;

    fn new(params: Self::NewTransactionParameters) -> Self;
    fn sign(&self, signer: &dyn AbstractSigner, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8>;
}