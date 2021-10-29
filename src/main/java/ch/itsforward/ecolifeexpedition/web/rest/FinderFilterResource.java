package ch.itsforward.ecolifeexpedition.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.itsforward.ecolifeexpedition.domain.Region;
import ch.itsforward.ecolifeexpedition.domain.TypeCircuit;
import ch.itsforward.ecolifeexpedition.service.FinderService;

@RestController
@RequestMapping("/filter")
public class FinderFilterResource {

	private Logger log = LoggerFactory.getLogger(FinderFilterResource.class);

	private FinderService finderService;

	public FinderFilterResource(FinderService finderService) {
		super();
		this.finderService = finderService;
	}

	@GetMapping("/regions")
	public List<Region> getAllRegions() {
		log.debug("REST request to get all Regions");
		return finderService.findAllRegion();
	}
	
	@GetMapping("/typecircuits")
	public List<TypeCircuit> getAllTypeCircuits() {
		log.debug("REST request to get all type of Circuit");
		return finderService.findAllTypeCircuit();
	}

}
