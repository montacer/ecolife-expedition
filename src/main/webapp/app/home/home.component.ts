import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { IRegion } from 'app/shared/model/region.model';
import { TypeCircuit } from 'app/shared/model/type-circuit.model';
import { HomeFilterService } from './home.service';

@Component({
	selector: 'jhi-home',
	templateUrl: './home.component.html',
	styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
	currentDate: Date = new Date();
	account: Account | null = null;
	authSubscription?: Subscription;
	selectedDestination:  an;
	destinations: IRegion[] = []
	destinationSelect: string[] = [];

	typeCircuits: TypeCircuit[] = [];

	editForm = this.fb.group({
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
		this.editForm = this.fb.group({
			destination: [],
			typeCircuit: [],
			dateCheckin: [],
			dateCheckout: []
		});
		this.homeService.queryForRegion().subscribe((res: HttpResponse<IRegion[]>) => (this.destinations = res.body || []));

		this.homeService.queryForTypeCircuit().subscribe((res: HttpResponse<TypeCircuit[]>) => (this.typeCircuits = res.body || []));

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

	search(): void {

	}

	onChange(): void {
		// eslint-disable-next-line no-console
		console.log("*********+++++++++++++++***************" + this.selectedDestination);
	
	}
}
