pub trait AbstractBlockchain {
    fn generate_private_key() -> Result<Vec<u8>, &'static str>;
    fn generate_public_key(private_key_bytes: &Vec<u8>) -> Result<Vec<u8>, &'static str>;
    fn get_address(public_key_bytes: &Vec<u8>) -> String;
    fn get_mnemonics(private_key_bytes: &Vec<u8>) -> Result<String, &'static str>;
    fn recover_wallet(mnemonic_phrase: &str) -> Result<Vec<u8>, &'static str>;
}