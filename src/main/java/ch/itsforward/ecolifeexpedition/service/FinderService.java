package ch.itsforward.ecolifeexpedition.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.itsforward.ecolifeexpedition.domain.Region;
import ch.itsforward.ecolifeexpedition.domain.TypeCircuit;
import ch.itsforward.ecolifeexpedition.repository.RegionRepository;
import ch.itsforward.ecolifeexpedition.repository.TypeCircuitRepository;

@Service
@Transactional
public class FinderService {

	private final Logger log = LoggerFactory.getLogger(FinderService.class);

	private RegionRepository regionRepository;
	private TypeCircuitRepository typeCircuitRepository;

	public FinderService(RegionRepository regionRepository, TypeCircuitRepository typeCircuitRepository) {
		super();
		this.regionRepository = regionRepository;
		this.typeCircuitRepository = typeCircuitRepository;
	}

	public List<Region> findAllRegion() {
		return regionRepository.findAll();
	}

	public List<TypeCircuit> findAllTypeCircuit() {
		return typeCircuitRepository.findAll();
	}

}
