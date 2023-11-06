pub trait Signer {
    fn sign(&self, private_key: &Vec<u8>, data_to_sign: &[u8]) -> Vec<u8>;
}