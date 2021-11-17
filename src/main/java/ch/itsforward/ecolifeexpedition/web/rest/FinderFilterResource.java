package ch.itsforward.ecolifeexpedition.web.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.itsforward.ecolifeexpedition.domain.Region;
import ch.itsforward.ecolifeexpedition.domain.Tour;
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

	@GetMapping("/tours")
	public List<Tour> searchTours(@RequestParam("destination") String destinationId,
			@RequestParam("typeCircuit") String typeCircuitId, @RequestParam("dateCheckin") String dateCheckin,
			@RequestParam("dateCheckin") String dateCheckout) {
		
		Date dateDebut = null;
		Date dateFin = null;

		if (dateCheckin != null)
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				dateDebut = sdf.parse(dateCheckin);
			} catch (Exception e) {
				dateDebut = null;
			}
		if (dateCheckout != null)
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				dateFin = sdf.parse(dateCheckout);
			} catch (Exception e) {
				dateFin = null;
			}
		log.info("REST request to search filtred Data on Tours ! { destination : " + destinationId + ", typeCircuit : "
				+ typeCircuitId + ",dateCheckin :" + dateCheckin + ",dateCheckout :" + dateCheckout  + ",dateDebut :" + dateDebut + ",dateFin :" + dateFin);
		List<Tour> result = finderService.findRquestedTours(Long.parseLong(destinationId),
				Long.parseLong(typeCircuitId), dateDebut, dateFin);

		return result;

	}
	
	@GetMapping("/regions/top")
	public List<Region> topDestinations(){
		List<Region> result = finderService.findTopDestinations();
		return result;
	}
	@GetMapping("/tours/trending")
	public List<Tour> trendingTours(){
		List<Tour> result = finderService.findTrendingTours();
		return result;
	}

}

