use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::jbyteArray;

use crate::blockchains::bitcoin::bitcoin_blockchain::BitcoinBlockchain;
use crate::blockchains::factory::BlockchainFactory;

#[no_mangle]
pub extern "system" fn Java_wtf_speech_core_cryptokt_CryptoApi_generatePrivateKey<'local>(env: JNIEnv, _class: JClass) -> jbyteArray {
    let bitcoin_blockchain = BlockchainFactory::create::<BitcoinBlockchain>();
    let rust_byte_array: Vec<u8> = bitcoin_blockchain.generate_private_key().unwrap();

    env.byte_array_from_slice(&rust_byte_array)
        .expect("Failed to create java byte array")
        .as_raw()
}

// cargo build --target aarch64-linux-android --release
// cargo build --target armv7-linux-androideabi --release
// cargo build --target i686-linux-android --release

// cp crypto/target/aarch64-linux-android/release/libcrypto.so vaultAndroid/src/androidMain/jniLibs/arm64-v8a/libcrypto.so
// cp crypto/target/i686-linux-android/release/libcrypto.so vaultAndroid/src/androidMain/jniLibs/x86/libcrypto.so
// cp crypto/target/armv7-linux-androideabi/release/libcrypto.so vaultAndroid/src/androidMain/jniLibs/armeabi-v7a/libcrypto.so