use crate::transactions::abstract_signer::Signer;
use crate::transactions::abstract_transaction::Transaction;
use crate::transactions::ethereum::ethereum_signer::EthereumSigner;

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

    fn sign(&self, signer: &dyn Signer, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        signer.sign(private_key, data_to_sign)
    }
}

impl EthereumTransaction {
    pub fn from(&self) -> &String {
        &self.from
    }

    pub fn to(&self) -> &String {
        &self.to
    }

    pub fn gas_limit(&self) -> u64 {
        self.gas_limit
    }

    pub fn max_fee_per_gas(&self) -> u64 {
        self.max_fee_per_gas
    }

    pub fn max_priority_fee_per_gas(&self) -> u64 {
        self.max_priority_fee_per_gas
    }

    pub fn nonce(&self) -> u64 {
        self.nonce
    }

    pub fn value(&self) -> u64 {
        self.value
    }

    pub fn data(&self) -> &Vec<u8> {
        &self.data
    }

    pub fn new(params: (String, String, u64, u64, u64, u64, u64, Vec<u8>)) -> Self {
        <Self as Transaction>::new(params)
    }

    pub fn sign(&self, signer: &EthereumSigner, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8> {
        <Self as Transaction>::sign(self, signer, private_key, data_to_sign)
    }
}

