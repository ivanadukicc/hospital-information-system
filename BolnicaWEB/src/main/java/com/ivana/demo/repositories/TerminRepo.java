package com.ivana.demo.repositories;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Termin;

@Repository
public interface TerminRepo extends JpaRepository<Termin, Long> {
	boolean existsByLekar_IdAndPocetak(Long lekarId, Date poctak);

	List<Termin> findByLekar_IdOrderByPocetakAsc(Long lekarId);

	List<Termin> findByPacijent_IdOrderByPocetakDesc(Long pacijentId);

	List<Termin> findByPacijent_IdAndStatusAndPocetakAfterOrderByPocetakAsc(Long pacijentId, String string,
			Date date);

	List<Termin> findByLekar_IdAndStatusInAndPocetakAfterOrderByPocetakAsc(Long lekarId, List<String> statusi,
			Date now);
	
	List<Termin> findByLekar_IdAndPocetakBetweenOrderByPocetakAsc(
            Long lekarId, Date startInclusive, Date endExclusive
    );

	 @Query("SELECT t FROM Termin t " +
	           "WHERE (:lekarId IS NULL OR t.lekar.id = :lekarId) " +
	           "AND (:od IS NULL OR t.pocetak >= :od) " +
	           "AND (:doo IS NULL OR t.pocetak < :doo)")
	    Page<Termin> pretraga(@Param("lekarId") Long lekarId,
	                          @Param("od") LocalDateTime od,
	                          @Param("doo") LocalDateTime doo,
	                          PageRequest pageRequest);
	 
	 @Query("SELECT COUNT(t) FROM Termin t WHERE t.pocetak >= :start AND t.pocetak < :end")
	    long brojTerminaZaDan(@Param("start") LocalDateTime start,
	                          @Param("end") LocalDateTime end);

	    @Query("SELECT t.lekar.id, t.lekar.ime, t.lekar.prezime, COUNT(t.id) AS cnt " +
	           "FROM Termin t " +
	           "WHERE (:od IS NULL OR t.pocetak >= :od) AND (:doo IS NULL OR t.pocetak < :doo) " +
	           "GROUP BY t.lekar.id, t.lekar.ime, t.lekar.prezime " +
	           "ORDER BY cnt DESC")
	    List<Object[]> rangLekara(@Param("od") LocalDateTime start, @Param("doo") LocalDateTime end);

	    @Query("SELECT t.lekar.specijalnost, FUNCTION('DATE_FORMAT', t.pocetak, '%Y-%m') AS ym, COUNT(t.id) " +
	            "FROM Termin t GROUP BY t.lekar.specijalnost, ym " +
	            "ORDER BY ym DESC, COUNT(t.id) DESC")
		List<Object[]> specStatMesecno();
}
