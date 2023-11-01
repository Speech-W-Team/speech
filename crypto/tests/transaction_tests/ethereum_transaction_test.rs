#[cfg(test)]
mod tests {
    use crypto::transactions::ethereum::ethereum_transaction::EthereumTransaction;

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
}