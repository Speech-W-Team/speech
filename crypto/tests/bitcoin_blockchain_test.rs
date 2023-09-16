#[cfg(test)]
mod tests {
    use crypto::blockchains::ethereum_blockchain::*;

    #[test]
    fn test_generate_keypair() {
        let (private_key, public_key) = generate_keypair().expect("Failed to generate keypair");

        assert_eq!(private_key.to_string().len(), 64);
        assert_eq!(public_key.serialize_uncompressed().len(), 65);
    }

    #[test]
    fn test_keypair_not_equal() {
        let (private_key, public_key) = generate_keypair().expect("Failed to generate keypair");

        assert_ne!(private_key.to_string(), public_key.to_string());
    }

    #[test]
    fn test_get_address() {
        let mut public_key_bytes: Vec<u8> = vec![4; 64];
        public_key_bytes.insert(0, 4);

        let address = get_address(&public_key_bytes);

        assert_eq!(address.len(), 40);
    }
}