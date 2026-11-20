package com.sb.Assurance.ml;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.sb.Assurance.entities.Client;
import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.entities.TypeSinistre;
import com.sb.Assurance.entities.Typevoiture;
import com.sb.Assurance.entities.Voiture;

class RisqueSinistreClientTest {

	@Test
	void construitLesVariablesAttenduesParLeServiceMl() {
		Client client = new Client();
		client.setClasse(7);
		Voiture voiture = new Voiture();
		voiture.setType(Typevoiture.taxi);
		voiture.setCv(8);
		voiture.setPrix(25);
		voiture.setNbplace(5);
		voiture.setClient(client);

		Sinistre s = new Sinistre();
		s.setVoiture(voiture);
		s.setTypeSinistre(TypeSinistre.bris_de_glace);
		s.setDateSinistre(LocalDate.of(2026, 11, 1));
		s.setDateDeclaration(LocalDate.of(2026, 11, 11));
		s.setMontantDeclare(600);
		s.setAccidentNuit(true);

		Map<String, Object> body = RisqueSinistreClient.construireRequete(s, 2);

		assertThat(body).containsEntry("type_vehicule", "taxi")
				.containsEntry("type_sinistre", "bris_de_glace")
				.containsEntry("classe_bonus_malus", 7)
				.containsEntry("delai_declaration_jours", 10L)
				.containsEntry("nb_sinistres_anterieurs", 2L)
				.containsEntry("accident_nuit", true)
				.containsEntry("presence_temoins", false)
				.hasSize(12);
	}
}
