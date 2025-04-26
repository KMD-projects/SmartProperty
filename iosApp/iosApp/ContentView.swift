import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            mapUIViewController: { (latitude, longitude, zoom, title, snippet) -> UIViewController in
                return UIHostingController(
                    rootView: GoogleMapView(
                        latitude: Double(truncating: latitude),
                        longitude: Double(truncating: longitude),
                        zoom: Float(truncating: zoom),
                        title: title,
                        snippet: snippet
                    )
                )
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea() // Compose has own keyboard handler
    }
}



