import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { Moment } from 'moment';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { IRegion } from 'app/shared/model/region.model';
import { ITypeCircuit } from 'app/shared/model/type-circuit.model';
import { ITour } from 'app/shared/model/tour.model';
import { HomeFilterService } from './home.service';
import * as moment from 'moment';

export interface IDataFilter {
	destination?: number;
	typeCircuit?: number;
	dateCheckin?: string;
	dateCheckout?: string;
}

export class DataFilter implements IDataFilter {
	constructor(destination?: number,
		typeCircuit?: number,
		dateCheckin?: string,
		dateCheckout?: string) {
	}
}


@Component({
	selector: 'jhi-home',
	templateUrl: './home.component.html',
	styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
	currentDate: Date = new Date();
	account: Account | null = null;
	authSubscription?: Subscription;

	selectedDestination: any;
	destinations: IRegion[] = []
	topDestinations: IRegion[] = []
	trendingTours: ITour[] = []
	destinationSelect: string[] = [];

	typeCircuits: ITypeCircuit[] = [];

	searchForm = this.fb.group({
		destination: [],
		typeCircuit: [],
		dateCheckin: [],
		dateCheckout: []
	});


	constructor(private accountService: AccountService,
		private loginModalService: LoginModalService,
		private homeService: HomeFilterService,
		private fb: FormBuilder) { }

	ngOnInit(): void {

		this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
		this.searchForm = this.fb.group({
			destination: [],
			typeCircuit: [],
			dateCheckin: [],
			dateCheckout: []
		});

		this.homeService.queryForRegion().subscribe((res: HttpResponse<IRegion[]>) => (this.destinations = res.body || []));
		this.homeService.queryForTopDestination().subscribe((res: HttpResponse<IRegion[]>) => (this.topDestinations = res.body || []));
		this.homeService.queryForTrendingTour().subscribe((res: HttpResponse<ITour[]>) => (this.trendingTours = res.body || []));
		this.homeService.queryForTypeCircuit().subscribe((res: HttpResponse<ITypeCircuit[]>) => (this.typeCircuits = res.body || []));

		this.destinations.forEach((reg: any) => {
			this.destinationSelect.push(reg.libRegion);
		});

	}

	isAuthenticated(): boolean {
		return this.accountService.isAuthenticated();
	}

	login(): void {
		this.loginModalService.open();
	}

	ngOnDestroy(): void {
		if (this.authSubscription) {
			this.authSubscription.unsubscribe();
		}
	}

	searchTours(): void {
		const df = this.createFromForm();
		// eslint-disable-next-line no-console
		console.log(df);

		this.homeService.queryForFilter(df).subscribe((res: HttpResponse<ITour[]>) => {
			// eslint-disable-next-line no-console
			console.log(res.body);
		});
	}

	onChange(): void {
		// eslint-disable-next-line no-console
		console.log(this.searchForm.get(['destination'])!.value);


	}

	private createFromForm(): IDataFilter {
		return {
			...new DataFilter(),
			destination: this.searchForm.get(['destination'])!.value,
			typeCircuit: this.searchForm.get(['typeCircuit'])!.value,
			dateCheckin: this.searchForm.get(['dateCheckin'])!.value ? moment(this.searchForm.get(['dateCheckin'])!.value).format('MM/DD/YYYY') : "",
			dateCheckout: this.searchForm.get(['dateCheckout'])!.value ? moment(this.searchForm.get(['dateCheckout'])!.value).format('MM/DD/YYYY') : ""
		};
	}
}
