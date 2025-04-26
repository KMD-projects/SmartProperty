//
//  GoogleMapView.swift
//  iosApp
//
//  Created by Emrys on 24/4/2568 BE.
//  Copyright © 2568 BE orgName. All rights reserved.
//

import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps

struct GoogleMapView: UIViewRepresentable {

    var latitude: Double
    var longitude: Double
    var zoom: Float
    var title: String?
    var snippet: String?

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        options.camera = GMSCameraPosition.camera(withLatitude: latitude, longitude: longitude, zoom: zoom)

        let mapView = GMSMapView(options: options)

        // Creates a marker in the center of the map.
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        marker.title = title
        marker.snippet = snippet
        marker.map = mapView
        
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {}
}
