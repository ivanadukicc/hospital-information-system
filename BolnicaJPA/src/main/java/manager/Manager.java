package manager;


import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Lek;
import model.Lekar;
import model.Pacijent;
import model.Pregled;
import model.Recept;
import model.Termin;


public class Manager {
	
	
	public static List<Lekar> traziLekare(String specIliIme) {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em.createQuery(
					"select l from Lekar l " + "where lower(l.specijalizacija) like :q "
							+ "   or lower(l.ime) like :q or lower(l.prezime) like :q " + "order by l.prezime, l.ime",
					Lekar.class).setParameter("q", "%" + specIliIme.toLowerCase() + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static List<Termin> getTerminiLekara(Long lekarId, LocalDate dan) {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em
					.createQuery("select t from Termin t "
							+ "where t.lekar.id = :lid and t.pocetak between :start and :end " + "order by t.pocetak",
							Termin.class)
					.setParameter("lid", lekarId).setParameter("start", dan.atStartOfDay())
					.setParameter("end", dan.plusDays(1).atStartOfDay().minusSeconds(1)).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static List<Pacijent> getPacijenti() {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em.createQuery("select p from Pacijent p order by p.prezime, p.ime", Pacijent.class).getResultList();
		} catch (NoResultException e) {
			System.out.println("Nema pacijenata.");
			return null;
		}
	}

	public static List<Pacijent> traziPacijente(String q) {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em
					.createQuery("select p from Pacijent p "
							+ "where lower(p.ime) like :q or lower(p.prezime) like :q or p.jmbg like :q "
							+ "order by p.prezime, p.ime", Pacijent.class)
					.setParameter("q", "%" + q.toLowerCase() + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static List<Pregled> getPreglediPacijenta(Long pacijentId, LocalDate od, LocalDate _do) {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em
					.createQuery("select pr from Pregled pr "
							+ "where pr.pacijent.id = :pid and pr.datum between :od and :do "
							+ "order by pr.datum desc", Pregled.class)
					.setParameter("pid", pacijentId).setParameter("od", od).setParameter("do", _do).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static List<Recept> getReceptiPacijenta(Long pacijentId) {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em.createQuery("select r from Recept r where r.pacijent.id = :pid order by r.datumIzdavanja desc",
					Recept.class).setParameter("pid", pacijentId).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static List<Termin> getTerminiPacijenta(Long pacijentId, java.util.Date od,
			java.util.Date _do) {
		EntityManager em = JPAUtils.getEntityManager();
		
		return em.createQuery("select t from Termin t " + "where t.pacijent.id = :pid and t.pocetak between :od and :do "
						+ "order by t.pocetak", Termin.class)
				.setParameter("pid", pacijentId).setParameter("od", od).setParameter("do", _do).getResultList();
	}

	public static List<Pregled> getPreglediLekara(Long lekarId, LocalDate od, LocalDate _do) {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em
					.createQuery("select pr from Pregled pr "
							+ "where pr.lekar.id = :lid and pr.datum between :od and :do " + "order by pr.datum desc",
							Pregled.class)
					.setParameter("lid", lekarId).setParameter("od", od).setParameter("do", _do).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static List<Lek> getLekovi() {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em.createQuery("select l from Lek l order by l.naziv", Lek.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static List<Recept> getRecepti() {
		EntityManager em = JPAUtils.getEntityManager();
		try {
			return em.createQuery("select r from Recept r order by r.datumIzdavanja desc", Recept.class)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}


	public static long countPreklapanja(Long lekarId, java.util.Date start, java.util.Date end) {
		jakarta.persistence.EntityManager em = JPAUtils.getEntityManager();
		return em
				.createQuery("select count(t) from Termin t "
						+ "where t.lekar.id = :lid and (t.pocetak < :end and t.kraj > :start)", Long.class)
				.setParameter("lid", lekarId).setParameter("start", start).setParameter("end", end).getSingleResult();
	}

	public static boolean zakaziTermin(Long pacijentId, Long lekarId, java.util.Date start, java.util.Date end) {
		jakarta.persistence.EntityManager em = JPAUtils.getEntityManager();
		try {
			em.getTransaction().begin();

			model.Pacijent pacijent = em.find(model.Pacijent.class, pacijentId);
			model.Lekar lekar = em.find(model.Lekar.class, lekarId);
			if (pacijent == null || lekar == null || start == null || end == null || !end.after(start)) {
				em.getTransaction().rollback();
				return false;
			}

			Long kolizija = em
					.createQuery("select count(t) from Termin t "
							+ "where t.lekar.id = :lid and (t.pocetak < :end and t.kraj > :start)", Long.class)
					.setParameter("lid", lekarId).setParameter("start", start).setParameter("end", end)
					.getSingleResult();

			if (kolizija != null && kolizija > 0) {
				em.getTransaction().rollback();
				return false;
			}

			model.Termin t = new model.Termin();
			t.setPacijent(pacijent);
			t.setLekar(lekar);
			t.setPocetak(start);
			t.setKraj(end);
			em.persist(t);

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			return false;
		}
	}

	

}
