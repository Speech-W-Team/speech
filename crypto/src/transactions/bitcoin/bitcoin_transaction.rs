use crate::transactions::abstract_signer::Signer;
use crate::transactions::abstract_transaction::Transaction;
use crate::transactions::bitcoin::bitcoin_signer::BitcoinSigner;

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

    fn sign(&self, signer: &dyn Signer, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        signer.sign(private_key, data_to_sign)
    }
}

impl BitcoinTransaction {
    pub fn version(&self) -> i32 {
        self.version
    }

    pub fn input(&self) -> &Vec<u8> {
        &self.input
    }

    pub fn output(&self) -> &Vec<u8> {
        &self.output
    }

    pub fn locktime(&self) -> u32 {
        self.locktime
    }

    pub fn new(params: (i32, Vec<u8>, Vec<u8>, u32)) -> Self {
        <Self as Transaction>::new(params)
    }

    pub fn sign(&self, signer: &BitcoinSigner, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        <Self as Transaction>::sign(self, signer, private_key, data_to_sign)
    }
}
