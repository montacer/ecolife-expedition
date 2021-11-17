package ch.itsforward.ecolifeexpedition.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.itsforward.ecolifeexpedition.domain.Region;
import ch.itsforward.ecolifeexpedition.domain.Tour;
import ch.itsforward.ecolifeexpedition.domain.TypeCircuit;
import ch.itsforward.ecolifeexpedition.repository.RegionRepository;
import ch.itsforward.ecolifeexpedition.repository.TourRepository;
import ch.itsforward.ecolifeexpedition.repository.TypeCircuitRepository;

@Service
@Transactional
public class FinderService {

	private final Logger log = LoggerFactory.getLogger(FinderService.class);

	private RegionRepository regionRepository;
	private TypeCircuitRepository typeCircuitRepository;
	private TourRepository tourRepository;

	public FinderService(RegionRepository regionRepository, TypeCircuitRepository typeCircuitRepository,
			TourRepository tourRepository) {
		super();
		this.regionRepository = regionRepository;
		this.typeCircuitRepository = typeCircuitRepository;
		this.tourRepository = tourRepository;
	}

	public List<Region> findAllRegion() {
		return regionRepository.findAll();
	}

	public List<TypeCircuit> findAllTypeCircuit() {
		return typeCircuitRepository.findAll();
	}

	public List<Tour> findRquestedTours(Long destinationId, Long typeCircuitId, Date dateCheckin, Date dateCheckout) {
		Tour probe = new Tour();
		TypeCircuit typeCircuit = new TypeCircuit();
		typeCircuit.setId(typeCircuitId);
		Region destination = new Region();
		destination.setId(destinationId);
		probe.setTypeCircuit(typeCircuit);
		probe.setRegion(destination);
		Example<Tour> exmapleToSearch = Example.of(probe);
		List<Tour> result = tourRepository.findAll(exmapleToSearch);
		result.forEach(System.out::println);
		return result;
	}

	public List<Region> findTopDestinations() {
		return null;
	}

	public List<Tour> findTrendingTours() {
		return null;
	}

}
