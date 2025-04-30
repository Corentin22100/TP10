import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Import des composants





const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' }, // Redirection vers /home par défaut


  { path: '**', redirectTo: 'home' } // Catch-all route pour éviter 404
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

