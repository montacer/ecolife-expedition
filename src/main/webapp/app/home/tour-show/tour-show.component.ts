import { Component, OnInit } from '@angular/core';
import { ITour } from 'app/shared/model/tour.model';

@Component({
	selector: 'jhi-tour-show',
	templateUrl: './tour-show.component.html',
	styleUrls: ['./tour-show.component.scss']
})
export class TourShowComponent implements OnInit {
	tourToShow: ITour = {};
	constructor() { }

	ngOnInit(): void {
		this.tourToShow = {
			id: 1,
			imageUrl: "../../../assets/images/uploads/tours/tour1.jpg",
			libTitre: "New Camp treks",
			//duree: "5 Days / 4 Nights",
			//score: "4",
			//prixVenteInitiale : "999",
			prixTTC: 599
		};



	}
}


