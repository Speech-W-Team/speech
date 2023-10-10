#[cfg(test)]
mod tests {
    use crypto::blockchains::ethereum::ethereum_blockchain::EthereumBlockchain;
    use crypto::blockchains::factory::BlockchainFactory;
    use bip39::Mnemonic;

    #[test]
    fn test_generate_private_key() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let private_key = ethereum_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        assert_eq!(private_key.len(), 32);
    }

    #[test]
    fn test_generate_public_key() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let private_key = ethereum_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        let public_key = ethereum_blockchain.generate_public_key(&private_key)
            .expect("Failed to generate public key");

        assert_eq!(public_key.len(), 65);
    }

    #[test]
    fn test_keypair_not_equal() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let private_key = ethereum_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        let public_key = ethereum_blockchain.generate_public_key(&private_key)
            .expect("Failed to generate public key");

        assert_ne!(private_key, public_key);
    }

    #[test]
    fn test_get_address() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let mut public_key_bytes: Vec<u8> = vec![4; 64];
        public_key_bytes.insert(0, 4);

        let address = ethereum_blockchain.get_address(&public_key_bytes);

        assert_eq!(address.len(), 40);
    }

    #[test]
    fn test_get_mnemonics() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let secret_key_bytes = ethereum_blockchain.generate_private_key()
            .expect("Failed to generate keypair");
        let mnemonic = Mnemonic::from_entropy(&secret_key_bytes)
            .expect("Failed to generate mnemonic phrase");
        let expected_phrase = mnemonic.to_string();

        let result = ethereum_blockchain.get_mnemonics(&secret_key_bytes).unwrap();

        assert_eq!(result, expected_phrase);
    }

    #[test]
    fn test_get_mnemonics_with_invalid_entropy() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let invalid_entropy: Vec<u8> = vec![0; 10];
        let result = ethereum_blockchain.get_mnemonics(&invalid_entropy);

        assert!(result.is_err());
    }

    #[test]
    fn test_recover_wallet() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let private_key_bytes = ethereum_blockchain.generate_private_key().unwrap();
        let mnemonics = ethereum_blockchain.get_mnemonics(&private_key_bytes).unwrap();

        let wallet = ethereum_blockchain.recover_wallet(&mnemonics.as_str()).unwrap();

        assert_eq!(wallet, private_key_bytes);
    }

    #[test]
    fn test_recover_wallet_with_wrong_mnemonic_format() {
        let ethereum_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let mnemonics = String::from("i am invalid mnemonics format");

        let wallet = ethereum_blockchain.recover_wallet(&mnemonics.as_str());

        assert!(wallet.is_err());
    }
}