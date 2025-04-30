
import { Component, OnInit, Input,Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Evenement } from '../evenement.model';
import { AjoutConcertComponent } from '../ajout-concert/ajout-concert.component'; 

@Component({
  selector: 'app-evenements',
  templateUrl: './evenements.component.html',
  standalone: true,
  styleUrl: './evenements.component.scss',
  imports: [CommonModule, AjoutConcertComponent],

})
export class EvenementsComponent implements OnInit {
  
  @Input('concerts')
  concerts: Evenement[] = [];
  
  @Output() 
  concertAjoute = new EventEmitter<Evenement>();
  id = 0;
  nouveauConcert = {
    id:0,
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
  }
  ajouterConcert(concert: Evenement) {
    this.concerts.push(concert);
  }

  ngOnInit(): void {
    this.concerts = [
      {
        id: 1,
        nom: 'Summer Fest',
        artiste: 'Imagine Dragons',
        lieu: 'Paris - Accor Arena',
        date: '15 août 2025',   // << juste une chaîne
        prix: 89.99,
        genre: 'Pop Rock',
        description: 'Un concert exceptionnel avec Imagine Dragons pour enflammer l\'été !',
        capacite: 15000
      },
      {
        id: 2,
        nom: 'Jazz Nights',
        artiste: 'Norah Jones',
        lieu: 'Lyon - Salle 3000',
        date: '20 septembre 2025',
        prix: 59.50,
        genre: 'Jazz',
        description: 'Norah Jones en tournée pour une soirée douce et mélodieuse.',
        capacite: 5000
      },
      {
        id: 3,
        nom: 'Electro Madness',
        artiste: 'David Guetta',
        lieu: 'Marseille - Stade Vélodrome',
        date: '5 octobre 2025',
        prix: 120.00,
        genre: 'Électro',
        description: 'David Guetta enflamme Marseille avec son show électrisant.',
        capacite: 60000
      }
    ];
    console.log('Concerts :', this.concerts);
  }
}

