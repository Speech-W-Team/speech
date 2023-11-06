#[cfg(test)]
mod tests {
    use crypto::blockchains::ethereum::ethereum_blockchain::EthereumBlockchain;
    use crypto::transactions::ethereum::ethereum_signer::EthereumSigner;
    use crypto::transactions::ethereum::ethereum_transaction::EthereumTransaction;
    use crypto::blockchains::factory::BlockchainFactory;

    #[test]
    fn test_ethereum_transaction() {
        let eth_transaction = EthereumTransaction::new((
            "0xFromAddress".to_string(),
            "0xToAddress".to_string(),
            21000,
            50,
            1,
            0,
            1000000000000000000,
            vec![],
        ));

        assert_eq!(eth_transaction.from(), "0xFromAddress");
        assert_eq!(eth_transaction.to(), "0xToAddress");
        assert_eq!(eth_transaction.gas_limit(), 21000);
        assert_eq!(eth_transaction.max_fee_per_gas(), 50);
        assert_eq!(eth_transaction.max_priority_fee_per_gas(), 1);
        assert_eq!(eth_transaction.nonce(), 0);
        assert_eq!(eth_transaction.value(), 1000000000000000000);
        assert_eq!(eth_transaction.data(), &vec![]);
    }

    #[test]
    fn test_bitcoin_signer() {
        let bitcoin_blockchain = BlockchainFactory::create::<EthereumBlockchain>();
        let private_key = bitcoin_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        let data_to_sign = b"Hello, world!";
        let signer = EthereumSigner;
        let transaction = EthereumTransaction::new((
            "0xFromAddress".to_string(),
            "0xToAddress".to_string(),
            21000,
            50,
            1,
            0,
            1000000000000000000,
            vec![],
        ));
        let signature = transaction.sign(&signer, &private_key, data_to_sign);

        assert!(!signature.is_empty(), "Signature should not be empty");
    }
}