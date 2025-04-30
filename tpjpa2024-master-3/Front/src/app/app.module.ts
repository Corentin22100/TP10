import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { DataViewModule } from 'primeng/dataview';
import { EvenementsComponent } from './evenements/evenements.component';
import { CardModule } from 'primeng/card';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AjoutConcertComponent } from './ajout-concert/ajout-concert.component';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    CardModule,
    DataViewModule,
    BrowserModule,
    AppRoutingModule,
    EvenementsComponent,
    AjoutConcertComponent
  ],

  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA] 
})
export class AppModule { }