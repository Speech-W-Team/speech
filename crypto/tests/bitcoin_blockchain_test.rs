#[cfg(test)]
mod tests {
    use crypto::blockchains::bitcoin_blockchain::*;

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
}