#[cfg(test)]
mod tests {
    use crypto::transactions::bitcoin::bitcoin_transaction::BitcoinTransaction;
    use crypto::transactions::bitcoin::bitcoin_signer::BitcoinSigner;
    use crypto::blockchains::bitcoin::bitcoin_blockchain::BitcoinBlockchain;
    use crypto::blockchains::factory::BlockchainFactory;

    #[test]
    fn test_bitcoin_transaction() {
        let btc_transaction = BitcoinTransaction::new((
            1,
            vec![1, 2, 3, 4, 5],
            vec![6, 7, 8, 9, 10],
            0,
        ));

        assert_eq!(btc_transaction.version(), 1);
        assert_eq!(btc_transaction.input(), &vec![1, 2, 3, 4, 5]);
        assert_eq!(btc_transaction.output(), &vec![6, 7, 8, 9, 10]);
        assert_eq!(btc_transaction.locktime(), 0);
    }

    #[test]
    fn test_bitcoin_signer() {
        let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
        let private_key = bitcoin_blockchain.generate_private_key()
            .expect("Failed to generate private key");
        let data_to_sign = b"Hello, world!";
        let signer = BitcoinSigner;
        let transaction = BitcoinTransaction::new((
            1,
            vec![1, 2, 3, 4, 5],
            vec![6, 7, 8, 9, 10],
            0,
        ));
        let signature = transaction.sign(&signer, &private_key, data_to_sign);

        assert!(!signature.is_empty(), "Signature should not be empty");
    }
}