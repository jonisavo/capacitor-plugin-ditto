import Foundation
import Capacitor
import DittoSwift

private let ONLINE_PLAYGROUND = "onlinePlayground"

@objc public class CapacitorDitto: NSObject {
    private let ditto: Ditto? = nil
    
    private func parseIdentity(identityObj: JSObject) -> DittoIdentity? {
        let type = identityObj["type"]
        
        if (type == nil) {
            return nil
        }
            
        return switch type as? String {
        case ONLINE_PLAYGROUND:
            let appId = identityObj["appId"] as? String
            let token = identityObj["token"] as? String
            let enableDittoCloudSync = identityObj["enableDittoCloudSync"] as? Bool ?? true
            let customAuthUrl = identityObj["customAuthUrl"] as? String ?? nil
            

        default:
            nil
        }
    }
    
    @objc public func initDitto(identityObj: JSObject) -> Ditto? {
        let identity = parseIdentity(identityObj)
        
        
    }

    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
