import Foundation
import Capacitor
import DittoSwift

@objc(CapacitorDittoPlugin)
public class CapacitorDittoPlugin: CAPPlugin {
    private let implementation = CapacitorDitto()
    
    @objc func initDitto(_ call: CAPPluginCall) {
        
    }

    @objc func echo(_ call: CAPPluginCall) {
        let identity = call.getObject("identity")
        
        if (identity == nil) {
            call.reject("No identity given")
            return
        }
        
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
