pub trait Blockchain {
    fn new() -> Self;
    fn generate_private_key(&self) -> Result<Vec<u8>, &'static str>;
    fn generate_public_key(&self, private_key_bytes: &Vec<u8>) -> Result<Vec<u8>, &'static str>;
    fn get_address(&self, public_key_bytes: &Vec<u8>) -> String;
    fn get_mnemonics(&self, private_key_bytes: &Vec<u8>) -> Result<String, &'static str>;
    fn recover_wallet(&self, mnemonic_phrase: &str) -> Result<Vec<u8>, &'static str>;
}