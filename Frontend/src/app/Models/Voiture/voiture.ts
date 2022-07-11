import { Client } from "../Client/client";

export class Voiture {
    matricule: string;
    prix: number;
    nbplace:number;
    bgl:number;
    assist:number;
    cv:number;
    mf:number;
    type:string;
    cin:number;
    client:Client;
}