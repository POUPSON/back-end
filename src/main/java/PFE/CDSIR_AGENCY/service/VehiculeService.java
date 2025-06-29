//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.entity.Vehicule;
import java.util.List;
import java.util.Optional;

public interface VehiculeService {
	List<Vehicule> getAllVehicules();

	Optional<Vehicule> getVehiculeById(Long id);

	Vehicule createVehicule(Vehicule vehicule);

	Vehicule updateVehicule(Long id, Vehicule vehiculeDetails);

	void deleteVehicule(Long id);

	List<Vehicule> getVehiculesByAgence(Long agenceId);

	List<Vehicule> getAvailableVehicules(Long agenceId, int requiredCapacity);
}
