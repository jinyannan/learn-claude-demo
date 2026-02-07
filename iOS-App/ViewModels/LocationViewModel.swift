import Foundation
import CoreLocation

@MainActor
class LocationViewModel: NSObject, ObservableObject, CLLocationManagerDelegate {
    private let api = LocationAPI()
    private let locationManager = CLLocationManager()

    @Published var nearbyPets: [Pet] = []
    @Published var nearbyUsers: [User] = []
    @Published var nearbyServices: [ServicePublish] = []
    @Published var currentLocation: CLLocation?
    @Published var isLoading = false
    @Published var errorMessage: String?
    @Published var authorizationStatus: CLAuthorizationStatus = .notDetermined

    override init() {
        super.init()
        setupLocationManager()
    }

    private func setupLocationManager() {
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.distanceFilter = 100 // Update every 100 meters

        authorizationStatus = locationManager.authorizationStatus
    }

    // MARK: - Location Permission
    func requestLocationPermission() {
        locationManager.requestWhenInUseAuthorization()
    }

    func startUpdatingLocation() {
        switch authorizationStatus {
        case .authorizedWhenInUse, .authorizedAlways:
            locationManager.startUpdatingLocation()
        default:
            requestLocationPermission()
        }
    }

    func stopUpdatingLocation() {
        locationManager.stopUpdatingLocation()
    }

    // MARK: - CLLocationManagerDelegate
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else { return }
        self.currentLocation = location
    }

    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        self.errorMessage = "Location error: \(error.localizedDescription)"
    }

    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        self.authorizationStatus = status

        switch status {
        case .authorizedWhenInUse, .authorizedAlways:
            startUpdatingLocation()
        case .denied, .restricted:
            self.errorMessage = "Location permission denied"
        case .notDetermined:
            requestLocationPermission()
        @unknown default:
            break
        }
    }

    // MARK: - Nearby Searches
    func searchNearbyPets(radius: Int = 10, petType: String? = nil, userId: Int64? = nil) {
        guard let location = currentLocation else {
            self.errorMessage = "Location not available"
            return
        }

        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.nearbyPets = try await api.findNearbyPets(
                    latitude: location.coordinate.latitude,
                    longitude: location.coordinate.longitude,
                    radius: radius,
                    userId: userId,
                    petType: petType
                )
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func searchNearbyUsers(radius: Int = 10, userId: Int64? = nil) {
        guard let location = currentLocation else {
            self.errorMessage = "Location not available"
            return
        }

        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.nearbyUsers = try await api.findNearbyUsers(
                    latitude: location.coordinate.latitude,
                    longitude: location.coordinate.longitude,
                    radius: radius,
                    userId: userId
                )
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func searchNearbyServices(radius: Int = 10, serviceTypeId: Int64? = nil) {
        guard let location = currentLocation else {
            self.errorMessage = "Location not available"
            return
        }

        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.nearbyServices = try await api.findNearbyServices(
                    latitude: location.coordinate.latitude,
                    longitude: location.coordinate.longitude,
                    radius: radius,
                    serviceTypeId: serviceTypeId
                )
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
                // Fallback to mock data
                self.nearbyServices = ServicePublish.mockData
            }
        }
    }

    // MARK: - Distance Calculation
    func calculateDistance(to latitude: Double, longitude: Double) -> Double? {
        guard let location = currentLocation else { return nil }
        let targetLocation = CLLocation(latitude: latitude, longitude: longitude)
        return location.distance(from: targetLocation) / 1000 // Convert to km
    }

    // Load mock data for testing
    func loadMockData() {
        self.nearbyPets = Pet.mockData
        self.nearbyServices = ServicePublish.mockData
    }
}
