import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-view-materials',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './view-materials.html',
  styleUrl: './view-materials.css',
})
export class ViewMaterials {

}
