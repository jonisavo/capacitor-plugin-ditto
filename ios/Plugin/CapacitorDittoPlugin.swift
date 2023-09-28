import Foundation

@objc public class CapacitorDittoPlugin: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
