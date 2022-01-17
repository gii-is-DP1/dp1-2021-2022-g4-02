package sevenisles.achievements;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService {
	
	private AchievementRepository achievementRepo;
	
	@Autowired
	public AchievementService(AchievementRepository achievementRepo) {
		this.achievementRepo = achievementRepo;
	}
	
	@Transactional(readOnly = true)
	public Integer achievementCount() {
		return (int) achievementRepo.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Achievement> achievementFindAll() {
		return achievementRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Achievement> findAchievementById(int id) throws IllegalArgumentException { 
		return achievementRepo.findById(id);
	}
	
	@Transactional
	public void saveAchievement(Achievement achievementToUpdate) throws DataAccessException {
		achievementRepo.save(achievementToUpdate);
	}
	
	@Transactional
	public void deleteAchievement(Achievement achievementToDelete) throws DataAccessException {
		achievementRepo.delete(achievementToDelete);
	}
}
