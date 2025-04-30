import { Component, EventEmitter, Output } from '@angular/core';
import { Evenement } from '../evenement.model';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-ajout-concert',
  standalone: true,
  templateUrl: './ajout-concert.component.html',
  styleUrls: ['./ajout-concert.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class AjoutConcertComponent {
  @Output() 
  concertAjoute = new EventEmitter<Evenement>();

  formVisible = false;

  id = 0;
  nouveauConcert = {
    id: 0,
    nom: '',
    artiste: '',
    lieu: '',
    date: '',
    prix: 0,
    genre: '',
    description: '',
    capacite: 0
  };

  envoyerConcert() {
    this.concertAjoute.emit({ ...this.nouveauConcert });
    this.nouveauConcert = {
      id: this.id++,
      nom: '',
      artiste: '',
      lieu: '',
      date: '',
      prix: 0,
      genre: '',
      description: '',
      capacite: 0,
    };
    this.formVisible = false;
  }
}
