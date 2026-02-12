package com.ivana.demo.services;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivana.demo.repositories.LekarRepo;
import com.ivana.demo.repositories.PacijentRepo;
import com.ivana.demo.repositories.PregledRepo;
import com.ivana.demo.repositories.TerminRepo;

import model.Korisnik;
import model.Lekar;
import model.Pacijent;
import model.Termin;

@Service
public class TerminService {

	@Autowired
	TerminRepo tr;

	@Autowired
	LekarRepo lr;

	@Autowired
	PacijentRepo pr;
	
	@Autowired
	KorisnikService ks;

	@Autowired
	PregledRepo pregledrepo;
	
	@Autowired
	EmailService es;

	public List<Termin> getTermins() {
		return tr.findAll();
	}

	@Transactional
	public Termin zakazi(Long lekarId, Long pacijentId, Date pocetak, String napomena, Integer trajanje) {

		if (pocetak == null) {
			throw new IllegalArgumentException("Pocetak termina je obavezan");
		}

		int tra = (trajanje == null || trajanje <= 0) ? 30 : trajanje;
		validateRadnoVreme(pocetak, tra);

		if (tr.existsByLekar_IdAndPocetak(lekarId, pocetak)) {
			throw new IllegalStateException("Termin je zauzet");
		}
		Lekar lekar = lr.findById(lekarId).orElseThrow(() -> new IllegalArgumentException("Lekar ne postoji"));
		Pacijent pacijent = pr.findById(pacijentId)
				.orElseThrow(() -> new IllegalArgumentException("Pacijent ne postoji"));

		Termin t = new Termin();
		t.setLekar(lekar);
		t.setPacijent(pacijent);
		t.setPocetak(pocetak);

		Instant kraj = pocetak.toInstant().plus(tra, ChronoUnit.MINUTES);
		t.setKraj(Date.from(kraj));
		t.setNapomena(napomena);
		t.setStatus("ZAKAZAN");

		Termin sacuvanTermin = tr.save(t);

		tr.save(sacuvanTermin);
		 Korisnik k = ks.getKorisnikById(pacijent.getKorisnik().getId());
		 if(k == null) {
			 throw new IllegalArgumentException("Korisnik ne postoji za taj id");
		 }
		 String email = "ivanadbusiness@gmail.com";
		 System.out.println("Šaljem mejl na adresu: " + email);
		es.sendTerminConfirmation(
				email,
                //k.getEmail(),
                pacijent.getIme() + " " + pacijent.getPrezime(),
                sacuvanTermin.getPocetak()
        );

		return sacuvanTermin;
	}

	private void validateRadnoVreme(Date pocetak, int tra) {
		ZoneId zone = ZoneId.of("Europe/Belgrade");
		ZonedDateTime start = pocetak.toInstant().atZone(zone);
		DayOfWeek dow = start.getDayOfWeek();
		if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
			throw new IllegalArgumentException("Termini se mogu zakazivati samo radnim danima");
		}
		LocalTime workStart = LocalTime.of(8, 0);
		LocalTime workEnd = LocalTime.of(17, 0);

		LocalTime startTime = start.toLocalTime();
		LocalTime latestAllowedStart = workEnd.minusMinutes(tra);

		if (startTime.isBefore(workStart) || startTime.isAfter(latestAllowedStart)) {
			throw new IllegalArgumentException("Termin mora biti unutar radnog vremena(08-17h).");
		}

		ZonedDateTime end = start.plusMinutes(tra);
		if (!start.toLocalDate().equals(end.toLocalDate()) || end.toLocalTime().isAfter(workEnd)) {
			throw new IllegalArgumentException("Termin mora biti unutar istog radnog dana (08–17h).");
		}

	}

	@Transactional(readOnly = true)
	public List<Termin> zaLekara(Long lekarId) {
		return tr.findByLekar_IdOrderByPocetakAsc(lekarId);
	}

	@Transactional(readOnly = true)
	public List<Termin> aktivniZaLekara(Long lekarId) {
		return tr.findByLekar_IdAndStatusInAndPocetakAfterOrderByPocetakAsc(lekarId,
				java.util.List.of("ZAKAZAN", "POTVRDJEN"), new java.util.Date());
	}

	@Transactional(readOnly = true)
	public List<Termin> zaPacijenta(Long pacijentId) {
		return tr.findByPacijent_IdOrderByPocetakDesc(pacijentId);
	}

	@Transactional(readOnly = true)
	public Optional<Termin> findOne(Long id) {
		return tr.findById(id);
	}

	public void otkazi(Long terminId) {
		Termin t = load(terminId);
		if ("OTKAZAN".equalsIgnoreCase(t.getStatus())) {
			throw new IllegalStateException("Termin je već otkazan.");
		}
		if (t.getPocetak() != null && t.getPocetak().before(new Date())) {
			throw new IllegalStateException("Ne možeš otkazati termin koji je u prošlosti.");
		}

		t.setStatus("OTKAZAN");
		tr.save(t);
	}

	public List<Termin> aktivniZaPacijenta(Long pacijentId) {
		return tr.findByPacijent_IdAndStatusAndPocetakAfterOrderByPocetakAsc(pacijentId, "ZAKAZAN",
				new java.util.Date());
	}

	private Termin load(Long id) {
		return tr.findById(id).orElseThrow(() -> new IllegalArgumentException("Termin ne postoji"));
	}

	@Transactional(readOnly = true)
	public List<Termin> zaLekaraNaDan(Long lekarId, LocalDate dan) {
		ZoneId zone = ZoneId.systemDefault();
		Instant start = dan.atStartOfDay(zone).toInstant();
		Instant end = dan.plusDays(1).atStartOfDay(zone).toInstant();
		return tr.findByLekar_IdAndPocetakBetweenOrderByPocetakAsc(lekarId, Date.from(start), Date.from(end));
	}


	public Optional<Termin> findById(Long terminId) {
		// TODO Auto-generated method stub
		return tr.findById(terminId);
	}

}
