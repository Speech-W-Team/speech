#[cfg(test)]
mod tests {
    use crypto::blockchains::ethereum_blockchain::*;
    use bip39::Mnemonic;

    #[test]
    fn test_generate_private_key() {
        let private_key = generate_private_key()
            .expect("Failed to generate private key");
        println!("private_key: {:?}", private_key);
        assert_eq!(private_key.len(), 32);
    }

    #[test]
    fn test_generate_public_key() {
        let private_key = generate_private_key()
            .expect("Failed to generate private key");
        let public_key = generate_public_key(&private_key)
            .expect("Failed to generate public key");

        assert_eq!(public_key.len(), 65);
    }

    #[test]
    fn test_keypair_not_equal() {
        let private_key = generate_private_key()
            .expect("Failed to generate private key");
        let public_key = generate_public_key(&private_key)
            .expect("Failed to generate public key");

        assert_ne!(private_key, public_key);
    }

    #[test]
    fn test_get_address() {
        let mut public_key_bytes: Vec<u8> = vec![4; 64];
        public_key_bytes.insert(0, 4);

        let address = get_address(&public_key_bytes);

        assert_eq!(address.len(), 40);
    }

    #[test]
    fn test_get_mnemonics() {
        let secret_key_bytes = generate_private_key().expect("Failed to generate keypair");
        let mnemonic = Mnemonic::from_entropy(&secret_key_bytes).expect("Failed to generate mnemonic phrase");
        let expected_phrase: String = mnemonic.word_iter().map(|s| s.to_string()).collect::<Vec<String>>().join(" ");

        let result = get_mnemonics(&secret_key_bytes).unwrap();

        assert_eq!(result, expected_phrase);
    }

    #[test]
    fn test_get_mnemonics_with_invalid_entropy() {
        let invalid_entropy: Vec<u8> = vec![0; 10];
        let result = get_mnemonics(&invalid_entropy);

        assert!(result.is_err());
    }
}