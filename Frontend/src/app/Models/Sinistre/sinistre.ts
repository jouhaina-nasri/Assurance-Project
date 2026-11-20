import { Voiture } from "../Voiture/voiture";

export type TypeSinistre = 'collision' | 'vol' | 'incendie' | 'bris_de_glace';
export type StatutSinistre = 'EN_EVALUATION' | 'EN_VALIDATION' | 'EN_EXPERTISE' | 'APPROUVE' | 'REJETE';

export class Sinistre {
    id: number;
    reference: string;
    voiture: Voiture;
    dateSinistre: string;
    dateDeclaration: string;
    typeSinistre: TypeSinistre;
    montantDeclare: number;
    description: string;
    accidentNuit: boolean;
    presenceTemoins: boolean;
    constatAmiable: boolean;
    statut: StatutSinistre;
    scoreRisque: number | null;
    niveauRisque: string;
    versionModele: string;
    routage: string;
    montantIndemnise: number | null;
    commentaireDecision: string;
    decidePar: string;
    dateDecision: string;
}

export interface DeclarationSinistre {
    matricule: string;
    dateSinistre: string;
    typeSinistre: TypeSinistre;
    montantDeclare: number;
    description: string;
    accidentNuit: boolean;
    presenceTemoins: boolean;
    constatAmiable: boolean;
}

export interface Tache {
    id: string;
    nom: string;
    cle: string;
    creation: string;
    sinistre: Sinistre;
}

export interface Decision {
    approuve: boolean;
    montantIndemnise?: number;
    commentaire?: string;
}

export const LIBELLES_TYPE: { [k: string]: string } = {
    collision: 'Collision',
    vol: 'Vol',
    incendie: 'Incendie',
    bris_de_glace: 'Bris de glace'
};

export const LIBELLES_STATUT: { [k: string]: string } = {
    EN_EVALUATION: 'En évaluation',
    EN_VALIDATION: 'En validation',
    EN_EXPERTISE: 'En expertise',
    APPROUVE: 'Approuvé',
    REJETE: 'Rejeté'
};

export const LIBELLES_ROUTAGE: { [k: string]: string } = {
    AUTO_APPROBATION: 'Approbation automatique',
    VALIDATION_AGENT: 'Validation agent',
    EXPERTISE: 'Expertise'
};
