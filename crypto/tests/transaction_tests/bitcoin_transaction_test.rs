#[cfg(test)]
mod tests {
    use crypto::transactions::bitcoin::bitcoin_transaction::BitcoinTransaction;

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
}