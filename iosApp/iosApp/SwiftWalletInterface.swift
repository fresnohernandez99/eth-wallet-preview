//
//  IOSMnemonicGenerator.swift
//  iosApp
//
//  Created by z17macbook1 on 2/4/25.
//  Copyright © 2025 orgName. All rights reserved.
//

//import secp256k1
import CryptoSwift
import Foundation
import ComposeApp

class SwiftWalletInterface: WalletInterface {
    
    func generateMnemonic(entropy: KotlinByteArray) -> [String] {
        let entropy = entropy.toData()
        let mnemonic = Mnemonic.create(entropy: entropy, language: .spanish)
        
        let seed = Mnemonic.createSeed(mnemonic: mnemonic)
        return mnemonic.split(separator: " ").map { String($0) }
    }
    
    func determinateSeed(mnemonic: [String]) -> String {
        let mnemonicString = mnemonic.joined(separator: " ")
        let seed = Mnemonic.createSeed(mnemonic: mnemonicString)
        let base64 = seed.base64EncodedString()
        return base64
    }
    
    func determinateAddress(privateKeyHex: String) -> String {
        let privateKey = PrivateKey(pk: privateKeyHex, coin: .ethereum)
        let publickKey = PublicKey(privateKey: privateKey!.raw, coin: .ethereum)
        
        return publickKey.generateEthAddress()
    }
    
    func deriveKeyFromMnemonic(mnemonic: [String], passphrase: String) -> KotlinPair<KotlinByteArray, KotlinByteArray> {
        let mnemonicString = mnemonic.joined(separator: " ")
        let seed = Mnemonic.createSeed(mnemonic: mnemonicString)
        let wallet = Wallet(seed: seed, coin: .ethereum).generateAccount()
        
        // Obtener la clave privada
        let privateKeyData = wallet.privateKey.raw
        
        // Derivar la clave pública (formato comprimido)
        let publicKeyData = wallet.privateKey.publicKey.data
        
        return KotlinPair(
            first: privateKeyData.toKotlinByteArray(),
            second: publicKeyData.toKotlinByteArray()
        )
    }
    
    func signMessage(privateKeyHex: String, message: String) -> KotlinByteArray? {
        guard let privateKey = PrivateKey(pk: privateKeyHex, coin: .ethereum) else {
            return nil
        }
        
        do {
            print("Message: \(message)")
            let signature = try privateKey.sign(hash: message.data(using: .utf8)!)
            return signature.toKotlinByteArray()
        } catch {
            print("Error signing message: \(error)")
            return nil
        }
    }
    
    func verifyMessage(publicKey: KotlinByteArray, message: String, signature: KotlinByteArray) -> Bool {
        return true
    }
    
}

extension KotlinByteArray {
    func toData() -> Data {
        let swiftByteArray: [UInt8] = (0..<Int(self.size)).map { index in
            // Obtener el valor del KotlinByteArray y convertirlo a UInt8
            return UInt8(bitPattern: self.get(index: Int32(index)))
        }
        
        return Data(swiftByteArray)
    }
}

extension Data {
    func toKotlinByteArray() -> KotlinByteArray {
        let kotlinByteArray = KotlinByteArray(size: Int32(self.count))
        self.withUnsafeBytes { (bufferPointer: UnsafeRawBufferPointer) in
            guard let baseAddress = bufferPointer.baseAddress else { return }
            let int8Pointer = baseAddress.assumingMemoryBound(to: Int8.self)
            for i in 0..<self.count {
                let byte = int8Pointer[i]
                kotlinByteArray.set(index: Int32(i), value: byte)
            }
        }
        return kotlinByteArray
    }
}
