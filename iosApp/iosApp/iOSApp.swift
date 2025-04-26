import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
    
    init() {
        GMSServices.provideAPIKey("<map-api-key>")
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
