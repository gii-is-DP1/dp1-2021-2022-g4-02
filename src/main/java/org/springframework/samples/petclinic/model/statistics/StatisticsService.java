package org.springframework.samples.petclinic.model.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsService {
	
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	@Transactional(readOnly = true)
	public Integer statisticsCount() {
		return (int) statisticsRepository.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Statistics> statisticsFindAll() {
		return statisticsRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Statistics findStatisticsById(int id) throws IllegalArgumentException { 
		return statisticsRepository.findById(id).get();
	}
	
	@Transactional
	public void saveStatistic(Statistics statisticToUpdate) throws DataAccessException {
		statisticsRepository.save(statisticToUpdate);
	}

}
