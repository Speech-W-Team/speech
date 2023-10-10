#[cfg(test)]
mod tests {
    use crypto::blockchains::bitcoin::bitcoin_blockchain::BitcoinBlockchain;
    use crypto::blockchains::factory::BlockchainFactory;
    use bip39::Mnemonic;

    #[test]
    fn test_generate_private_key() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let private_key = bitcoin_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        assert_eq!(private_key.len(), 32);
    }

    #[test]
    fn test_generate_public_key() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let private_key = bitcoin_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        let public_key = bitcoin_blockchain.generate_public_key(&private_key)
            .expect("Failed to generate public key");

        assert_eq!(public_key.len(), 65);
    }

    #[test]
    fn test_keypair_not_equal() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let private_key = bitcoin_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        let public_key = bitcoin_blockchain.generate_public_key(&private_key)
            .expect("Failed to generate public key");

        assert_ne!(private_key, public_key);
    }

    #[test]
    fn test_get_address() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let mut public_key_bytes: Vec<u8> = vec![4; 64];
        public_key_bytes.insert(0, 4);

        let address = bitcoin_blockchain.get_address(&public_key_bytes);

        assert_eq!(address.len(), 40);
    }

    #[test]
    fn test_get_mnemonics() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let secret_key_bytes = bitcoin_blockchain.generate_private_key()
            .expect("Failed to generate keypair");
        let mnemonic = Mnemonic::from_entropy(&secret_key_bytes)
            .expect("Failed to generate mnemonic phrase");
        let expected_phrase = mnemonic.to_string();

        let result = bitcoin_blockchain.get_mnemonics(&secret_key_bytes).unwrap();

        assert_eq!(result, expected_phrase);
    }

    #[test]
    fn test_get_mnemonics_with_invalid_entropy() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let invalid_entropy: Vec<u8> = vec![0; 10];
        let result = bitcoin_blockchain.get_mnemonics(&invalid_entropy);

        assert!(result.is_err());
    }

    #[test]
    fn test_recover_wallet() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let private_key_bytes = bitcoin_blockchain.generate_private_key().unwrap();
        let mnemonics = bitcoin_blockchain.get_mnemonics(&private_key_bytes).unwrap();

        let wallet = bitcoin_blockchain.recover_wallet(&mnemonics.as_str()).unwrap();

        assert_eq!(wallet, private_key_bytes);
    }

    #[test]
    fn test_recover_wallet_with_wrong_mnemonic_format() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let mnemonics = String::from("i am invalid mnemonics format");

        let wallet = bitcoin_blockchain.recover_wallet(&mnemonics.as_str());

        assert!(wallet.is_err());
    }
}