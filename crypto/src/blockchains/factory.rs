use crate::blockchains::abstract_blockchain::Blockchain;
pub struct BlockchainFactory;
impl BlockchainFactory {
    pub fn create<T: Blockchain>() -> T{
        T::new()
    }
}