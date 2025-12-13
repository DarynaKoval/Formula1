package models;

import models.team.Driver;
import java.util.*;

/**
 * Система підрахунку балів за формулою F1
 * 
 * Розподіл балів:
 * 1-е місце: 25 балів
 * 2-е місце: 18 балів
 * 3-е місце: 15 балів
 * 4-е місце: 12 балів
 * 5-е місце: 10 балів
 * 6-е місце: 8 балів
 * 7-е місце: 6 балів
 * 8-е місце: 4 бали
 * 9-е місце: 2 бали
 * 10-е місце: 1 бал
 * Найшвидше коло (з топ-10): +1 бал
 */
public class ScoringSystem {
    
    // Система балів F1
    private static final int[] POINTS = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};
    
    /**
     * Розрахувати бали для результатів гонки
     * @param results - список результатів (відсортований за позицією)
     * @return мапа Driver -> бали
     */
    public static Map<Driver, Integer> calculatePoints(List<RaceResult> results) {
        Map<Driver, Integer> pointsMap = new HashMap<>();
        
        // Розподіл балів за місце
        for (int i = 0; i < results.size() && i < POINTS.length; i++) {
            RaceResult result = results.get(i);
            if (result.isFinished()) {
                int points = POINTS[i];
                result.setPoints(points);
                pointsMap.put(result.getDriver(), points);
            }
        }
        
        // Додатковий бал за найшвидше коло (якщо гонщик в топ-10)
        RaceResult fastestLapResult = findFastestLap(results);
        if (fastestLapResult != null && fastestLapResult.getPosition() <= 10) {
            Driver driver = fastestLapResult.getDriver();
            int currentPoints = pointsMap.getOrDefault(driver, 0);
            pointsMap.put(driver, currentPoints + 1);
            fastestLapResult.setPoints(currentPoints + 1);
        }
        
        return pointsMap;
    }
    
    /**
     * Знайти гонщика з найшвидшим колом
     */
    private static RaceResult findFastestLap(List<RaceResult> results) {
        RaceResult fastest = null;
        double fastestTime = Double.MAX_VALUE;
        
        for (RaceResult result : results) {
            if (result.getLapTime() > 0 && result.getLapTime() < fastestTime) {
                fastestTime = result.getLapTime();
                fastest = result;
            }
        }
        
        return fastest;
    }
    
    /**
     * Сортувати результати за позицією
     */
    public static void sortByPosition(List<RaceResult> results) {
        results.sort((r1, r2) -> Integer.compare(r1.getPosition(), r2.getPosition()));
    }
    
    /**
     * Отримати бали за позицію
     */
    public static int getPointsForPosition(int position) {
        if (position >= 1 && position <= POINTS.length) {
            return POINTS[position - 1];
        }
        return 0;
    }
}

