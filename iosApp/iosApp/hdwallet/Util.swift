//
//  Util.swift
//  iosApp
//
//  Created by z17macbook1 on 2/6/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import Foundation

public extension Data {
    /// A property that returns an array of UInt8 bytes.
    @inlinable var bytes: [UInt8] {
        withUnsafeBytes { bytesPtr in Array(bytesPtr) }
    }
}
